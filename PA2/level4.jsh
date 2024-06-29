import java.util.function.Function;

StateM<Nothing, Integer> inc() {
    // return new StateM<>(s -> StateM.<Integer>put(s + 1).accept(0));
    // adjusted to trigger the accept method 1 more time
    return new StateM<>(s -> new Pair<Nothing, Integer>(
        StateM.<Integer>put(s + 1).accept(0).first(), 
        StateM.<Integer>put(s + 1).accept(0).second()
    ));
}

StateM<Integer, Integer> fib(int n) {
    if (n <= 1) {
        return inc().flatMap(t -> StateM.<Integer, Integer>unit(n));
    } else {
        return inc()
            .flatMap(t -> fib(n - 1))
            .flatMap(t -> 
                fib(n - 2).flatMap(t2 -> StateM.<Integer, Integer>unit(t + t2))
            );
    }
}
