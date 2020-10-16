package sprites;

import java.awt.Color;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The BlockColorBackground class implements BlockBackground interface and represents a color background of a block.
 */
public class BlockColorBackground implements BlockBackground {
    // Declare the members of the class.
    private Color color;

    /**
     * Constructor.
     * @param color the color of the background.
     */
    public BlockColorBackground(Color color) {
        this.color = color;
    }

    @Override
    public void draw(DrawSurface d, Rectangle rec) {
        // Draw the color background of the block on the draw surface.
        d.setColor(this.color);
        int startX = (int) rec.getUpperLeft().getX();
        int startY = (int) rec.getUpperLeft().getY();
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        d.fillRectangle(startX, startY, width, height);
    }

} // class BlockColorBackground