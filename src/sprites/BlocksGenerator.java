package sprites;

import java.util.Map;
import java.util.TreeMap;
import geometry.Point;

/**
 * The BlocksGenerator class is responsible to create blocks at a specified location.
 */
public class BlocksGenerator implements BlockCreator {
    // Declare the members of the class.
    private int width;
    private int height;
    private int numOfHits;
    private Sprite defBackground;
    private Stroke stroke;
    private Map<Integer, BlockBackground> hitsToBackground;

    /**
     * Constructor.
     * Initialize the values of the block generator.
     */
    public BlocksGenerator() {
        this.width = 0;
        this.height = 0;
        this.numOfHits = 0;
        this.defBackground = null;
        this.stroke = null;
        this.hitsToBackground = new TreeMap<>();
    }

    @Override
    public Block create(int xpos, int ypos) {
        // Create a block at the specified location.
        Block block = new Block(new Point(xpos, ypos));
        block.setWidth(this.width);
        block.setHeight(this.height);
        block.setStroke(stroke);
        for (int hitsNum : this.hitsToBackground.keySet()) {
            block.addBackgroundByHits(hitsNum, this.hitsToBackground.get(hitsNum));
        }
        return block;
    }

    /**
     * @return the width of the block.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return the height of the block.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * @return the number of hits of the block.
     */
    public int getNumOfHits() {
        return this.numOfHits;
    }

    /**
     * @return the default background of the block.
     */
    public Sprite getDefBackground() {
        return this.defBackground;
    }

    /**
     * @return the map that maps between the number of hits and the backgrounds.
     */
    public Map<Integer, BlockBackground> getHitsToBackground() {
        return this.hitsToBackground;
    }

    /**
     * @param blockWidth the width of the block.
     */
    public void setWidth(int blockWidth) {
        this.width = blockWidth;
    }

    /**
     * @param blockHeight the height of the block.
     */
    public void setHeight(int blockHeight) {
        this.height = blockHeight;
    }

    /**
     * @param hits number of hits that the ball should hit the block.
     */
    public void setNumOfHits(int hits) {
        this.numOfHits = hits;
    }

    /**
     * @param blockBackground the default background of the block.
     */
    public void setDefBackground(Sprite blockBackground) {
        this.defBackground = blockBackground;
    }

    /**
     * @param blockStroke the stroke of the block.
     */
    public void setStroke(Stroke blockStroke) {
        this.stroke = blockStroke;
    }

} // class BlocksGenerator