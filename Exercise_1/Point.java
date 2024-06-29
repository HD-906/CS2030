public class Point {
    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point other) {
        double dX = this.x - other.x;
        double dY = this.y - other.y;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public double angleTo(Point other) {
        double dX = other.x - this.x;
        double dY = other.y - this.y;
        return Math.atan2(dY, dX);
    }

    public Point midPoint(Point other) {
        double xMid = (this.x + other.x) / 2;
        double yMid = (this.y + other.y) / 2;
        return new Point(xMid, yMid);
    }

    public Point moveTo(double theta, double dist) {
        double xNew = this.x + dist * Math.cos(theta);
        double yNew = this.y + dist * Math.sin(theta);
        return new Point(xNew, yNew);
    }

    @Override
    public String toString() {
        return String.format("point (%.3f, %.3f)", x, y);
    }
}
