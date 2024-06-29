class Ingredient extends Additive {
    private static final String FORMAT = "%s>%s";

    Ingredient(Nutri nutri, Drink drink) {
        super(nutri, drink);
    }

    @Override
    public String prep() {
        return String.format(FORMAT, this.toString(), super.prep());
    }
}
