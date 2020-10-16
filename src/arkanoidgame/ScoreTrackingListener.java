package arkanoidgame;

import collision.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The ScoreTrackingListener class updates the counter of the blocks of the game
 * when blocks are being hit and removed.
 */
public class ScoreTrackingListener implements HitListener {
    // Declare the members of the class.
    private Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter the counter of the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Hitting a block is worth 5 points, and destroying a block is worth additional 10 points.
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() == 1) {
            this.currentScore.increase(10);
        }
    }

 } // class ScoreTrackingListener