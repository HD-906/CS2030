import java.util.Optional;

public class EventLeave extends Event {
    EventLeave(Event event) {
        super(event);
    }

    @Override
    protected String output() {
        String out = this.customer.output("%.3f {Customer} leaves\n");
        return String.format(out, this.eventTime);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        return new Pair<>(Optional.<Event>empty(), handler);
    }
}
