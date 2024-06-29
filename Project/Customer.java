import java.util.function.Supplier;

public class Customer implements Comparable<Customer> {
    private final Supplier<Double> serviceTime;
    private final Integer id;
    private final double arrivalTime;

    Customer(Supplier<Double> serviceTime, int id, double arrivalTime) {
        this.serviceTime = serviceTime;
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    private double getServiceTime() {
        return this.serviceTime.get();
    }

    public String output(String sFormat) {
        return sFormat.replaceFirst("\\{Customer\\}", String.valueOf(this.id));
    }

    public double getFinishingTime(double startingTime) {
        return startingTime + this.getServiceTime();
    }

    public double getFinishingTime() {
        return this.arrivalTime + this.getServiceTime();
    }

    public double getWaitingTime(double startingTime) {
        return startingTime - this.arrivalTime;
    }

    @Override
    public int compareTo(Customer other) {
        return this.id.compareTo(other.id);
    }
}
