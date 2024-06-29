public class FuncStat {
    private final int count;
    private final int depth;
    private final int maxDepth;

    FuncStat() {
        this.count = 0;
        this.depth = 0;
        this.maxDepth = 0;
    }

    private FuncStat(int count, int depth, int maxDepth) {
        this.count = count;
        this.depth = depth;
        this.maxDepth = Integer.max(depth, maxDepth);
    }
    
    public FuncStat inc() {
        return new FuncStat(this.count + 1, this.depth + 1, this.maxDepth);
    }

    public FuncStat decrDepth() {
        return new FuncStat(this.count, this.depth - 1, this.maxDepth);
    }

    public String toString() {
        return String.format("count=%d maxDepth=%d", this.count, this.maxDepth);
    }
}
