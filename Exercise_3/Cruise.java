public class Cruise {
    private static final String FORMAT = "%s@%04d";
    private final String id;
    private final int arrivalTime;
    private final int numOfLoaders;
    private final int serviceTime;

    Cruise(String id, int arrivalTime, int numOfLoaders, int timeNeeded) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.numOfLoaders = numOfLoaders;
        this.serviceTime = timeNeeded;
    }

    public int getServiceTime() {
        return this.serviceTime;
    }

    public int getArrivalTime() {
        return (this.arrivalTime / 100) * 60 + this.arrivalTime % 100;
    }

    public int getNumOfLoadersRequired() {
        return this.numOfLoaders;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, this.id, this.arrivalTime);
    }
}
