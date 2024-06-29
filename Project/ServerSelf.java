import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerSelf extends Server {
    private final ImList<Double> selfCheckAvailability;
    private final int numOfSelfChecks;

    public ServerSelf(int qmax, int numOfSelfChecks) {
        super(qmax, () -> 0.0);
        this.numOfSelfChecks = numOfSelfChecks;
        this.selfCheckAvailability = new ImList<>(
            Stream.generate(() -> 0.0)
                .limit(numOfSelfChecks)
                .collect(Collectors.toList())
        );
    }

    private ServerSelf(ServerSelf server, int customersCount) {
        super(server, customersCount);
        this.numOfSelfChecks = server.numOfSelfChecks;
        this.selfCheckAvailability = server.selfCheckAvailability;
    }

    private ServerSelf(ServerSelf server, ImList<Double> newAvail, double firstAvail) {
        super(server, firstAvail);
        this.numOfSelfChecks = server.numOfSelfChecks;
        this.selfCheckAvailability = newAvail;
    }

    private ServerSelf(ServerSelf server, boolean restStatus) {
        super(server, restStatus);
        this.numOfSelfChecks = server.numOfSelfChecks;
        this.selfCheckAvailability = server.selfCheckAvailability;
    }

    @Override
    public int getNextAvailIndex(int index, double eventTime) {
        if (index < 0) {
            for (int i = 0; i < this.selfCheckAvailability.size(); i++) {
                if (this.selfCheckAvailability.get(i) <= eventTime) {
                    return i - index;
                }
            }
        }
        return index;
    }

    @Override
    public ServerSelf addCustomer() {
        return new ServerSelf(this, this.customersCount + 1);
    }

    @Override
    public ServerSelf doneServing() {
        return new ServerSelf(this, this.customersCount - 1);
    }

    @Override
    public ServerSelf updateTime(int index, double newTime, double timeToCompare) {
        // from Wait: index == -1
        if (index < 0) {
            index = this.getNextAvailIndex(-1, timeToCompare);
        }
        ImList<Double> newAvail = this.selfCheckAvailability.set(index - 1, newTime);
        double firstAvail = newAvail.stream()
            .min(Comparator.naturalOrder())
            .orElse(0.0);
        return new ServerSelf(this, newAvail, firstAvail);
    }

    @Override
    public ServerSelf setResting(boolean restStatus) {
        return new ServerSelf(this, false);
    }

    @Override
    public double getDoneTime(int index) { // from Wait: index == -1
        if (index > 0) {
            return this.selfCheckAvailability.get(index - 1);
        }
        return this.doneTime;
    }

    @Override
    public int isIdle(double arriveTime) {
        for (int i = 0; i < this.selfCheckAvailability.size(); i++) {
            if (this.selfCheckAvailability.get(i) <= arriveTime) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean hasRoom() {
        return this.customersCount < this.qmax + this.numOfSelfChecks;
    }
}
