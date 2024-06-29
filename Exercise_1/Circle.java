public class Circle {
    private static final double EPSILON = 1E-15;

    private final Point centre;
    private final double radius;

    Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }
    
    public boolean contains(Point p) {
        return this.centre.distanceTo(p) < this.radius + EPSILON;
    }

    @Override
    public String toString() {
        String s = String.format("circle of radius %.1f centred at ", this.radius);
        return s + this.centre;
    }

    public static Circle createUnitCircle(Point p, Point q) {
        Point m = p.midPoint(q);
        double theta = p.angleTo(q);
        double mc = Math.sqrt(1.0 - Math.pow(m.distanceTo(p), 2.0));
        return new Circle(m.moveTo(theta + Math.PI / 2.0, mc), 1.0);
    }
}
