import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Main {
    static IntStream twinPrimes(int n) {
        return IntStream.iterate(2 + 1, i -> i <= n, i -> i + 2)
            .filter(Main::isPrime)
            .filter(e -> isPrime(e + 2) || isPrime(e - 2));
    }
    
    private static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int)Math.sqrt(n))
            .allMatch(divisor -> n % divisor != 0);
    }

    static String reverse(String str) {
        return Stream.<String>of(str.split(""))
            .parallel()
            .reduce("", (a, b) -> b + a);
    }

    static int countRepeats(List<Integer> list) {
        return (int) IntStream.range(0, list.size() - 1)
            .filter(i -> 
                list.get(i).equals(list.get(i + 1)) && 
                !(i > 0 && list.get(i).equals(list.get(i - 1)))
            )
            .count();
    }

    private static Stream<String> gol(List<Integer> lst, UnaryOperator<List<Integer>> rule, int n) {
        Stream<String> listStr = Stream.of(
            lst.stream().map(x -> x != 0 ? "x" : ".").parallel().reduce("", (a, b) -> a + b));
        if (n > 1) {
            return Stream.concat(listStr, gol(rule.apply(lst), rule, n - 1));
        }
        return listStr;
    }

    static Stream<String> gameOfLife(List<Integer> list, UnaryOperator<List<Integer>> rule, int n) {
        // All process written in another private method instead because
        // recusively calling this method causes an unknown compilation error on CodeCrunch
        return gol(list, rule, n);
    }

    static UnaryOperator<List<Integer>> generateRule() {
        return list -> IntStream.range(0, list.size())
            .mapToObj(n -> {
                int left = (n > 0) ? list.get(n - 1) : 0;
                int right = (n < list.size() - 1) ? list.get(n + 1) : 0;
                return (list.get(n) == 0 && (left ^ right) == 1) ? 1 : 0;
            })
            .collect(Collectors.toList());
    }
}
