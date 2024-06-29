import java.util.Optional;

public abstract class Event implements Comparable<Event> {
    protected final Customer customer;
    protected final Double eventTime;
    protected final int serverId;
    protected final int maxHumanServerId;
    protected final double waitingTime;

    protected Event(Customer customer, Double eventTime, int serverId, int maxHumanServerId) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.serverId = serverId;
        this.maxHumanServerId = maxHumanServerId;
        this.waitingTime = 0.0;
    }

    protected Event(Event event) {
        this.customer = event.customer;
        this.eventTime = event.eventTime;
        this.serverId = event.serverId;
        this.maxHumanServerId = event.maxHumanServerId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, int serverId) {
        this.customer = event.customer;
        this.eventTime = event.eventTime;
        this.serverId = serverId;
        this.maxHumanServerId = event.maxHumanServerId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, Double eventTime) {
        this.customer = event.customer;
        this.eventTime = eventTime;
        this.serverId = event.serverId;
        this.maxHumanServerId = event.maxHumanServerId;
        this.waitingTime = event.waitingTime;
    }

    protected Event(Event event, Double eventTime, String s) {
        this.customer = event.customer;
        this.eventTime = eventTime;
        this.serverId = event.serverId;
        this.maxHumanServerId = event.maxHumanServerId;
        this.waitingTime = event.customer.getWaitingTime(eventTime);
    }

    protected Event(Event event, Double eventTime, int id) {
        this.customer = event.customer;
        this.eventTime = eventTime;
        this.serverId = Math.abs(id);
        this.maxHumanServerId = event.maxHumanServerId;
        this.waitingTime = event.waitingTime;
    }

    protected abstract String output();

    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        return new Pair<>(Optional.<Event>empty(), handler);
    }

    public Pair<Pair<Optional<Event>, ServerHandler>, String> triggerOut(ServerHandler handler) {
        return new Pair<>(trigger(handler), output());
    }

    public double getWaitingTime() {
        return 0.0;
    }

    public int countServed() {
        return 0;
    }

    protected String serverToStr() {
        int serverId = Math.abs(this.serverId);
        if (serverId > this.maxHumanServerId) {
            return String.format("%s %d", "self-check", serverId);
        }
        return String.valueOf(serverId);
    }

    protected String serverToStr(int serverId) {
        serverId = Math.abs(serverId);
        if (serverId > this.maxHumanServerId) {
            return String.format("%s %d", "self-check", serverId);
        }
        return String.valueOf(serverId);
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
