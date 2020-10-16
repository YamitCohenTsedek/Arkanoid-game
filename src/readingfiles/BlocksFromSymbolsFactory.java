package readingfiles;

import java.util.Map;
import sprites.Block;
import sprites.BlockGenerator;
import sprites.Stroke;

/**
 * The BlocksDefinitionReader class is in charge of reading a block-definitions file and returning a
 * BlocksFromSymbolsFactory object.
 */
public class BlocksFromSymbolsFactory {
    // Declare the members of the class.
    private Map<String, BlockGenerator> blockCreators;
    private Map<String, Integer> spacerWidths;

    /**
     * Constructor.
     * @param blockCreators a map that maps between a symbol to the appropriate block creator.
     * @param spacerWidths a map that maps between a symbol to the spacer widths.
     */
    public BlocksFromSymbolsFactory(Map<String, BlockGenerator> blockCreators, Map<String, Integer> spacerWidths) {
        this.blockCreators = blockCreators;
        this.spacerWidths = spacerWidths;
    }

    /**
     * Return a block according to the definitions associated with symbol s. The position of the block is (xpos, ypos).
     * @param s a block symbol.
     * @param xpos the x value of the position of the block.
     * @param ypos the y value of the position of the block.
     * @return true if it's a block symbol, false otherwise.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }


    /**
     * @param s a string to check whether it's a space symbol or not.
     * @return true if 's' is a valid space symbol, false otherwise.
     */
    public boolean isSpaceSymbol(String s) {
        return (this.spacerWidths.containsKey(s));
    }

    /**
     * @param s a string to check whether it's a block symbol or not.
     * @return true if 's' is a valid block symbol, false otherwise.
     */
    public boolean isBlockSymbol(String s) {
        return (this.blockCreators.containsKey(s));
    }

    /**
     * @param s a space symbol.
     * @return the width (in pixels) associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * @param s a block symbol.
     * @return the width (in pixels) associated with the given block-symbol.
     */
    public int getBlockWidth(String s) {
        return this.blockCreators.get(s).getWidth();
    }

    /**
     * @param s a block symbol.
     * @return the number of hits of the block.
     */
    public int getNumOfHits(String s) {
        return this.blockCreators.get(s).getNumOfHits();
    }

    /**
     * @param s a block symbol.
     * @return the stroke the block.
     */
    public Stroke getStroke(String s) {
        return this.blockCreators.get(s).getStroke();
    }

} // class BlocksFromSymbolsFactory