public class Service {
    private static final String FORMAT = "%s serving %s";
    private final Loader loader;
    private final Cruise cruise;

    Service(Loader loader, Cruise cruise) {
        this.loader = loader;
        this.cruise = cruise;
    }

    public int getServiceStartTime() {
        return this.cruise.getArrivalTime();
    }

    public int getServiceEndTime() {
        return getServiceStartTime() + this.cruise.getServiceTime();
    }

    @Override
    public String toString() {
        return String.format(FORMAT, this.loader, this.cruise);
    }
}
