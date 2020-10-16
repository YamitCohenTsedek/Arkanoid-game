package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import geometry.Rectangle;
import levels.GameLevel;

/**
 * The BackgroundOfColor class is in charge of drawing the background as a color.
 */
public class ColorBackground implements Sprite {
    // Declare the members of the class.
    private Color color;
    private Rectangle rectangle;

     /**
      * Constructor.
      * @param color the color of the block.
      */
    public ColorBackground(Color color) {
        this.color = color;
    }

    /**
     * @param backgroundRec the rectangle shape of the background.
     */
    public void setRecBackground(Rectangle backgroundRec) {
        this.rectangle = backgroundRec;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        int startX = (int) this.rectangle.getUpperLeft().getX();
        int startY = (int) rectangle.getUpperLeft().getY();
        int width =  (int) rectangle.getWidth();
        int height =  (int) rectangle.getHeight();
        d.fillRectangle(startX, startY, width, height);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

} // class BackgroundOfColor