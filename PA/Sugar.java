class Sugar extends Ingredient {
    private static final String NAME = "sugar";
    private static final Nutri NUTRI = 
    new Nutri("sugar", 8000);

    Sugar(Drink drink) {
        super(NUTRI, drink);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
