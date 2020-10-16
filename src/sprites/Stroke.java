package sprites;

import java.awt.Color;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The Stroke class is responsible to draw a stroke to the block.
 */
public class Stroke implements BlockBackground {
    // Declare the members of the class.
    private Color strokeColor;

    /**
     * Constructor.
     * @param strokeColor the color of the stroke.
     */
    public Stroke(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    @Override
    public void draw(DrawSurface d, Rectangle rec) {
        int startX = (int) rec.getUpperLeft().getX();
        int startY = (int) rec.getUpperLeft().getY();
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        d.setColor(this.strokeColor);
        // Draw the outline of the block (a rectangle shape).
        d.drawRectangle(startX, startY, width, height);
    }

} // class Stroke
