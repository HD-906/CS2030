Log<Integer> sum(int n) {
    if (n == 0) {
        return Log.<Integer>of(n, "hit base case!");
    } else {
        return sum(n - 1).flatMap(
            i -> Log.<Integer>of(
                i + n, String.format("adding %d", n)
            )
        );
    }
}

Log<Integer> f(int n) {
    if (n == 1) {
        return Log.<Integer>of(n, "1");
    } else if (n % 2 == 0) {
        return Log.<Integer>of(n, String.format("%d / 2", n))
            .flatMap(i -> f(i / 2));
    } else {
        return Log.<Integer>of(n, String.format("3(%d) + 1", n))
            .flatMap(i -> f(3 * i + 1));
    } 
}