import java.util.Scanner;
import java.util.function.Supplier;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

class Main {
    private static final Random RNG_REST = new Random(3L);
    private static final Random RNG_REST_PERIOD = new Random(4L);
    private static final double SERVER_REST_RATE = 0.1;

    static double genRestPeriod() {
        return -Math.log(RNG_REST_PERIOD.nextDouble()) / SERVER_REST_RATE;
    }

    static double genServPeriod(Iterator<Double> iter) {
        if (iter.hasNext()) {
            return iter.next();
        }
        return 1.0;
    }

    public static void main(String[] args) {
        double[] d = {0.313, 2.645, 2.701, 0.264, 1.482, 0.415, 0.215, 3.513, 0.209, 0.056, 1.879, 0.094, 0.001, 0.616, 0.023, 1.842, 0.6, 2.37};
        Double[] objectDoubles = Arrays.stream(d).boxed().toArray(Double[]::new);
        Iterator<Double> iter = Arrays.asList(objectDoubles).iterator();

        Scanner sc = new Scanner(System.in);
        ImList<Double> arrivalTimes = new ImList<Double>();
        Supplier<Double> serviceTimes = () -> genServPeriod(iter);
        int numOfServers = sc.nextInt();
        int numOfSelfChecks = sc.nextInt();
        int qmax = sc.nextInt();
        double probRest = sc.nextDouble();
        Supplier<Double> restTimes = () ->
            RNG_REST.nextDouble() < probRest ? genRestPeriod() : 0.0;

        while (sc.hasNextDouble()) {
            arrivalTimes = arrivalTimes.add(sc.nextDouble());
        }

        Simulator sim = new Simulator(numOfServers, numOfSelfChecks, qmax, arrivalTimes, serviceTimes, restTimes);
        System.out.println(sim.simulate());
        sc.close();
    }
}
