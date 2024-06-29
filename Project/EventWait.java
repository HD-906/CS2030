import java.util.Optional;

public class EventWait extends Event {
    EventWait(Event event, int serverId) {
        super(event, serverId);
    }

    @Override
    protected String output() {
        String out = this.customer.output("%.3f {Customer} waits at %s\n");
        return String.format(out, this.eventTime, this.serverToStr());
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        double doneTime = handler.getDoneTime(this.serverId);
        return new Pair<>(Optional.of(new EventServe(this, doneTime)), handler);
    }
}
