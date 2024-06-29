public class Server {
    private final int customersCount;
    private final int qmax;
    private final Double doneTime;

    public Server(int qmax) {
        this.customersCount = 0;
        this.qmax = qmax;
        this.doneTime = 0.0;
    }

    private Server(Server server, int customersCount) {
        this.customersCount = customersCount;
        this.qmax = server.qmax;
        this.doneTime = server.doneTime;
    }

    private Server(Server server, Double newTime) {
        this.customersCount = server.customersCount;
        this.qmax = server.qmax;
        this.doneTime = newTime;
    }

    public Server addCustomer() {
        return new Server(this, this.customersCount + 1);
    }

    public Server doneServing() {
        return new Server(this, this.customersCount - 1);
    }

    public Server updateTime(Double newTime) {
        return new Server(this, newTime);
    }

    public Double getDoneTime() {
        return this.doneTime;
    }

    public boolean isIdle() {
        return this.customersCount == 0;
    }

    public boolean hasRoom() {
        return this.customersCount <= this.qmax;
    }
}
