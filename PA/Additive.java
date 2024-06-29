class Additive implements Drink {
    private final Nutri nutri;
    private final String prepName;

    Additive(Nutri nutri, Drink drink) {
        this.nutri = nutri.update(drink.value());
        this.prepName = drink.prep();
    }

    public Nutri value() {
        return this.nutri;
    }

    public String prep() {
        return this.prepName;
    }
}
