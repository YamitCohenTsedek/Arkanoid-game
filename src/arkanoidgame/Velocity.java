package arkanoidgame;

import geometry.Point;

/**
 * The Velocity class represents the velocity of an object.
 * Velocity is the rate of change of the position with respect to a frame of reference.
 * dx is the change in position on the x-axis, and dy is the change in position on the y-axis.
 * The start point of the axis system (0,0) on the screen is the top left corner, thus the y-axis is upside down.
 */
public class Velocity {
    // Declare the members of the class.
    private double dx;
    private double dy;

    /**
     * Constructor (the first way to set the velocity).
     * @param dx the dx value of the velocity (the change on the x-axis).
     * @param dy the dy value of the velocity (the change on the y-axis).
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Set the velocity by trigonometric calculations (the second way to set the velocity).
     * This static method creates new velocities, instead of the Velocity constructor.
     * @param angle the degrees represent the direction of the movement (assuming up is angle 0).
     * @param speed the speed of the object.
     * @return the velocity that calculated with trigonometry.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        // Calculate dx and dy values by trigonometry.
        double dx = speed * Math.sin(angle);
        // Multiply dy by -1 since the y-axis is upside down.
        double dy = (-1) * speed * Math.cos(angle);
        return new Velocity(dx, dy);
     }

    /**
     * @return the dx value of the velocity.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the dy value of the velocity.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * @param p the point from which the movement is made.
     * @param dt specifies the number of seconds passed since the last call.
     * @return the new point the object has reached to.
     * @throws RuntimeException - throw an error if the given point is null.
     */
    public Point applyToPoint(Point p, double dt) throws RuntimeException {
        if (p == null) {
             throw new RuntimeException("The given point doesn't exist (NULL).");
        } else {
            // Given the point (x,y), return the point (x+dx, y+dy).
            double newX = p.getX() + this.getDx() * dt;
            double newY = p.getY() + this.getDy() * dt;
            return new Point(newX, newY);
        }
    }
} // class Velocity
