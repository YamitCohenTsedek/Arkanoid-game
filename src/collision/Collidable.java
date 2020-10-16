package collision;
import arkanoidgame.Velocity;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;

/**
 * The Collidable interface represents all the objects that the ball can collide with.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that the ball collided with at collisionPoint with a given velocity.
     * @param hitter the ball that collided with the object.
     * @param collisionPoint the collision point of the ball with the object.
     * @param currentVelocity the current velocity at which the ball collided with the object.
     * @return the new velocity expected after the hit (based on the force the object inflicted on the ball).
     * @param dt specifies the number of seconds passed since the last call.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity, double dt);
}