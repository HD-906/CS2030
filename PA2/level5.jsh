StateM<Integer, FuncStat> ack(int m, int n) {
    StateM<Integer, FuncStat> state;
    StateM<Nothing, FuncStat> stateInit = new StateM<>(
        s -> StateM.<FuncStat>put(s.inc()).accept(s)
    );

    if (m == 0) {
        state = stateInit.flatMap(t -> StateM.<Integer, FuncStat>unit(n + 1));
    } else if (n == 0) {
        state = stateInit.flatMap(t -> ack(m - 1, 1));
    } else {
        state = stateInit.flatMap(t -> ack(m, n - 1)).flatMap(t -> ack(m - 1, t));
    }

    return state.flatMap(
        t -> StateM.<FuncStat>get()
            .flatMap(tn -> StateM.<FuncStat>put(tn.decrDepth()))
            .flatMap(tn -> StateM.<Integer, FuncStat>unit(t))
    );
}
