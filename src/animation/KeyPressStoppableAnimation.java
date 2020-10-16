package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The KeyPressStoppableAnimation class is in charge of running a stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    // Declare the members of the class.
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    // The animation should be stopped only if the the key was pressed after the animation has started.
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     * @param sensor a keyboardSensor.
     * @param key a key that when the player presses it, the animation stops.
     * @param animation a stoppable animation to run until the key is pressed.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
    }

    @Override
    public boolean shouldStop() {
        if (sensor.isPressed(this.key)) {
            return !isAlreadyPressed;
        }
        this.isAlreadyPressed = false;
        return false;
     }

} // class KeyPressStoppableAnimation