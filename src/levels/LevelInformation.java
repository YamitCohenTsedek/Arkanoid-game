package levels;
import java.util.List;
import arkanoidgame.Velocity;
import sprites.Block;
import sprites.Sprite;

/**
 * The LevelInformation interface specifies the information required to fully describe a level.
 */
public interface LevelInformation {

    /**
     * @return the number of balls of the level.
     */
    int numberOfBalls();

    /**
     * @return the initial velocities of all the balls.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return the paddle speed.
     */
    int paddleSpeed();

    /**
     * @return the paddle width.
     */
    int paddleWidth();

    /**
     * @return the level name that is displayed at the top of the screen.
     */
    String levelName();

    /**
     * @return a Sprite contains the background of the level.
     */
    Sprite getBackground();

    /**
     * @return a list of blocks that make up the level (each block contains its size, color and location).
     */
    List<Block> blocks();

    /**
     * @return the number of blocks that should be removed from the game.
     */
    int numberOfBlocksToRemove();

} // interface LevelInformation