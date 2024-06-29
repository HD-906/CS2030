import java.util.Optional;

public class EventArrive extends Event {
    EventArrive(Customer customer, Double eventTime, int maxHumanServerId) {
        super(customer, eventTime, -1, maxHumanServerId);
    }

    @Override
    protected String output() {
        String out = this.customer.output("%.3f {Customer} arrives\n");
        return String.format(out, this.eventTime);
    }

    @Override
    protected Pair<Optional<Event>, ServerHandler> trigger(ServerHandler handler) {
        Pair<Integer, Integer> pair = handler.checkStatus(this.eventTime);
        Integer status = pair.first();
        int index = pair.second();
        if (status == 0) { // Idle
            return new Pair<>(Optional.of(new EventServe(this, index + 1)), 
            handler.addCustomer(index + 1));
        } else if (status == 1) { // Queue Available
            return new Pair<>(Optional.of(new EventWait(this, -(index + 1))), 
            handler.addCustomer(index + 1));
        } else if (status == -1) { // Full
            return new Pair<>(Optional.of(new EventLeave(this)), 
            handler);
        }
        return new Pair<>(Optional.<Event>empty(), handler);
    }
}
