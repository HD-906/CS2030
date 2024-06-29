public class ServingCustomer implements Comparable<ServingCustomer> {
    private final Double leavingTime;
    private final Integer myId;
    private final Integer serverId;

    ServingCustomer() {
        this.leavingTime = 0.0;
        this.myId = -1;
        this.serverId = -1;
    }

    ServingCustomer(Double leavingTime, Integer myId, Integer serverId) {
        this.leavingTime = leavingTime;
        this.myId = myId;
        this.serverId = serverId;
    }

    ServingCustomer(Pair<Pair<Integer, Integer>, Double> info) {
        this.leavingTime = info.second();
        this.myId = info.first().first();
        this.serverId = info.first().second();
    }

    public Boolean isEmpty() {
        return this.myId == -1;
    }

    public String output(String stringFormat) {
        return String.format(stringFormat, leavingTime, myId, serverId);
    }

    public Boolean doneLaterThan(Double time) {
        return this.leavingTime > time;
    }

    @Override
    public int compareTo(ServingCustomer other) {
        int timeCompare = this.leavingTime.compareTo(other.leavingTime);
        if (timeCompare != 0) {
            return timeCompare;
        }
        return this.myId.compareTo(other.myId);
    }
}
