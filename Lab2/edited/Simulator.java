import java.util.Comparator;
import java.util.Optional;

public class Simulator {
    private static final String NEW_ARRIVAL = "%.3f %s arrives\n";
    private static final String NEW_SERVE = "%.3f %s serves by %s\n";
    private static final String NEW_DONE = "%.3f %s done serving by %s\n";
    private static final String NEW_LEAVE = "%.3f %s leaves\n";

    private final ServerHandler servers;
    private final ImList<Double> arrivalTimes;
    private final ImList<Double> serviceTimes;
    
    Simulator(int numOfServers, ImList<Double> arrivalTimes, ImList<Double> serviceTimes) {
        this.servers = new ServerHandler(numOfServers);
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
    }
    
    public String simulate() {
        ServerHandler serv = this.servers;
        Double arrivalTime;
        Double duration;
        String output = "";
        int id;
        int served = 0;
        PQ<ServingCustomer> servingPQ = new PQ<>(Comparator.naturalOrder());
        Pair<ServingCustomer, PQ<ServingCustomer>> polled;
        // UnderServingQueue servingPQ = new UnderServingQueue();
        
        for (id = 0; id < arrivalTimes.size(); id++) {
            arrivalTime = arrivalTimes.get(id);
            duration = serviceTimes.get(id);
            Optional<ServingCustomer> c = Optional.empty();

            while (!servingPQ.isEmpty() && !servingPQ.poll().first().doneLaterThan(arrivalTime)) {
                polled = servingPQ.poll();
                output += polled.first().output(NEW_DONE);
                servingPQ = polled.second();
            }
            
            Pair<ServerHandler, Integer> result = serv.serversAvailableTime(arrivalTime, duration);
            serv = result.first();
            Integer index = result.second();
            output += String.format(NEW_ARRIVAL, arrivalTime, id + 1);
            if (index >= 0) {
                output += String.format(NEW_SERVE, arrivalTime, id + 1, index + 1);
                servingPQ = servingPQ.add(
                    new ServingCustomer(arrivalTime + duration, id + 1, index + 1));
                served++;
            } else {
                output += String.format(NEW_LEAVE, arrivalTime, id + 1);
            }
        }

        while (!servingPQ.isEmpty()) {
            polled = servingPQ.poll();
            output += polled.first().output(NEW_DONE);
            servingPQ = polled.second();
        }

        output = output + String.format("[%d %d]", served, id - served);
        return output;
    }
}