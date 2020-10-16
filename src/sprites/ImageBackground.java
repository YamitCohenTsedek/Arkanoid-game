package sprites;

import java.awt.Image;

import biuoop.DrawSurface;
import geometry.Point;
import levels.GameLevel;

/**
 * The ImageBackground class is in charge of drawing the background as an image.
 */
public class ImageBackground implements Sprite {
    // Declare the members of the class.
    private Image imageName;
    private Point startPoint;

    /**
     * Constructor.
     * @param imageName the name of the image that should be loaded.
     */
    public ImageBackground(Image imageName) {
        this.imageName = imageName;
    }

    /**
     * @param start the start point of the background image.
     */
    public void setStartPoint(Point start) {
        this.startPoint = start;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the image on the draw surface.
        int startX = (int) this.startPoint.getX();
        int startY = (int) this.startPoint.getY();
        d.drawImage(startX, startY, this.imageName);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

} // class BackgroundOfImage