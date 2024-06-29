static final double EPSILON = 1E-15;

Circle createUnitCircle(Point p, Point q) {
    Point m = p.midPoint(q);
    double theta = p.angleTo(q);
    double mc = Math.sqrt(1.0 - Math.pow(m.distanceTo(p), 2.0));
    return new Circle(m.moveTo(theta + Math.PI / 2.0, mc), 1.0);
}

int findCoverage(Circle c, ImList<Point> points) {
    int coverage = 0;
    for (Point point : points) {
        if (c.contains(point)) {
            coverage += 1;
        }
    }
    return coverage;
}

int findMaxDiscCoverage(ImList<Point> points) {
    int maxDiscCoverage = 0;
    int numOfPoints = points.size();

    for (int i = 0; i < numOfPoints - 1; i++) {
        for (int j = i + 1; j < numOfPoints; j++) {
            Point p = points.get(i);
            Point q = points.get(j);
            double distPQ = p.distanceTo(q);
            if (distPQ < 2.0 + EPSILON && distPQ > 0) {
                Circle c = createUnitCircle(p, q);
                int coverage = findCoverage(c, points);
                if (coverage > maxDiscCoverage) {
                    maxDiscCoverage = coverage;
                }
            }
        }
    }
    return maxDiscCoverage;
}
