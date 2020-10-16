package animation;

import biuoop.DrawSurface;

/**
 * The Animation interface represents an animation that we would like to run in an unknown platform.
 */
public interface Animation {

    /**
     * Perform the logic of one frame of the animation.
     * @param d a draw surface you can draw on.
     * @param dt specifies the number of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * @return true if the animation should be stopped, false otherwise.
     */
    boolean shouldStop();

} // interface Animation