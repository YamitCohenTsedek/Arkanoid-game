package collision;

import levels.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * The BallRemover class is in charge of removing balls and updating a counter of the available balls.
 */
public class BallRemover implements HitListener {
    // Declare the members of the class.
    private GameLevel gameLevel;

    /**
     *  Constructor.
     *  @param gameLevel the level that the ball should be removed from.
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // When a ball reaches the bottom of the screen, it is removed from the game.
        if (beingHit.getHitListenersList().contains(this)) {
            hitter.removeFromGame(this.gameLevel);
            gameLevel.getBallsCounter().decrease(1);
        }
   }

} // class BallRemover