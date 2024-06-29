public class SolidCuboid extends Cuboid implements Solid {
    private static final String FORMAT = "solid-%s with a mass of %.2f";
    private final double density;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
        this.density = density;
    }

    public double mass() {
        return volume() * this.density;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, super.toString(), mass());
    }
}
