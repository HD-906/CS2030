import java.util.Optional;

public abstract class Event implements Comparable<Event> {
    protected final Customer customer;
    protected final Double eventTime;
    protected final int serverId;
    protected final double waitingTime;

    Event(Customer customer, Double eventTime, int serverId) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.serverId = serverId;
        this.waitingTime = 0.0;
    }

    protected Event(Event event) {
        this.customer = event.customer;
        this.eventTime = event.eventTime;
        this.serverId = event.serverId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, int serverId) {
        this.customer = event.customer;
        this.eventTime = event.eventTime;
        this.serverId = serverId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, Double eventTime) {
        this.customer = event.customer;
        this.eventTime = eventTime;
        this.serverId = event.serverId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, Double eventTime, int i) {
        this.customer = event.customer;
        this.eventTime = eventTime;
        this.serverId = event.serverId;
        this.waitingTime = event.customer.getWaitingTime(eventTime);
    }

    protected abstract String output();

    protected abstract Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler);

    public Pair<Pair<Optional<Event>, ServerHandler>, String> triggerOut(ServerHandler handler) {
        return new Pair<>(trigger(handler), output());
    }

    public double getWaitingTime() {
        return 0.0;
    }

    public int countServed() {
        return 0;
    }

    @Override
    public int compareTo(Event other) {
        int firstComp = this.eventTime.compareTo(other.eventTime);
        if (firstComp == 0) {
            return this.customer.compareTo(other.customer);
        }
        return firstComp;
    }
}
