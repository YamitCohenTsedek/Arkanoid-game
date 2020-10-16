package sprites;

/**
 * The BlockCreator interface is an interface of a factory-object that is used for creating blocks.
 */

public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the x position of the block.
     * @param ypos the y position of the block.
     * @return a new block.
     */
    Block create(int xpos, int ypos);

} // interface BlockCreator