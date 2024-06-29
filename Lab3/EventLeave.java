import java.util.Optional;

public class EventLeave extends Event {
    private static final String OUT = "%.3f %s leaves\n";

    EventLeave(Event event) {
        super(event);
    }

    @Override
    protected String output() {
        String out = this.customer.output(OUT);
        return String.format(out, this.eventTime);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        return new Pair<>(Optional.empty(), handler);
    }
}
