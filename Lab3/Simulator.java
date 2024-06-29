import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulator {

    private final int numOfServers;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    
    Simulator(int numSer, int qmax, ImList<Double> arrTimes, Supplier<Double> svcTimes) {
        this.numOfServers = numSer;
        this.qmax = qmax;
        this.arrivalTimes = arrTimes;
        this.serviceTimes = svcTimes;
    }

    public String simulate() {
        String output = "";
        int served = 0;
        double totalWaitingTime = 0.0;
        ServerHandler handler = new ServerHandler(this.numOfServers, this.qmax);
        PQ<Event> eventsPQ = new PQ<>(Comparator.naturalOrder());

        for (int id = 0; id < arrivalTimes.size(); id++) {
            Double arrivalTime = arrivalTimes.get(id);
            Customer newCustomer = new Customer(this.serviceTimes, id + 1, arrivalTime);
            eventsPQ = eventsPQ.add(new EventArrive(newCustomer, arrivalTime));
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
            (totalWaitingTime > 0) ? totalWaitingTime / served : totalWaitingTime, 
            served, 
            arrivalTimes.size() - served);

        return output;
    }
}
