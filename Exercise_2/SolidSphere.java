public class SolidSphere extends Sphere implements Solid {
    private static final String FORMAT = "solid-%s with a mass of %.2f";
    private final double density;

    SolidSphere(double radius, double density) {
        super(radius);
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
