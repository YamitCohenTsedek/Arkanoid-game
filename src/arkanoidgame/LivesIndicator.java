package arkanoidgame;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;

import biuoop.DrawSurface;
import levels.GameLevel;
import sprites.Sprite;

import javax.imageio.ImageIO;

/**
 * The LivesIndicator class indicates the current number of lives.
 */
public class LivesIndicator implements Sprite {
    // Declare the members of the class.
    private Counter numberOfLives;

    /**
     * Constructor.
     * @param lives the counter of the lives in the game.
     */
    public LivesIndicator(Counter lives) {
        this.numberOfLives = lives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the LivesIndicator sprite at the top of the screen.
        int leftBlockX = d.getWidth() / 3;
        int centerBlockY = d.getHeight() / 25;
        Image heartImage = null;
        try {
            heartImage = ImageIO.read(Objects.requireNonNull(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("general_images/heart.png")));
        } catch (IOException e) {
        }
        d.drawImage(leftBlockX - 22, -10, heartImage);
        Color textColor = Color.black;
        d.setColor(textColor);
        String scoreString = Integer.toString(this.numberOfLives.getValue());
        d.drawText(leftBlockX, centerBlockY, scoreString, 20);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    /**
     * Add the score indicator to the game, by calling the appropriate game methods.
     * @param game the game that the LivesIndicator should be added to.
     */
     public void addToGame(GameLevel game) {
         game.addSprite(this);
     }

} // class LivesIndicator
