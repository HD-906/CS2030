public class Loader {
    private static final String FORMAT = "Loader #%d";
    private final int id;

    Loader(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, this.id);
    }
}
