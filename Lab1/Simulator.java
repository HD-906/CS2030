class Simulator {
	private static final String NEW_ARRIVAL = "%.3f customer %s arrives\n";
	private static final String NEW_SERVE = "%.3f customer %s served by server %s\n";
	private static final String NEW_LEAVE = "%.3f customer %s leaves\n";

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
        Double arr, dur;
        String output = "";
        int served = 0, id;
        for (id = 0; id < arrivalTimes.size(); id++) {
            arr = arrivalTimes.get(id);
            dur = serviceTimes.get(id);
            Pair<ServerHandler, Integer> result = serv.serversAvailableTime(arr, dur);
            serv = result.first();
            Integer index = result.second();
            output += String.format(NEW_ARRIVAL, arr, id + 1);
            if (index >= 0) {
                output += String.format(NEW_SERVE, arr, id + 1, index + 1);
                served++;
            } else {
                output += String.format(NEW_LEAVE, arr, id + 1);
            }
        }
        output = output + String.format("[%d %d]", served, id - served);
        return output;
    }
}