package levels;
import java.io.File;
import java.io.IOException;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndScreenAnimation;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import highscores.HighScoresTable;
import highscores.ScoreInfo;

/**
 * The Tools class contains some helper function that help to build the Arkanoid game.
 */
public class Tools {

    /**
     * Run a stoppable animation.
     * @param animationRunner a runner that runs animations.
     * @param animation an animation to run.
     * @param endKey a key that when the player presses on it, the animation stops.
     */
    public void runStoppableAnimation(AnimationRunner animationRunner, Animation animation,
        String endKey) {
            KeyPressStoppableAnimation stoppableAnimation = new KeyPressStoppableAnimation(
                animationRunner.getKeyboardSensor(), endKey, animation);
            animationRunner.run(stoppableAnimation);
    }

    /**
     * @param highScoresTable the table of the high scores.
     */
    public void tryToLoadHighScores(HighScoresTable highScoresTable) {
        File highScores = new File("highscores.txt");
        try {
            highScoresTable.load(highScores);
        } catch (IOException e) {
            try {
                highScoresTable.save(highScores);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Add a player name to the high scores table.
     * @param highScoresTable the table of the high scores.
     * @param score the score of the player.
     * @param dialogManager to get the player's name.
     */
    private void addPlayerName(HighScoresTable highScoresTable, int score, DialogManager dialogManager) {
        String name;
        File highScores = new File("highscores.txt");
        // Add the player's name if his score entitles him to be listed in the high scores table.
        if (highScoresTable.getRank(score) <= highScoresTable.size()) {
            name = dialogManager.showQuestionDialog("Player Name", "What is your name?", "");
            try {
                highScoresTable.add(new ScoreInfo(name, score));
                highScoresTable.save(highScores);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Run the animations of the end of game.
     * @param animationRunner a runner that runs animations.
     * @param highScoresTable the table of the high scores.
     * @param score the score of the player.
     * @param winningOrLoss a boolean variable that indicates which animation should run - winning or loss animation.
     * @param dialogManager to get the player's name.
     */
    public void endOfGameAnimations(AnimationRunner animationRunner, HighScoresTable highScoresTable, int score,
                                    boolean winningOrLoss, DialogManager dialogManager) {
        String spaceKey = KeyboardSensor.SPACE_KEY;
        // Run the end screen animation of winning or loss.
        EndScreenAnimation endScreen = new EndScreenAnimation(winningOrLoss, score);
        this.runStoppableAnimation(animationRunner, endScreen, "c");
        // Add the player's name if his score entitles him to be listed on the high-scores table.
        addPlayerName(highScoresTable, score, dialogManager);
        Animation highScoresAnimation = new HighScoresAnimation(highScoresTable);
        this.runStoppableAnimation(animationRunner, highScoresAnimation, spaceKey);
    }

} // class Tools