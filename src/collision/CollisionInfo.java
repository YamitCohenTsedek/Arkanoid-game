package collision;

import geometry.Point;

/**
 * The CollisionInfo class holds the information about the collision of the ball with the collidable object.
 */
public class CollisionInfo {
    // Declare the members of the class.
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * @param collisionPoint the collision point of the ball with the object.
     * @param collisionObject the collidable object that the ball collided with.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the collision point of the ball with the object.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object that the ball collided with.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}