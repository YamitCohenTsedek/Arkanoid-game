package sprites;

import java.util.Map;
import java.util.TreeMap;
import geometry.Point;
import geometry.Rectangle;

/**
 * The BlocksGenerator class is responsible to create blocks at a specified location.
 */
public class BlockGenerator implements BlockCreator {
    // Declare the members of the class.
    private String symbol;
    private int width;
    private int height;
    private Rectangle rectangle;
    private int numOfHits;
    private BlockBackground defBackground;
    private Stroke stroke;
    private Map<Integer, BlockBackground> hitsToBackground;

    /**
     * Constructor.
     */
    public BlockGenerator() {
        this.width = 0;
        this.height = 0;
        this.rectangle = null;
        this.numOfHits = 0;
        this.defBackground = null;
        this.hitsToBackground = new TreeMap<>();
        this.stroke = null;
    }

    @Override
    public Block create(int xpos, int ypos) {
        // Create a block at the specified location.
        Block block = new Block(new Point(xpos, ypos));
        this.rectangle = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        block.setRectangle(rectangle);
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
     * @return the symbol the block.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * @return the stroke of the block.
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * @return the background of the block.
     */
    public BlockBackground getDefBackground() {
        return this.defBackground;
    }

    /**
     * @return a map that maps between number of hits of blocks to their backgrounds.
     */
    public Map<Integer, BlockBackground> getHitsToBackground() {
        return this.hitsToBackground;
    }

    /**
     * @param s the symbol of the block.
     */
    public void setSymbol(String s) {
        this.symbol = s;
    }

    /**
     * @return the rectangle shape of the block.
     */
    public Rectangle getRectangle() {
        return this.rectangle;
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
     * @param hits the number of hits of the block.
     */
    public void setNumOfHits(int hits) {
        this.numOfHits = hits;
    }

    /**
     * @param blockBackground the background of the block.
     */
    public void setDefBackground(BlockBackground blockBackground) {
        this.defBackground = blockBackground;
    }

    /**
     * @param hits - the number of hits of the block in which background should be added.
     * @param blockBackground the background of the block that fits to the current number of hits.
     */
    public void addBackground(int hits, BlockBackground blockBackground) {
        this.hitsToBackground.put(hits, blockBackground);
    }

    /**
     * @param blockStroke the stroke of the block.
     */
    public void setStroke(Stroke blockStroke) {
        this.stroke = blockStroke;
    }

} // class BlocksGenerator