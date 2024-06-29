import java.util.Optional;

public class EventServe extends Event {
    private static final String OUT = "%.3f %s serves by %s\n";

    EventServe(Event event) {
        super(event);
    }

    EventServe(Event event, int serverId) {
        super(event, serverId);
    }

    EventServe(Event event, Double eventTime) {
        super(event, eventTime, 0);
    }

    @Override
    protected String output() {
        String out = this.customer.output(OUT);
        return String.format(out, this.eventTime, this.serverId);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        double doneTime = handler.getDoneTime(this.serverId - 1);
        if (doneTime <= this.eventTime) {
            double finishingTime = this.customer.getFinishingTime();
            return new Pair<>(Optional.of(new EventDone(this, finishingTime)), 
            handler.setDoneTime(this.serverId - 1, finishingTime));
        }
        return new Pair<>(Optional.of(new EventServe(this, doneTime)), handler);
    }

    @Override
    public Pair<Pair<Optional<Event>, ServerHandler>, String> triggerOut(ServerHandler handler) {
        double doneTime = handler.getDoneTime(this.serverId - 1);
        Pair<Optional<Event>, ServerHandler> res;
        if (doneTime <= this.eventTime) {
            double finishingTime = this.customer.getFinishingTime(this.eventTime);
            res = new Pair<>(Optional.of(new EventDone(this, finishingTime)), 
            handler.setDoneTime(this.serverId - 1, finishingTime));
            return new Pair<>(res, output());
        }
        
        res = new Pair<>(Optional.of(new EventServe(this, doneTime)), handler);
        return new Pair<>(res, "");
    }
}
