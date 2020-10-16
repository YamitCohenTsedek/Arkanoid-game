package arkanoidgame;

import java.awt.Color;

import biuoop.DrawSurface;
import levels.GameLevel;
import sprites.Sprite;

/**
 * The ScoreIndicator class is in charge of displaying the current score.
 */
public class ScoreIndicator implements Sprite {
    // Declare the members of the class.
    private Counter score;

    /**
     * Constructor.
     * @param score the current score of the game.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the score indicator at the top of the screen.
        int centerBlockX =  360;
        int centerBlockY = d.getHeight() / 25;
        Color textColor = Color.black;
        d.setColor(textColor);
        String scoreString = "Score: " + this.score.getValue();
        d.drawText(centerBlockX, centerBlockY, scoreString, 20);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    /**
     * Add the score indicator to the game, by calling the appropriate game methods.
     * @param game the game that the score indicator should be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

} // class ScoreIndicator
