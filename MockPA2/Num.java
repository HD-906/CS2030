import java.util.Optional;

class Num extends AbstractNum<Integer> {
    private Num() {
        super(Optional.<Integer>empty());
    }

    private Num(Optional<Integer> opt) {
        super(opt.filter(AbstractNum.valid));
    }

    private Num(int i) {
        super(i);
    }

    static Num of(int i) {
        if (AbstractNum.valid.test(i)) {
            return new Num(i);
        }
        return new Num();
    }

    static Num of(Optional<Integer> opt) {
        return new Num(opt);
    }

    static Num zero() {
        return new Num(AbstractNum.zero().opt);
    }

    static Num one() {
        return zero().succ();
    }

    public Num succ() {
        return new Num(this.opt.map(AbstractNum.s));
    }

    public Num add(Num n) {
        if (!(this.isValid() && n.isValid())) {
            return new Num();
        }

        Optional<Integer> optNum = this.opt;
        for (Num i = zero(); !i.equals(n); i = i.succ()) {
            optNum = optNum.map(AbstractNum.s);
        }
        return new Num(optNum);
    }

    public Num mul(Num n) {
        if (!(this.isValid() && n.isValid())) {
            return new Num();
        }

        if (this.equals(zero()) || n.equals(zero())) {
            return zero();
        }

        Num num = this;
        for (Num i = one(); !i.equals(n); i = i.succ()) {
            num = num.add(this);
        }
        return num;
    }

    private Optional<Integer> subPart(Num n) {
        if (!(this.isValid() && n.isValid())) {
            return Optional.<Integer>empty();
        }

        if (this.equals(zero())) {
            return n.opt;
        }

        Optional<Integer> optNum = this.opt.map(AbstractNum.n);
        for (Num i = zero(); !i.equals(n); i = i.succ()) {
            optNum = optNum.map(AbstractNum.s);
        }
        return optNum;
    }

    public Num sub(Num n) {
        Optional<Integer> optNum = subPart(n);
        return new Num(optNum.map(AbstractNum.n));
    }

    public Num subAbs(Num n) {
        Optional<Integer> optNum = subPart(n);
        Num res = new Num(optNum);
        if (res.isValid()) {
            return res;
        }
        return new Num(optNum.map(AbstractNum.n));
    }

    public Num min(Num n) {
        if (this.sub(n).isValid()) {
            return n;
        }
        return this;
    }

    public Num max(Num n) {
        if (this.sub(n).isValid()) {
            return this;
        }
        return n;
    }

    public Num div(Num n) {
        if (!(this.isValid() && n.sub(one()).isValid())) {
            return new Num();
        }

        if (n.equals(one())) {
            return this;
        }

        Num quot = zero();
        Num num = this.sub(n);

        while (num.isValid()) {
            num = num.sub(n);
            quot = quot.succ();
        }
        return quot;
    }

    public Num mod(Num n) {
        if (!(this.isValid() && n.sub(one()).isValid())) {
            return new Num();
        }

        if (n.equals(one())) {
            return zero();
        }

        Num num = this.sub(n);
        Num res = this;

        while (num.isValid()) {
            res = num;
            num = num.sub(n);
        }
        return res;
    }

    public Num hcf(Num n) {
        if (this.equals(zero()) || n.equals(zero())) {
            return this.max(n);
        }

        Num num = this;
        while (!(num.equals(n))) {
            Num numTemp = num.subAbs(n);
            n = num.min(n);
            num = numTemp;
        }
        return num;
    }

    public Num lcm(Num n) {
        return this.div(this.hcf(n)).mul(n);
    }
}
