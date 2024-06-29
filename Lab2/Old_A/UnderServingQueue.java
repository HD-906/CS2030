import java.util.Comparator;

public class UnderServingQueue {
    private static final ServingCustomer EMPTY = new ServingCustomer();

    private final ServingCustomer nextPoll;
    private final PQ2<ServingCustomer> polledQueue;

    public UnderServingQueue() {
        this.nextPoll = EMPTY;
        this.polledQueue = new PQ2<ServingCustomer>(Comparator.naturalOrder());
    }

    private UnderServingQueue(PQ2<ServingCustomer> pq) {
        if (pq.isEmpty()) {
            this.nextPoll = EMPTY;
            this.polledQueue = pq;
        } else {
            Pair<ServingCustomer, PQ2<ServingCustomer>> poll = pq.poll();
            this.nextPoll = poll.first();
            this.polledQueue = poll.second();
        }
    }

    private UnderServingQueue(ServingCustomer polled, PQ2<ServingCustomer> pq) {
        this.nextPoll = polled;
        this.polledQueue = pq;
    }

    public Boolean isEmpty() {
        return nextPoll.isEmpty();
    }

    public ServingCustomer getDoneCustomer() {
        return this.nextPoll;
    }

    public UnderServingQueue updatePoll() {
        return new UnderServingQueue(polledQueue);
    }

    public Boolean existEarlierDone(Double arrivingTime) {
        if (this.nextPoll.isEmpty() || nextPoll.leavingLaterThan(arrivingTime)) {
            return false;
        }
        return true;
    }

    public UnderServingQueue addServed(ServingCustomer arrivingCustomer) {
        if (nextPoll.isEmpty()) {
            return new UnderServingQueue(arrivingCustomer, polledQueue);
        }
        if (nextPoll.compareTo(arrivingCustomer) == 1) { // arrivingCustomer leaves earliest
            return new UnderServingQueue(arrivingCustomer, polledQueue.add(nextPoll));
        }
        return new UnderServingQueue(nextPoll, polledQueue.add(arrivingCustomer));
    }
}
