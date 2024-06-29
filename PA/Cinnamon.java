class Cinnamon extends Topping {
    private static final String NAME = "cinnamon";
    private static final Nutri NUTRI = 
    new Nutri("sugar", 150)
    .update("fat", 220);

    Cinnamon(Drink drink) {
        super(NUTRI, drink);
    }

    @Override
    public String toString() {
        return NAME;
    }
}
