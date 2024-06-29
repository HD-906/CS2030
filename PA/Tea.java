class Tea extends PreparableDrink implements Drink {
    private static final String NAME = "tea";
    private static final Nutri NUTRI = new Nutri("caffeine", 50);

    public Nutri value() {
        return NUTRI;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
