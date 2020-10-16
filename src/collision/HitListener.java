package collision;

import sprites.Ball;
import sprites.Block;

/**
 * The HitListener interface is an interface in which objects that want to be notified of hit events should implement,
 * and register themselves to a HitNotifier object, using its addHitListener method.
 */
public interface HitListener {
    /**
     * @param beingHit the method is called whenever the beingHit object is hit.
     * @param hitter the ball that hit the object
     */
    void hitEvent(Block beingHit, Ball hitter);
}