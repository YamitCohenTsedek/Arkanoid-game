package animation;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import biuoop.DrawSurface;
import sprites.Colors;

import javax.imageio.ImageIO;

import static levels.GameLevel.SURFACE_HEIGHT;
import static levels.GameLevel.SURFACE_WIDTH;

/**
 * The EndScreenAnimation class represents the animation of the end of the game.
 */
public class EndScreenAnimation implements Animation {
    // Declare the members of the class.
    private boolean stop;
    private boolean winning;
    private int score;

    /**
     * Constructor.
     * @param winning a boolean variable that indicates whether the game was over as a result
     * of a winning or as a result of a loss.
     * @param score the score of the game.
     */
    public EndScreenAnimation(boolean winning, int score) {
        this.stop = false;
        this.winning = winning;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //  If the game was over as a result of a winning, draw a winning screen.
        if (winning) {
            Image winningBackgroundImage = null;
            try {
                winningBackgroundImage = ImageIO.read(
                        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(
                                "background_images/fireworks.jpg")));
            } catch (IOException e) {
            }
            d.drawImage(0, 0,  winningBackgroundImage);
            Colors colors = new Colors();
            d.setColor(colors.setRainbowColor(-1));
            d.drawText(220, 200, "You Win!", 90);
            d.setColor(Color.darkGray);
        // Else - the game was over as a result of a loss - draw a game over screen.
        } else {
            d.setColor(Color.black);
            d.fillRectangle(0, 0, SURFACE_WIDTH, SURFACE_HEIGHT);
            d.setColor(Color.white);
            d.drawText(170, 200, "Game Over", 90);
        }
        d.drawText(240, 330, "Your score is: " + score, 40);
        // If "c" key is pressed, the animation ends.
        d.drawText(260, 550, "Press \"c\" to continue", 30);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

} // class EndScreenAnimation