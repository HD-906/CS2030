import java.util.Optional;

public class EventDone extends Event {
    private static final String OUT = "%.3f %s done serving by %s\n";

    EventDone(Event event, Double eventTime) {
        super(event, eventTime);
    }

    @Override
    protected String output() {
        String out = this.customer.output(OUT);
        return String.format(out, this.eventTime, this.serverId);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        return new Pair<>(Optional.empty(), handler.doneServing(this.serverId - 1));
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
