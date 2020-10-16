package collision;

import arkanoidgame.Counter;
import levels.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * The BlockRemover class is in charge of removing blocks from the game,
 * as well as keeping count of the number of the remaining blocks.
 */
public class BlockRemover implements HitListener {
    // Declare the members of the class.
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game the game that the block should be removed from.
     * @param removedBlocks the counter of the removed blocks.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Blocks that are hit and reach 0 hit-points should be removed from the game.
        if (beingHit.getHitPoints() == 1) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }

} // class BlockRemover