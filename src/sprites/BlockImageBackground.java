package sprites;

import java.awt.Image;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The BlockImageBackground class implements the BlockBackground interface. and represents an image background
 * of a block.
 */
public class BlockImageBackground implements BlockBackground {
    // Declare the members of the class.
    private Image image;

    /**
     * Constructor.
     * @param image the image of the background.
     */
    public BlockImageBackground(Image image) {
        this.image = image;
    }

    @Override
    public void draw(DrawSurface d, Rectangle rec) {
        // Draw the image of the block on a draw surface.
        int startX = (int) rec.getUpperLeft().getX();
        int startY = (int) rec.getUpperLeft().getY();
        d.drawImage(startX, startY, this.image);
    }

} // class BlockImageBackground