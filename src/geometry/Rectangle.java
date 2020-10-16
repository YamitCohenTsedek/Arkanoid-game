package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents a rectangle which have an upper-left point, width and height.
 */
public class Rectangle {
    // Declare the members of the class.
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructor - create a new rectangle with an upper left point, width and height.
     * @param upperLeft the upper left point of the rectangle (the start point).
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Set the width of the rectangle.
     * @param blockWidth the width of the rectangle.
     */
    public void setWidth(int blockWidth) {
        this.width = blockWidth;
    }

    /**
     * Set the height of the rectangle.
     * @param blockHeight the height of the rectangle.
     */
    public void setHeight(int blockHeight) {
        this.height = blockHeight;
    }

    /**
     * @return the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Set a new upper left to the rectangle.
     * @param newPosition the new upper left point of the rectangle.
     */
    public void setUpperLeft(Point newPosition) {
        this.upperLeft = newPosition;
    }

    /**
     * @return the left edge of the rectangle.
     */
     public Line getLeftEdge() {
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        return new Line(this.upperLeft, lowerLeft);
    }

    /**
     * @return the right edge of the rectangle.
     */
    public Line getRightEdge() {
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        return new Line(upperRight, lowerRight);
    }

    /**
     * @return the upper edge of the rectangle.
     */
    public Line getUpperEdge() {
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        return new Line(this.upperLeft, upperRight);
    }

    /**
     * @return the lower edge of the rectangle.
     */
    public Line getLowerEdge() {
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
        return new Line(lowerLeft, lowerRight);
    }

    /**
     * @param line the line that it's checked whether it intersects with the rectangle.
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public List<Point> intersectionPoints(Line line) {
        // Define the list of the intersection points with the line.
        List<Point> intersectionPoints = new ArrayList<>();
        // Find the edges of the rectangle
        Line leftEdge = getLeftEdge();
        Line rightEdge = getRightEdge();
        Line upperEdge = getUpperEdge();
        Line lowerEdge = getLowerEdge();
        // Add the intersection points of the given line with the edges of the rectangle to the list.
        if (line.isIntersecting(leftEdge)) {
            intersectionPoints.add(line.intersectionWith(leftEdge));
        }
        if (line.isIntersecting(rightEdge)) {
            intersectionPoints.add(line.intersectionWith(rightEdge));
        }
        if (line.isIntersecting(upperEdge)) {
            intersectionPoints.add(line.intersectionWith(upperEdge));
        }
        if (line.isIntersecting(lowerEdge)) {
            intersectionPoints.add(line.intersectionWith(lowerEdge));
        }
        return intersectionPoints;
    }

} // class Rectangle
