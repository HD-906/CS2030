class Milk extends Ingredient {
    private static final String NAME = "milk";
    private static final Nutri NUTRI = 
    new Nutri("sugar", 4340)
    .update("fat", 2680);

    Milk(Drink drink) {
        super(NUTRI, drink);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
