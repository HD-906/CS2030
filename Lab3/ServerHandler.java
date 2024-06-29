import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerHandler {
    private final ImList<Server> serversAvailableTime;

    public ServerHandler(int numOfServers, int qmax) {
        this.serversAvailableTime = new ImList<>(
            Stream.generate(() -> new Server(qmax))
                .limit(numOfServers)
                .collect(Collectors.toList())
        );
    }

    private ServerHandler(ImList<Server> serversAvailableTime) {
        this.serversAvailableTime = serversAvailableTime;
    }

    public Pair<Integer, Integer> checkStatus() { // (status, index) , index = -1 if status == FULL
        boolean hasRoom = false;
        int hasRoomIndex = 0;
        for (int i = 0; i < this.serversAvailableTime.size(); i++) {
            if (this.serversAvailableTime.get(i).isIdle()) {
                return new Pair<>(0, i);
            } else if (!hasRoom && this.serversAvailableTime.get(i).hasRoom()) {
                hasRoomIndex = i;
                hasRoom = true;
            }
        }
        if (hasRoom) {
            return new Pair<>(1, hasRoomIndex);
        }
        return new Pair<>(-1, -1);
    }

    public ServerHandler addCustomer(int index) {
        Server server = this.serversAvailableTime.get(index).addCustomer();
        return new ServerHandler(serversAvailableTime.set(index, server));
    }

    public ServerHandler doneServing(int index) {
        Server server = this.serversAvailableTime.get(index).doneServing();
        return new ServerHandler(serversAvailableTime.set(index, server));
    }

    public ServerHandler setDoneTime(int index, double finishingTime) {
        Server server = this.serversAvailableTime.get(index).updateTime(finishingTime);
        return new ServerHandler(serversAvailableTime.set(index, server));
    }

    public Double getDoneTime(int index) {
        return this.serversAvailableTime.get(index).getDoneTime();
    }
}
