import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Supplier;

public class ServerHandler {
    private final ImList<Server> serversAvailableTime;

    public ServerHandler(
        int numOfServers, int numOfSelfChecks, int qmax, Supplier<Double> restTime) {

        Server server = new Server(qmax, restTime);
        Stream<Server> serverHuman = Stream.generate(() -> server).limit(numOfServers);
        Stream<Server> serverSelfCheck = numOfSelfChecks > 0 ?
            Stream.<Server>of(new ServerSelf(qmax, numOfSelfChecks)) :
            Stream.<Server>empty();
        this.serversAvailableTime = new ImList<>(
            Stream.concat(serverHuman, serverSelfCheck).collect(Collectors.toList())
        );
    }

    private ServerHandler(ImList<Server> serversAvailableTime) {
        this.serversAvailableTime = serversAvailableTime;
    }

    private int getIndexMain(int index) {
        return Integer.min(Math.abs(index), this.serversAvailableTime.size()) - 1;
    }

    public Pair<Integer, Integer> checkStatus(Double arriveTime) {
        // Pair: (status, index), index = -1 if status == 1
        boolean hasRoom = false;
        int hasRoomIndex = 0;
        for (int i = 0; i < this.serversAvailableTime.size(); i++) {
            int id = this.serversAvailableTime.get(i).isIdle(arriveTime);
            if (id != -1) {
                return new Pair<>(0, i + id);
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
        index = getIndexMain(index);
        Server server = this.serversAvailableTime.get(index).addCustomer();
        
        return new ServerHandler(this.serversAvailableTime.set(index, server));
    }

    public ServerHandler doneServing(int index) {
        index = getIndexMain(index);
        Server server = this.serversAvailableTime.get(index).doneServing();
        return new ServerHandler(serversAvailableTime.set(index, server));
    }

    public ServerHandler setDoneTime(int index, double finishingTime, double timeToCompare) {
        int sign = index < 0 ? -1 : 1;
        int indexMain = getIndexMain(index);
        Server server = this.serversAvailableTime.get(indexMain)
            .updateTime(index - sign * indexMain, finishingTime, timeToCompare);
        return new ServerHandler(serversAvailableTime.set(indexMain, server));
    }

    public ServerHandler setResting(int index, boolean restStatus) {
        index = getIndexMain(index);
        Server server = this.serversAvailableTime.get(index).setResting(restStatus);
        return new ServerHandler(serversAvailableTime.set(index, server));
    }

    public double getDoneTime(int index) {
        int sign = index < 0 ? -1 : 1;
        int indexMain = getIndexMain(index);
        return this.serversAvailableTime.get(indexMain).getDoneTime(index - sign * indexMain);
    }

    public double getRestTime(int index) {
        index = getIndexMain(index);
        return this.serversAvailableTime.get(index).getRestTime();
    }

    public int getNextAvailIndex(int index, double eventTime) {
        int indexMain = getIndexMain(index);
        return this.serversAvailableTime.get(indexMain).getNextAvailIndex(index, eventTime);
    }
}
