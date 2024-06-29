import java.util.Comparator;

class PairFirstComparator<T extends Comparable<T>, U> implements Comparator<Pair<T, U>> {
    @Override
    public int compare(Pair<T, U> p1, Pair<T, U> p2) {
        return p1.first().compareTo(p2.first());
    }
}
