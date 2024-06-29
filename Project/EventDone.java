import java.util.Optional;

public class EventDone extends Event {
    EventDone(Event event, Double eventTime, int realServerId) {
        super(event, eventTime, realServerId);
    }

    @Override
    protected String output() {
        String out = this.customer.output("%.3f {Customer} done serving by %s\n");
        return String.format(out, this.eventTime, this.serverToStr());
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        double doneTimeWithRest = 
            handler.getDoneTime(this.serverId) + handler.getRestTime(this.serverId);
        return new Pair<>(
            Optional.<Event>empty(), 
            handler
                .setDoneTime(this.serverId, doneTimeWithRest, 0.0)
                .doneServing(this.serverId)
                .setResting(this.serverId, true)
        );
    }

    @Override
    public double getWaitingTime() {
        return this.waitingTime;
    }

    @Override
    public int countServed() {
        return 1;
    }
}
