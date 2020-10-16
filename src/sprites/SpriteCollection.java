package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * The SpriteCollection class represents a collection of sprites that are in the game.
 */
public class SpriteCollection {
    // Declare the members of the class.
    private List<Sprite> spritesList = new ArrayList<>();

    /**
     * @return the sprites list.
     */
    public List<Sprite> getSprites() {
        return this.spritesList;
    }

    /**
     * Add the given sprite to the Sprite collection.
     * @param s the sprite that should be add to the Sprite collection.
     */
    public void addSprite(Sprite s) {
        this.spritesList.add(s);
    }

    /**
     * Notify each one of the sprites in the list that time has passed.
     * @param dt specifies the number of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        Sprite pointerToList;
        // Run over the list of the sprites and notify each one of them that time has passed.
        for (int i = 0; i < this.spritesList.size(); i++) {
            pointerToList = this.spritesList.get(i);
            pointerToList.timePassed(dt);
        }
    }

    /**
     * Call drawOn(d) from all the sprites.
     * @param d a surface you can draw on.
     */
    public void drawAllOn(DrawSurface d) {
        Sprite pointerToList;
        for (int i = 0; i < this.spritesList.size(); i++) {
            pointerToList = this.spritesList.get(i);
            pointerToList.drawOn(d);
        }
    }

} // class SpriteCollection