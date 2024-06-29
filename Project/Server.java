import java.util.function.Supplier;

public class Server {
    protected final int customersCount;
    protected final int qmax;
    protected final double doneTime;
    protected final boolean resting;
    protected final Supplier<Double> restTime;

    public Server(int qmax, Supplier<Double> restTime) {
        this.customersCount = 0;
        this.qmax = qmax;
        this.doneTime = 0.0;
        this.resting = false;
        this.restTime = restTime;
    }

    protected Server(Server server, int customersCount) {
        this.customersCount = customersCount;
        this.qmax = server.qmax;
        this.doneTime = server.doneTime;
        this.resting = server.resting;
        this.restTime = server.restTime;
    }

    protected Server(Server server, double newTime) {
        this.customersCount = server.customersCount;
        this.qmax = server.qmax;
        this.doneTime = newTime;
        this.resting = server.resting;
        this.restTime = server.restTime;
    }

    protected Server(Server server, boolean restStatus) {
        this.customersCount = server.customersCount;
        this.qmax = server.qmax;
        this.doneTime = server.doneTime;
        this.resting = restStatus;
        this.restTime = server.restTime;
    }

    public int getNextAvailIndex(int index, double eventTime) { // used by ServerSelf
        return index;
    }

    public Server addCustomer() {
        return new Server(this, this.customersCount + 1);
    }

    public Server doneServing() {
        return new Server(this, this.customersCount - 1);
    }

    public Server updateTime(int i, double newTime, double j) { // i, j used by ServerSelf
        return new Server(this, newTime);
    }

    public Server setResting(boolean restStatus) {
        return new Server(this, restStatus);
    }

    public double getDoneTime(int i) { // i used by ServerSelf
        return this.doneTime;
    }

    public double getRestTime() {
        return this.restTime.get();
    }

    public int isIdle(double arriveTime) {
        return (this.customersCount == 0 && this.doneTime <= arriveTime) ? 0 : -1;
    }

    public boolean hasRoom() {
        int effectiveCustomersCount = this.customersCount;
        if (this.resting) {
            effectiveCustomersCount += 1;
        }
        return effectiveCustomersCount <= this.qmax;
    }
}
