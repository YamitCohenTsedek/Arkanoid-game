package levels;
import java.util.List;
import animation.AnimationRunner;
import arkanoidgame.Counter;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import highscores.HighScoresTable;

/**
 * The GameFlow class is in charge of creating different levels and passing from one level to the next one.
 */
public class GameFlow {
    // Declare the members of the class.
    private KeyboardSensor keyboard;
    private AnimationRunner animationRunner;
    private Counter gameScore;
    private Counter lives;
    private HighScoresTable highScoresTable;
    private DialogManager dialogManager;

    /**
     * Constructor.
     * @param animationRunner an animation runner.
     * @param keyboard a keyboard sensor.
     * @param dialogManager is used to get the player's name.
     * @param highScoresTable the table of the highest scores.
     */
    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyboard, DialogManager dialogManager,
        HighScoresTable highScoresTable) {
        this.animationRunner = animationRunner;
        this.keyboard = keyboard;
        this.dialogManager = dialogManager;
        // Initialize the score and the number of lives.
        this.gameScore = new Counter(0);
        this.lives = new Counter(7);
        this.highScoresTable = highScoresTable;
    }

    /**
     * @param levels a list of LevelInformation objects (the information of each level).
     */
    public void runLevels(List<LevelInformation> levels) {
        int score;
        Tools tools = new Tools();
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboard, this.animationRunner,
                this.gameScore, this.lives);
            // Initialize the game.
            level.initialize();
            // As long as there are more blocks in the current level and there are still lives, run the game.
            while (level.getBlocksNumber() > 0 && level.getLivesNumber() > 0) {
                level.playOneTurn();
            }
            // If the number of lives is 0, end the game.
            if (level.getLivesNumber() == 0) {
                score = this.gameScore.getValue();
                tools.endOfGameAnimations(this.animationRunner, this.highScoresTable,
                        score, false, this.dialogManager);
                return;
            }
        }
        score = this.gameScore.getValue();
        tools.endOfGameAnimations(this.animationRunner, this.highScoresTable,
                score, true, this.dialogManager);
    }

} // class GameFlow