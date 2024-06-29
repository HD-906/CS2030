public class Sphere implements Shape3D {
    private static final String FORMAT = "sphere [%.2f]";
    private static final double NOT_A_MAGIC_NUMBER = 4.0 * Math.PI / 3.0;
    private final double radius;

    Sphere(double radius) {
        this.radius = radius;
    }

    public double volume() {
        return NOT_A_MAGIC_NUMBER * this.radius * this.radius * this.radius;
    }

    @Override
    public String toString() {
        return String.format(FORMAT, this.radius);
    }
}
