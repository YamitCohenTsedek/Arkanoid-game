package animation;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * The PauseScreenAnimation is an animation displayed when the game is paused.
 */
public class PauseScreenAnimation implements Animation {
    // Declare the members of the class.
    private boolean stop;

    /**
     * Constructor.
     */
    public PauseScreenAnimation() {
        // Initialize the variable that is in charge of stopping the animation with false.
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // Draw the pause screen.
        Image backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/blocks.jpg")));
        } catch (IOException e) {
        }
        d.drawImage(0, 0,  backgroundImage);
        d.setColor(Color.cyan);
        // If "c" key is pressed, the animation ends.
        d.drawText(165, d.getHeight() / 2, "Paused -- press \"c\" to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

} // class PauseScreenAnimation