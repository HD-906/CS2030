import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulator {

    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;
    
    Simulator(
        int numOfServers, int numOfSelfChecks, int qmax, ImList<Double> arrivalTimes, 
        Supplier<Double> serviceTimes, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    public String simulate() {
        String output = "";
        int served = 0;
        double totalWaitingTime = 0.0;
        ServerHandler handler = new ServerHandler(
            this.numOfServers, this.numOfSelfChecks, this.qmax, this.restTimes);
        PQ<Event> eventsPQ = new PQ<>(Comparator.naturalOrder());

        for (int id = 0; id < arrivalTimes.size(); id++) {
            Double arrivalTime = arrivalTimes.get(id);
            Customer newCustomer = new Customer(this.serviceTimes, id + 1, arrivalTime);
            eventsPQ = eventsPQ.add(new EventArrive(newCustomer, arrivalTime, this.numOfServers));
        }

        while (!eventsPQ.isEmpty()) {
            Pair<Event, PQ<Event>> polledPair = eventsPQ.poll();
            Event event = polledPair.first();
            eventsPQ = polledPair.second();

            Pair<Pair<Optional<Event>, ServerHandler>, String> p = event.triggerOut(handler);
            Pair<Optional<Event>, ServerHandler> transformedPair = p.first();
            output += p.second();

            Optional<Event> oTriggeredEvent = transformedPair.first();
            handler = transformedPair.second();

            if (oTriggeredEvent.map(e -> true).orElse(false)) {
                eventsPQ = eventsPQ.add(oTriggeredEvent.orElse(event));
            } else {
                served += event.countServed();
                totalWaitingTime += event.getWaitingTime();
            }
        }

        output += String.format(
            "[%.3f %d %d]", 
            (served != 0) ? totalWaitingTime / served : totalWaitingTime, 
            served, 
            arrivalTimes.size() - served
        );

        return output;
    }
}
