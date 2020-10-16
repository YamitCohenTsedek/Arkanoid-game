package menu;

import animation.Animation;
import animation.AnimationRunner;

/**
 * The ShowHighScoresTask class is in charge of implementing the task of displaying the high scores animation.
 */
public class ShowHighScoresTask implements Task<Void> {
    // Declare the members of the class.
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * @param runner the animation runner.
     * @param highScoresAnimation the animation of the high scores.
     */
    public ShowHighScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * Run the high scores animation.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }

} // class ShowHighScoresTask
