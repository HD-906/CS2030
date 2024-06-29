import java.util.function.Function;

class StateM<T, S> extends AbstractStateM<T, S> {
    StateM(Function<S, Pair<T, S>> f) {
        super(f);
    }

    StateM(T t) {
        super(t);
    }

    static <T, S> StateM<T, S> unit(T t) {
        return new StateM<>(t);
    }

    static <S> StateM<S, S> get() {
        return new StateM<>(s -> new Pair<S, S>(s, s));
    }

    static <T> StateM<Nothing, T> put(T t) {
        return new StateM<>(s -> new Pair<Nothing, T>(Nothing.nothing(), t));
    }

    public <U> StateM<U, S> flatMap(Function<T, StateM<U, S>> func) {
        return new StateM<U, S>((S s) -> {
            Pair<T, S> pair = this.accept(s);
            return func.apply(pair.first()).accept(pair.second());
        });
    }

    @Override
    public String toString() {
        return "StateM";
    }
}

