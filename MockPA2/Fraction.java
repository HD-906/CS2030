import java.util.Optional;
import java.util.function.Function;

class Fraction extends AbstractNum<Frac> {
    private Fraction() {
        super(Optional.<Frac>empty());
    }

    private Fraction(Num numN, Num numD) {
        super(Frac.of(numN, numD));
    }

    static Fraction of(int n, int d) {
        return of(Num.of(n), Num.of(d));
    }

    static Fraction of(Num numN, Num numD) {
        if (!(numN.isValid() && numD.sub(Num.one()).isValid())) {
            return new Fraction();
        }
        return new Fraction(numN, numD);
    }

    public Fraction reduce() {
        Function<Frac, Optional<Integer>> first = f -> f.first().opt;
        Function<Frac, Optional<Integer>> second = f -> f.second().opt;
        Num n = Num.of(this.opt.flatMap(first));
        Num d = Num.of(this.opt.flatMap(second));
        Num hcf = n.hcf(d);
        return new Fraction(n.div(hcf), d.div(hcf));
    }

    public Fraction add(Fraction other) {
        Function<Frac, Optional<Integer>> first = f -> f.first().opt;
        Function<Frac, Optional<Integer>> second = f -> f.second().opt;
        Num n = Num.of(this.opt.flatMap(first));
        Num d = Num.of(this.opt.flatMap(second));
        Num otherN = Num.of(other.opt.flatMap(first));
        Num otherD = Num.of(other.opt.flatMap(second));
        
        return of(n.mul(otherD).add(d.mul(otherN)), d.mul(otherD));
    }

    public Fraction mul(Fraction other) {
        Function<Frac, Optional<Integer>> first = f -> f.first().opt;
        Function<Frac, Optional<Integer>> second = f -> f.second().opt;
        Num n = Num.of(this.opt.flatMap(first));
        Num d = Num.of(this.opt.flatMap(second));
        Num otherN = Num.of(other.opt.flatMap(first));
        Num otherD = Num.of(other.opt.flatMap(second));
        
        return of(n.mul(otherN), d.mul(otherD));
    }

    public Fraction sub(Fraction other) {
        Function<Frac, Optional<Integer>> first = f -> f.first().opt;
        Function<Frac, Optional<Integer>> second = f -> f.second().opt;
        Num n = Num.of(this.opt.flatMap(first));
        Num d = Num.of(this.opt.flatMap(second));
        Num otherN = Num.of(other.opt.flatMap(first));
        Num otherD = Num.of(other.opt.flatMap(second));
        
        return of(n.mul(otherD).sub(d.mul(otherN)), d.mul(otherD));
    }
}
