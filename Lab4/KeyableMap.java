import java.util.Map;
import java.util.Optional;

class KeyableMap<V extends Keyable> implements Keyable {
    private static final String FORMAT = "%s: {%s}";
    private final String name;
    private final ImmutableMap<String, V> v;

    KeyableMap(String name) {
        this.name = name;
        this.v = new ImmutableMap<>();
    }

    KeyableMap(KeyableMap<V> prev, V v) {
        this.name = prev.name;
        this.v = prev.v.put(v.getKey(), v);
    }

    public KeyableMap<V> put(V v) {
        return new KeyableMap<>(this, v);
    }

    public Optional<V> get(String name) {
        return this.v.get(name);
    }

    public String getKey() {
        return this.name;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (Map.Entry<String, V> e: v) {
            if (s != "") {
                s += ", ";
            }
            s += e.getValue().toString();
        }

        return String.format(FORMAT, this.name, s);
    }
}
