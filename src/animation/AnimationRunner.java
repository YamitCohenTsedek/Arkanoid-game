package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

/**
 * The AnimationRunner class takes a non-specific Animation object and runs it.
 */
public class AnimationRunner {
    // Declare the members of the class.
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructor.
     * @param gui a Graphical User Interface that the game is displayed on it.
     * @param framesPerSecond the number of frames that should be displayed in any second.
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        // Use a platform of GUI in the animation runner.
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Run the given animation.
     * @param animation the animation that should run.
     */
    public void run(Animation animation) {
        double dt = 1.0 / this.framesPerSecond;
        // Each frame in the animation can last 1000 / framesPerSecond milliseconds.
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        // As long as the animation should not be stopped, continue to run the animation.
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // Timing.
            // Do one frame of the animation and show it.
            DrawSurface drawSurface = gui.getDrawSurface();
            animation.doOneFrame(drawSurface, dt);
            gui.show(drawSurface);
            // Timing - the time it takes to perform the work in the loop may be non-negligible.
            // Therefore, subtract the time it takes to do the work from the number of milliseconds per frame.
            // If the subtraction result is > 0, the sleeper should sleep for this number of milliseconds.
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    } // run

    /**
     * Get a draw surface from the animation runner.
     * @return a draw surface you can draw on.
     */
    public DrawSurface getDrawSurface() {
        return this.gui.getDrawSurface();
    }

    /**
     * @return the KeyboardSensor of the animation runner.
     */
    public KeyboardSensor getKeyboardSensor() {
        return this.gui.getKeyboardSensor();
    }

} // AnimationRunner
