class Topping extends Additive {
    private static final String FORMAT = "%s<%s";

    Topping(Nutri nutri, Drink drink) {
        super(nutri, drink);
    }

    @Override
    public String prep() {
        return String.format(FORMAT, super.prep(), this.toString());
    }
}
