package sprites;
import java.awt.Color;

import arkanoidgame.GameEnvironment;
import arkanoidgame.Velocity;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import levels.GameLevel;

/**
 * The Ball class represents a ball that has a center, radius, color, velocity, and surface boundaries.
 * The ball also has a game environment of obstacles it can collide with.
 */
public class Ball implements Sprite {
    // Declare the members of the class.
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;

    /**
     * Constructor.
     * @param center the center of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
    }

    /**
     * @param gameEnv a game environment of obstacles the ball can collide with.
     */
    public void setGameEnvironment(GameEnvironment gameEnv) {
        this.gameEnvironment = gameEnv;
    }

    /**
     * @return the x value of the center of the ball.
     */
    public double getX() {
        return this.center.getX();
    }

    /**
     * @return the y value of the center of the ball.
     */
    public double getY() {
        return this.center.getY();
    }

    /**
     * @return the size (the radius) of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * @return the current velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * @param v a new velocity for the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * @param dx the dx value of the velocity (the change on the x-axis).
     * @param dy the dy value of the velocity (the change on the y-axis).
     */
    public void setVelocity(double dx, double dy) {
         this.velocity = new Velocity(dx, dy);
     }

    /**
     * @return the center point of the ball.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * @param newCenter the new center of the ball.
     */
    public void setCenter(Point newCenter) {
        this.center = newCenter;
    }

    /**
     * @param ballColor the new color to set to the ball.
     */
    public void setColor(Color ballColor) {
        this.color = ballColor;
    }

    /**
     * @param rec the rectangle that it's checked whether the x or the y values of the
     * center of the ball are in its range.
     * @return true if the x or the y values of the center of the ball are in the range of the given rectangle,
     * false otherwise
     */
    private boolean pointIsInRec(Rectangle rec) {
        return rec.getUpperEdge().isInXRange(this.center.getX())
            || rec.getLeftEdge().isInYRange(this.center.getY());
    }

    /**
     * Do a move almost to the point.
     * @param hitP the point from which the move is made.
     * @return the new point the ball has reached to.
     */
    public Point almostHit(Point hitP) {
        // Epsilon helps to move the ball "almost" to the hit point, in a case that there is a hit between
        // the ball and one of the obstacles.
        final double epsilon = 0.07;
        double newCenterX;
        double newCenterY;
        if (this.velocity.getDx() <= 0) {
            newCenterX = hitP.getX() + epsilon;
        } else {
            newCenterX = hitP.getX() - epsilon;
        }
        if (this.velocity.getDy() <= 0) {
            newCenterY = hitP.getY() + epsilon;
        } else {
            newCenterY = hitP.getY() - epsilon;
        }
        // Promote the ball "almost" to the hit point, but just slightly before it.
        return new Point(newCenterX, newCenterY);
    }

    /**
     * Perform the hit of the ball with the collidable.
     * @param closestCollision the closest collision point of the collidable with the ball's trajectory.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    private void hitWithCollidable(CollisionInfo closestCollision, double dt) {
        Collidable collidable = closestCollision.collisionObject();
        // Find the collision point between the paddle and the trajectory of the ball.
        Point collisionPoint = closestCollision.collisionPoint();
        double dx = getVelocity().getDx();
        double dy = getVelocity().getDy();
        double directionDx;
        double directionDy;
        double newCenterX;
        double newCenterY;
        // Find the direction of the dx & dy values (negative/positive).
        if (dx < 0) {
            directionDx = -1;
        } else {
            directionDx = 1;
        }
        if (dy < 0) {
            directionDy = -1;
        } else {
            directionDy = 1;
        }
        // The new center of the ball according to the compatible directions.
        this.setCenter(collisionPoint);
        // Get to the hit point, using the radius of the ball and the directions.
        newCenterX =  collisionPoint.getX() - (this.getSize() * directionDx);
        newCenterY =  collisionPoint.getY() - (this.getSize() * directionDy);
        this.center = new Point(newCenterX, newCenterY);
        // Perform the hit with the collidable.
        this.setVelocity(collidable.hit(this, collisionPoint, this.getVelocity(), dt));
    }

    /**
     * Promote the ball in one step depending on its velocity.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {
        Colors colors = new Colors();
        this.setColor(colors.setRainbowColor(-1));
        // Find the end point of the current trajectory of the ball.
        Point endOfTrajectory = this.velocity.applyToPoint(this.center, dt);
        // Find the current trajectory of the ball (the movement from the center to the next step).
        Line trajectory = new Line(this.getCenter(), endOfTrajectory);
        // Get the information about the collision.
        CollisionInfo closestCollision = this.gameEnvironment.getClosestCollision(trajectory);
        // If moving on this trajectory will not hit anything.
        if (closestCollision.collisionPoint() == null) {
            // Promote the ball by the regular velocity.
            this.setCenter(this.getVelocity().applyToPoint(this.center, dt));
        // Otherwise (there is a hit), perform the hit with the collidable (the paddle or the block).
        } else {
            hitWithCollidable(closestCollision, dt);
       }
} // moveOneStep

    /**
     * Draw the ball on the given surface.
     * @param surface the draw surface that the ball should be drawn on.
     */
    public void drawOn(DrawSurface surface) {
        // Draw the ball on the given draw surface.
        surface.setColor(this.color);
        surface.fillCircle((int) this.getX(), (int) this.getY(), this.getSize());
    }

    /**
     * Notify the ball that time has passed.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        // Move the ball one step.
        this.moveOneStep(dt);
    }

    /**
     * Add the ball to the game, calling the appropriate game methods.
     * @param g the game that the ball should be added to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove the current ball from the game.
     * @param game the game that the ball should be removed from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }

} // class Ball