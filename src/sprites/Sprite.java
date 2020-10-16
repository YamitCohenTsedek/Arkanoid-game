package sprites;

import biuoop.DrawSurface;
import levels.GameLevel;

/**
 * The Sprite interface represents the objects that can be drawn on the screen and can be notified that time
 * has passed (so that they know to change their position, shape, appearance, etc.).
 */
public interface Sprite {
    /**
     * Draw the Sprite on the screen.
     * @param d a surface you can draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     * @param dt specifies the number of seconds passed since the last call.
     */
    void timePassed(double dt);

    /**
     * Add the Sprite to the game, calling the appropriate game methods.
     * @param g the game that the sprite should be added to.
     */
    void addToGame(GameLevel g);
}