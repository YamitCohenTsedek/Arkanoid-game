package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * The BlockBackground interface represents a block background.
 */
public interface BlockBackground {

    /**
     * Draw the background of the block.
     * @param d a surface to draw on.
     * @param rec the block's shape.
     */
    void draw(DrawSurface d, Rectangle rec);

} // BlockBackground interface