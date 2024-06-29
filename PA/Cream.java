class Cream extends Topping {
    private static final String NAME = "cream";
    private static final Nutri NUTRI = 
    new Nutri("sugar", 2000)
    .update("fat", 10000);

    Cream(Drink drink) {
        super(NUTRI, drink);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
