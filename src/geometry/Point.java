package geometry;

/**
 * The Point class represents a point in an axis system. A point has x and y values.
 */
public class Point {
    // Declare the members of the class.
    private double x;
    private double y;

    /**
     * Constructor.
     * @param x the x value of the point.
     * @param y the y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other another point that the distance between it and the current point should be calculated.
     * @return the distance between the current point and the other point.
     * @throws RuntimeException - throw an error if the other point is null.
     */
    public double distance(Point other) throws RuntimeException {
        if (other == null) {
            throw new RuntimeException("The given point doesn't exist (NULL).");
        } else {
        // Find the difference between the x and y values of the points.
        double differenceX = this.x - other.x;
        double differenceY = this.y - other.y;
        // Calculate the distance between the points using the distance formula and return it.
        return Math.sqrt(differenceX * differenceX + differenceY * differenceY);
        }
    }

    /**
     * @param other another point that will be compared to the current point.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        /*
         * Epsilon is used to allow some tolerance in the comparison, since numeric computations involving floating
         * point arithmetic may be imprecise.
         */
        double epsilon = 0.007;
        double smallX = Math.min(this.x, other.getX());
        double bigX = Math.max(this.x, other.getX());
        double smallY = Math.min(this.y, other.getY());
        double bigY = Math.max(this.y, other.getY());
        // Equal points are points that have the same x and y value.
        return ((smallX + epsilon) >= bigX && (smallY + epsilon) >= bigY);
    }

    /**
     * @return the x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * @return the y value of the point.
     */
    public double getY() {
        return this.y;
    }

} //class Point