package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import menu.Menu;
import menu.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The MenuAnimation class represents a menu animation. The menu is a screen stating a list of several options that the
 * player can choose to do.
 * @param <T> the specific type of MenuAnimation .
 */
public class MenuAnimation<T> implements Menu<T> {
    // Declare the members of the class.
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private T status;
    private boolean stop;
    private List<String> keys;
    private List<String> messages = new ArrayList<>();
    private List<Task<T>> returnValues;

    /**
     * Constructor.
     * @param animationRunner a runner that runs animations.
     * @param keyboard detects the player's presses.
     */
    public MenuAnimation(AnimationRunner animationRunner, KeyboardSensor keyboard) {
        this.animationRunner = animationRunner;
        this.keyboard = keyboard;
        this.status = null;
        this.stop = false;
        this.keys = new ArrayList<>();
        this.returnValues = new ArrayList<>();
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // Draw the primary screen of the menu.
        Image backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/blocks.jpg")));
        } catch (IOException e) {
        }
        d.drawImage(0, 0, backgroundImage);
        d.setColor(Color.cyan);

        Image arkanoidImage = null;
        try {
             arkanoidImage = ImageIO.read(Objects.requireNonNull(
                     ClassLoader.getSystemClassLoader().getResourceAsStream("general_images/Arkanoid.jpg")));
        } catch (IOException e) {
        }
        d.drawImage(80, 80,  arkanoidImage);

        for (int i = 0; i < this.keys.size(); i++) {
            d.drawText(140, 300 + (i * 80), "▶ Press " + "\"" + this.keys.get(i)
                    + "\" " + this.messages.get(i), 32);
        }

        // Check whether the player pressed on a key.
        for (int i = 0; i < returnValues.size(); i++) {
            if (keyboard.isPressed(this.keys.get(i))) {
                this.stop = true;
                try {
                    backgroundImage = ImageIO.read(Objects.requireNonNull(
                            ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/blocks.jpg")));
                } catch (IOException e) {
                }
                d.drawImage(0, 0, backgroundImage);
                status = this.returnValues.get(i).run();
                break;
             }
        }
    }

    @Override
    public boolean shouldStop() {
        boolean tempStop = this.stop;
        this.stop = false;
        return tempStop;
    }

    /**
     * Initialize the stop member with false to enable to run the animation again.
     */
    public void reset() {
        this.status = null;
        this.stop = false;
    }

    @Override
    public void addSelection(String stopKey, String stopMessage, T returnValue) {
        this.keys.add(stopKey);
        this.messages.add(stopMessage);
        this.returnValues.add(() -> returnValue);
    }

    @Override
    public T getStatus() {
        return status;
    }

    @Override
    public void addSubMenu(String stopKey, String message, Menu<T> subMenu) {
        // Add the keys, the messages and the return values.
        this.keys.add(stopKey);
        this.messages.add(message);
        this.returnValues.add(() -> {
            animationRunner.run(subMenu);
            return subMenu.getStatus();
        });
    }

} // class MenuAnimation<T>