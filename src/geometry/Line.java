package geometry;

/**
 * The Line class represents a line segment in an axis system. A line has a start point and an end point.
 */
public class Line {
    // Declare the members of the class.
    private Point start;
    private Point end;

    /**
     * Constructor (first type).
     * @param start the start point of the line.
     * @param end the end point of the line.
     */
    public Line(Point start, Point end) {
        /*
         * Set the leftmost point (the point with the minimal x value) to be the start point,
         * and the rightmost point to be the end point.
         */
        if (Math.min(start.getX(), end.getX()) == start.getX()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }

    /**
     * Constructor (second type).
     * @param x1 the x value of the start point of the line.
     * @param y1 the y value of start point of the line.
     * @param x2 the x value of the end point of the line.
     * @param y2 the y value of the end point of the line.
     */
    public Line(double x1, double y1, double x2, double y2) {
        /*
         * Set the leftmost point (the point with the minimal x value) to be the start point,
         * and the rightmost point to be the end point.
         */
        if (Math.min(x1, x2) ==  x1) {
            this.start = new Point(x1, y1);
            this.end = new Point(x2, y2);
        } else {
            this.start = new Point(x2, y2);
            this.end = new Point(x1, y1);
        }
    }

    /**
     * @return the length of the given line.
     */
    public double length() {
         // The length of a line is calculated as the distance between the start point and the end point of the line.
        return this.start.distance(this.end);
   }

    /**
     * @return the middle point of the given line.
     */
    public Point middle() {
        // middleX: (the x value of the start point + the x value of end point) / 2
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        // middleY: (the y value of the start point + the y value of end point) / 2
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(middleX, middleY);
    }

    /**
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * @param other another line that it is checked whether it's parallel to y-axis.
     * @return 1 if both of the lines are parallel to y-axis, 2 if only the current line is parallel to y-axis,
     * 3 if only the other line is parallel to y-axis, and 4 if neither the current line nor the other line are
     * parallel to y-axis.
     */
    private int isParallelToY(Line other) {
        // If the current line is parallel to y axis (the x values of the start point and the end point are equal).
        if (this.start.getX() == this.end.getX()) {
            // If the other line is also parallel to y axis, return 1.
            if (other.start.getX() == other.end.getX()) {
                return 1;
            // Else - the current line is parallel to y-axis and the other line is not parallel to y-axis, return 2.
            } else {
                return 2;
            }
        // Else - if the current line is not parallel to y-axis, but the other is parallel to y-axis, return 3.
        } else if (other.start.getX() == other.end.getX()) {
            return 3;
        // Else - neither the current line nor the other line are parallel to y axis, return 4.
        } else {
            return 4;
        }
    } // isParallelToY

   /**
    * @return the slope of a line
    */
    private double slope() {
        // Return the slope of the given line (by using the formula: m = (y2-y1)/(x2-x1) ).
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     * @param x the x value of the point that it's checked whether it's in the segment.
     * @param y the y value of the point that it's checked whether it's in the segment.
     * @return true if the point is in the segment, false otherwise.
     */
    public boolean isInTheSegment(double x, double y) {
        /*
         * Epsilon is used to allow some tolerance in the comparison, since numeric computations involving floating
         * point arithmetic may be imprecise.
         */
        double epsilon = 0.007;
        // If the line is increasing or horizontal (the y value of the end point >= the y value of the start point).
        if (this.start.getY() <= this.end.getY()) {
            // The point should be bigger than the start point and smaller than the end point.
            return this.start.getX() - epsilon <= x && this.start.getY() - epsilon <= y
            && this.end.getX() + epsilon >= x && this.end.getY() + epsilon >= y;
        // Else - the line is decreasing (the y value of the end point < the y value of the start point).
        } else {
            // The point should be smaller than the start point and bigger than the end point.
            return this.start.getX() - epsilon <= x && this.start.getY() + epsilon >= y
            && this.end.getX() + epsilon >= x && this.end.getY() + epsilon <= y;
        }
    } // isInTheSegment

    /**
     * @param x the x value of the point that it's checked whether it's in the x-range of the line.
     * @return true if the point is in the range, false otherwise.
     */
    public boolean isInXRange(double x) {
        double epsilon = 0.007;
        return this.start.getX() - epsilon <= x && x <= this.end.getX() + epsilon;
    }

    /**
     * @param y the y value of the point that it's checked whether it's in the y-range of the line.
     * @return true if the point is in the range, false otherwise.
     */
    public boolean isInYRange(double y) {
        double epsilon = 0.007;
        // If the line is increasing (the y value of the start point <= the y value of the end point).
        if (this.start.getY() <= this.end.getY()) {
            return this.start.getY() - epsilon <= y && y <= this.end.getY() + epsilon;
            // Else - the line is decreasing (the y value of the end point < the y value of the start point).
        } else {
            return this.start.getY() + epsilon >= y && y >= this.end.getY() - epsilon;
        }
    }

   /**
    * @param other another line that it's checked what is the intersection point between it and the current line,
    * if the lines intersect.
    * @return the intersection point if the lines intersect, null otherwise.
    */
    private Point interCurrentParallelToY(Line other) {
        /*
         * The current line is parallel to y axis (it also may be a single point), and the other line is not.
         * Calculate what is the intersection point between the lines, if the lines intersect.
         * If they do intersect, check also whether the intersection points are in the segments.
         */

        // The equation of the current line is x=b1 (parallel to y-axis).
        double b1 = this.start.getX();
        // The equation of the other line is y=m2x+b2.
        double x2 = other.start.getX(); // The x value of the point that is on the line.
        double y2 = other.start.getY(); // The y value of the point that is on the line.
        double m2 = other.slope(); // The slope of the line.
        double b2 = y2 - m2 * x2; // The y value of the intersection with y-axis.
        // To find the x value of the intersection point, the equation is: b1=m2x+b2 --->  x=(b1-b2)/m2.
        double intersectionX = b1;
        // To find the y value of the intersection point, the equation is: y=m2x+b2.
        double intersectionY = m2 * intersectionX + b2;
        // Check whether the intersection point of the lines is also in the segments.
        if (this.isInTheSegment(intersectionX, intersectionY)
            && other.isInTheSegment(intersectionX, intersectionY)) {
                return new Point(intersectionX, intersectionY);
        // Else - the intersection point of the lines is not in the segments, return null.
        } else {
            return null;
        }
    } // interCurrentParallelToY

    /**
     * @param other the other line that it will be checked what is the intersection point
     * between it and the current point, if the lines intersect.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    private Point interOtherParallelToY(Line other) {
        /*
         * The other line is parallel to y axis (it also can be a single point), and the current line is not.
         * The method calculates what is the intersection point between the lines, if the lines intersect.
         * If they do, it also checks whether the intersection points is in the segments.
         */
        // The equation of the other line is x=b2 (parallel to y axis).
        double b2 = other.start.getX();
        // The equation of the current line is y=m1x+b1.
        double x1 = this.start.getX(); // x value of the point that is on the line.
        double y1 = this.start.getY(); // y value of the point that is on the line.
        double m1 = this.slope(); // the slope of the line.
        double b1 = y1 - m1 * x1; //the y value of the intersection with y axis.
        // In order to find the x value intersection point, the equation is: b2=m1x+b1 --->  x=(b2-b1)/m1.
        double intersectionX = b2;
        // In order to find the y value intersection point, the equation is: y=m1x+b1.
        double intersectionY = m1 * intersectionX + b1;
        // Check whether the intersection point of the lines is also in the segments.
        if (this.isInTheSegment(intersectionX, intersectionY)
            && other.isInTheSegment(intersectionX, intersectionY)) {
                return new Point(intersectionX, intersectionY);
        } else {
            // else the intersection point of the lines is not in the segments, return null.
            return null;
        }
    } // interOtherParallelToY

    /**
     * @param other the other line that it will be checked what is the intersection point between it and the current
     * point, if the lines intersect.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    private Point interDifferentSlopes(Line other) {
        if (this.slope() != other.slope()) {
            // The equation line of the current line: y=m1x+b1
            double x1 = this.start.getX();
            double y1 = this.start.getY();
            double m1 = this.slope();
            double b1 = y1 - m1 * x1;
            // The equation line of the current line: y=m2x+b2
            double x2 = other.start.getX();
            double y2 = other.start.getY();
            double m2 = other.slope();
            double b2 = y2 - m2 * x2;
            /*
             * In order to find the x value intersection point,
             * the equation is: m1x+b1=m2x+b2 --->  x=(b2-b1)/(m1-m2).
             */
            double intersectionX = (b2 - b1) / (m1 - m2);
            // In order to find the y value intersection point, the equation is: y=m1x+b1.
            double intersectionY = m1 * intersectionX + b1;
            if (this.isInTheSegment(intersectionX, intersectionY)
            && other.isInTheSegment(intersectionX, intersectionY)) {
                return new Point(intersectionX, intersectionY);
            // else- the intersection point of the lines is not in the segments, return null.
            } else {
                return null;
            }
        // else- the lines don't have the same slope, they don't intersect - return null.
        } else {
            return null;
        }
    } // interDifferentSlopes

    /**
     * @param other the other line it's checked what is the intersection point between it and the current point,
     * if the lines intersect.
     * @return the intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        // First, check whether (at least) one of the lines is parallel to y-axis.
        int result = this.isParallelToY(other);
        // If both the current and the other line are parallel to y-axis.
        if (result == 1) {
            // The lines intersect only if both of them are points and they both are the same point.
            if (this.start.equals(this.end) && this.equals(other)) {
                return this.start;
            // Else - the lines don't intersect, return null.
            } else {
                return null;
            }
        // Else - if the current line is parallel to y-axis and the other line is not parallel to y-axis.
        } else if (result == 2) {
            // Use interCurrentParallelToY method, to check whether the lines intersect in this case.
            return this.interCurrentParallelToY(other);
        // Else - if the current line is not parallel to y-axis and the other line is parallel to y-axis.
        } else if (result == 3) {
            // Use interOtherParallelToY method to check whether the lines intersect in this case.
            return this.interOtherParallelToY(other);
        // Else - neither the current line nor the other line are parallel to y-axis.
        } else {
            /*
             * Only if the lines don't have the same slope, they can intersect.
             * If the lines intersect and the intersection point is not in the segments, return null.
             * If the lines don't have the same slope, they don't intersect, return null.
             */
            return (interDifferentSlopes(other));
        }
    } // intersectionWith

    /**
     * @param other another line that it's checked whether it intersects to the current line.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        /*
         * Return true if the lines intersect, false otherwise. Uses the intersectionWith method - if it returns
         * a point return true, otherwise - return false.
         */
        return this.intersectionWith(other) != null;
    }

    /**
     * @param other another line that it's checked whether it's equal to the current line.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        // The lines are equal if both their start and their end points are equal.
        return (this.start.equals(other.start) && this.end.equals(other.end));
    }

    /**
     * @param rect the rectangle that it's checked whether the current line intersects with.
     * @param edge the edge of the rectangle that it's checked whether the current line intersects with.
     * @param closestInterPoint - the current closest intersection point.
     * @return the closest intersection point to the start of the line, if they intersect.
     * If the given line does not intersect with the rectangle, return null.
     */
    private Point isCloserInterPoint(Rectangle rect, Line edge, Point closestInterPoint) {
        Point intersectionPoint;
        double distanceToInter;
        // If the given edge intersects with the line
        if (this.isIntersecting(edge)) {
            // If there is no closest intersection point yet.
            if (closestInterPoint == null) {
                // Return the intersection point with the given edge. In the worst case it will also be null,
                // and in the best case there will be a collision.
                return this.intersectionWith(edge);
            // If the closest point is not null, check whether the collision with the given edge is closer.
            } else {
                double distanceToClosest = this.start.distance(closestInterPoint);
                intersectionPoint = this.intersectionWith(edge);
                distanceToInter = this.start().distance(intersectionPoint);
                // If the collision with the given edge is closer - return it.
                if (distanceToInter < distanceToClosest) {
                    return intersectionPoint;
                // Else - return the original closest collision that was sent as an argument.
                } else {
                     return closestInterPoint;
                }
            }
        // Else - the given edge doesn't intersect with the line, return the original closest collision.
        } else {
            return closestInterPoint;
        }
    } // isCloserInterPoint

    /**
     * @param rect the rectangle that it's checked whether it intersects with the current line, and if they do,
     * it's checked what is the closest intersection point to the start of the current line.
     * @return the closest intersection point to the start of the line, if the rectangle and the line intersect.
     * If the line doesn't intersect with the rectangle, return null.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // Get the lengths of the rectangle.
        Line leftEdge = rect.getLeftEdge();
        Line rightEdge = rect.getRightEdge();
        Line upperEdge = rect.getUpperEdge();
        Line lowerEdge = rect.getLowerEdge();
        // Initialize the closest point to be the intersection with the left edge (it may be null).
        Point closestInterPoint = this.intersectionWith(leftEdge);
        /*
         *  Check which intersection point between the rectangle and the line is the closest to the start of
         *  the line (if they intersect).
         */
        if (closestInterPoint == null) {
            closestInterPoint = this.intersectionWith(rightEdge);
        } else {
            closestInterPoint =  isCloserInterPoint(rect, rightEdge, closestInterPoint);
        }
        if (closestInterPoint == null) {
            closestInterPoint = this.intersectionWith(upperEdge);
        } else {
            closestInterPoint =  isCloserInterPoint(rect, upperEdge, closestInterPoint);
        }
        if (closestInterPoint == null) {
            closestInterPoint = this.intersectionWith(lowerEdge);
        } else {
            closestInterPoint =  isCloserInterPoint(rect, lowerEdge, closestInterPoint);
        }
        return closestInterPoint;
    }  // closestIntersectionToStartOfLine

} // class Line