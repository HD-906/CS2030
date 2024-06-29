import java.util.Optional;

public class EventServe extends Event {
    EventServe(Event event) {
        super(event);
    }

    EventServe(Event event, int serverId) {
        super(event, serverId);
    }

    EventServe(Event event, Double eventTime) {
        super(event, eventTime, "use customer.getWaitingTime(eventTime)");
    }

    @Override
    protected String output() {
        String out = this.customer.output("%.3f {Customer} serves by %s\n");
        return String.format(out, this.eventTime, "%s");
    }

    @Override
    public Pair<Pair<Optional<Event>, ServerHandler>, String> triggerOut(ServerHandler handler) {
        double doneTime = handler.getDoneTime(this.serverId);
        Pair<Optional<Event>, ServerHandler> res;
        if (doneTime <= this.eventTime) { // first in queue after last done
            double finishingTime = this.customer.getFinishingTime(this.eventTime);
            int servingId = handler.getNextAvailIndex(this.serverId, this.eventTime);
            handler = handler
                .setDoneTime(this.serverId, finishingTime, this.eventTime)
                .setResting(this.serverId, false);
            res = new Pair<>(
                Optional.of(new EventDone(this, finishingTime, servingId)), 
                handler
            );
            return new Pair<>(res, String.format(output(), serverToStr(servingId)));
        }
        // not first in queue after last done, update doneTime
        res = new Pair<>(Optional.of(new EventServe(this, doneTime)), handler);
        return new Pair<>(res, "");
    }
}
