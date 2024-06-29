import java.util.Optional;

public class EventWait extends Event {
    private static final String OUT = "%.3f %s waits at %s\n";

    EventWait(Event event, int serverId) {
        super(event, serverId);
    }

    @Override
    protected String output() {
        String out = this.customer.output(OUT);
        return String.format(out, this.eventTime, this.serverId);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        double doneTime = handler.getDoneTime(this.serverId - 1);
        return new Pair<>(Optional.of(new EventServe(this, doneTime)), handler);
    }
}
