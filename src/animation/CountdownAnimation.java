package animation;
import arkanoidgame.Counter;
import biuoop.DrawSurface;
import sprites.Colors;
import sprites.SpriteCollection;

/**
 * The CountdownAnimation class creates an animation that displays the given gameScreen for
 * numOfSeconds seconds, and shows on top of them a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before it's replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    // Declare the members of the class.
    private boolean stop;
    private long startTimeMillis;
    private long passedTimeMillis;
    private long animationMillis;
    private int countFrom;
    private Counter currentNumber;
    private SpriteCollection gameScreen;

    /**
     * constructor.
     * @param numOfSeconds the number of seconds that the animation will be displayed for on the gameScreen.
     * @param countFrom a countdown from countFrom (some number) back to 1.
     * @param gameScreen the screen of the game that the animation will be displayed on.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.stop = false;
        // The number of milliseconds that the animation will be displayed for. 1 second = 1000 milliseconds.
        this.animationMillis = (long) numOfSeconds * 1000;
        // Save the time the animation started to calculate when the animation should stop.
        this.startTimeMillis = System.currentTimeMillis();
        // The time that has passed in milliseconds since the first time that CountdownAnimation was called.
        this.passedTimeMillis = 0;
        this.countFrom = countFrom;
        // The current number in the countdown.
        this.currentNumber = new Counter(countFrom);
        this.gameScreen = gameScreen;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        /*
         * The animation should be displayed for the given number of seconds.
         * The System.currentTimeMillis() method is used to know when the animation has started and when it should stop.
         * Each function call saves the startTimeMillis, calculates the differences of milliseconds between the calls,
         * and adds it to passedTimeMillis.
         */
        this.passedTimeMillis = this.passedTimeMillis + System.currentTimeMillis() - this.startTimeMillis;
        this.startTimeMillis = System.currentTimeMillis();
        int textSize = 100;
        // Colors is an object that is in charge of the colors' creating.
        Colors colors = new Colors();
        // Each number of the countdown will appear on the screen for (num Of milliseconds / countFrom) milliseconds.
        long millisForNumber = this.animationMillis / countFrom;
        // The index of the current number (in an ascending counting).
        int numberIndex = (countFrom - currentNumber.getValue()) + 1;
        // Draw all the sprites of the game screen.
        this.gameScreen.drawAllOn(d);
        d.setColor(colors.setRainbowColor(-1));
        // Draw the current number in the countdown.
        d.drawText(d.getWidth() / 2 - 20, d.getHeight() / 2 + 20, Integer.toString(currentNumber.getValue()), textSize);
        // Add the difference of the milliseconds between the calls to passedTimeMillis.
        this.passedTimeMillis = this.passedTimeMillis + (System.currentTimeMillis() - startTimeMillis);
        // If the time limit of the current number has passed, continue to the next number.
        if (this.passedTimeMillis >= millisForNumber * numberIndex) {
            currentNumber.decrease(1);
        }
        // If the current number is 0, stop counting.
        if (currentNumber.getValue() == 0) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

} // class CountDownAnimation