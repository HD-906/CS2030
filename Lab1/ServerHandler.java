public class ServerHandler {
    private final ImList<Double> serversAvailableTime;

    public ServerHandler(int numOfServers) {
        ImList<Double> serversAvailableTime = new ImList<Double>();

        for (int i = 1; i <= numOfServers; i++) {
            serversAvailableTime = serversAvailableTime.add(0.0);
        }
        this.serversAvailableTime = serversAvailableTime;
    }

    private ServerHandler(ImList<Double> serversAvailableTime) {
        this.serversAvailableTime = serversAvailableTime;
    }

    private int checkAvailability(Double arriveTime) {
        for(int i = 0; i < this.serversAvailableTime.size(); i++) {
            if(arriveTime >= this.serversAvailableTime.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public Pair<ServerHandler, Integer> serversAvailableTime(Double arriveTime, Double duration) {
        int index = checkAvailability(arriveTime);
        String str;
        if (index == -1) {
            return new Pair<ServerHandler, Integer>(this, index);
        }
        ImList<Double> newServersAvailableTime = this.serversAvailableTime.set(index, arriveTime + duration);
        return new Pair<ServerHandler, Integer>(new ServerHandler(newServersAvailableTime), index);
    }
}
