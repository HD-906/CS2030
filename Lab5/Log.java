import java.util.Optional;
import java.util.function.Function;
import java.lang.IllegalArgumentException;

class Log<T> {
    private static final String FORMAT = "Log[%s]";
    private static final String FORMAT_S = "%s\n%s";
    private static final String E = "Invalid arguments";
    private final T t;
    private final String s;

    private Log(Log<? extends T> log) {
        this.t = log.t;
        this.s = log.s;
    }

    private Log(T t, String str) {
        this.t = t;
        this.s = str;
    }

    private Log(Optional<T> oT, String str) {
        this.t = oT.orElseThrow(() -> new IllegalArgumentException(E));
        this.s = str;
    }

    private Log<T> appendStr(String s) {
        if (this.s.isEmpty() || s.isEmpty()) {
            return new Log<>(this.t, s + this.s);
        }
        return new Log<>(this.t, String.format(FORMAT_S, s, this.s));
    }

    public static <T> Log<T> of(T t) {
        Optional<T> oT = Optional.<T>ofNullable(t).filter(x -> !(x instanceof Log<?>));

        return new Log<>(oT, "");
    }

    public static <T> Log<T> of(T t, String s) {
        Optional<T> oT = Optional.<T>ofNullable(t)
            .filter(i -> !(i instanceof Log<?>))
            .filter(i -> Optional.<String>ofNullable(s).map(j -> true).orElse(false));

        return new Log<>(oT, s);
    }

    public <U> Log<U> map(Function<? super T, ? extends U> mapper) {
        return new Log<U>(Optional.<U>of(mapper.apply(this.t)), this.s);
    }

    public <U> Log<U> flatMap(Function<? super T,? extends Log<? extends U>> mapper) {
        Log<? extends U> logU = mapper.apply(this.t);
        return new Log<U>(logU.appendStr(this.s));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Log<?>)) {
            return false;
        }
        Log<?> otherLog = (Log<?>) other;
        return this.t.equals(otherLog.t) && this.s.equals(otherLog.s);
    }

    @Override
    public String toString() {
        String out = String.format(FORMAT, t.toString());
        if (this.s.isEmpty()) {
            return out;
        }
        return String.format(FORMAT_S, out, this.s);
    }
}
