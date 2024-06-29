import java.util.Scanner;
import java.util.Iterator;

class Main_o {
    static final int NO_AVAILABLE_SERVERS = -1;

    static int findAvailableServer(ImList<Double> serversAvailableTime, double time) {
        int index = 0;

        while (index < serversAvailableTime.size() && serversAvailableTime.get(index) > time) {
            index = index + 1;
        }
        if (index < serversAvailableTime.size()) {
            return index;
        } else {
            return -1;
        }
    }

    static ImList<Double> initServersAvailableTime(int numOfServers) {
        ImList<Double> serversAvailableTime = new ImList<Double>();

        for (int i = 1; i <= numOfServers; i++) {
            serversAvailableTime = serversAvailableTime.add(0.0);
        }
        return serversAvailableTime;
    }

    static String processCustomers(ImList<Double> arrivalTimes, ImList<Double> serviceTimes, 
            ImList<Double> serversAvailableTime) {
        int customerId = 1;
        int numServed = 0;
        int numLeft = 0;
        String output = ""; 
        Iterator<Double> iter = serviceTimes.iterator();

        for (double time : arrivalTimes) {
            output = output + String.format("%.3f customer %s arrives\n", time, customerId);
            double serviceTime = iter.next();
            int index = findAvailableServer(serversAvailableTime, time);

            if (index != NO_AVAILABLE_SERVERS) {
                serversAvailableTime = serversAvailableTime.set(index, time + serviceTime);
                numServed = numServed + 1;
                output = output + String.format("%.3f customer %s served by server %s\n", 
                        time, customerId, (index + 1));
            } else {
                numLeft = numLeft + 1;
                output = output + String.format("%.3f customer %s leaves\n", time, customerId);
            }
            customerId = customerId + 1;
        }

        output = output + "[" + numServed + " " + numLeft + "]";
        return output;
    }

    static void simulate(int numOfServers, ImList<Double> arrivalTimes, 
            ImList<Double> serviceTimes) {
        ImList<Double> serversAvailableTime = initServersAvailableTime(numOfServers);    
        String output = processCustomers(arrivalTimes, serviceTimes, serversAvailableTime);
        System.out.println(output);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ImList<Double> arrivalTimes = new ImList<Double>();
        ImList<Double> serviceTimes = new ImList<Double>();

        int numOfServers = sc.nextInt();
        while (sc.hasNextDouble()) {
            arrivalTimes = arrivalTimes.add(sc.nextDouble());
            serviceTimes = serviceTimes.add(sc.nextDouble());
        }
        simulate(numOfServers, arrivalTimes, serviceTimes);
        sc.close();
    }
}
