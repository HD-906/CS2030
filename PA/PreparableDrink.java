abstract class PreparableDrink {
    private static final String FORMAT = "[%s]";
    
    public String prep() {
        return String.format(FORMAT, this.toString());
    }
}
