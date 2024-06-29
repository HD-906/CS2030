class Coffee extends PreparableDrink implements Drink {
    private static final String NAME = "coffee";
    private static final Nutri NUTRI = new Nutri("caffeine", 100);

    public Nutri value() {
        return NUTRI;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
