package readingfiles;

import java.awt.Color;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import arkanoidgame.Velocity;
import geometry.Point;
import geometry.Rectangle;
import levels.LevelInformation;
import sprites.Block;
import sprites.ColorBackground;
import sprites.ColorsParser;
import sprites.ImageBackground;
import sprites.Sprite;

/**
 * The LevelCreation class implements the LevelInformation interface and is in charge of creating a new level.
 */
public class LevelCreation implements LevelInformation {
    // Declare the members of the class.
    private String levelName;
    private List<Velocity> ballVelocities;
    private Sprite background;
    private int paddleSpeed;
    private int paddleWidth;
    private String blockDefinitions;
    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private int numOfBlocks;
    private List<Block> blocksLayout;

    /**
     * Constructor.
     * Save all the information of the level in order to be able to create it.
     * @param levelInfo a map that maps between features of the level and their values.
     */
    public LevelCreation(Map<String, String> levelInfo) {
        this.levelName = levelInfo.get("level_name");
        this.ballVelocities = parseToBallVelocities(levelInfo.get("ball_velocities"));
        this.background = stringToBackground(levelInfo.get("background"));
        this.paddleSpeed = Integer.parseInt(levelInfo.get("paddle_speed"));
        this.paddleWidth = Integer.parseInt(levelInfo.get("paddle_width"));
        this.blockDefinitions = levelInfo.get("block_definitions");
        this.blocksStartX = Integer.parseInt(levelInfo.get("blocks_start_x"));
        this.blocksStartY = Integer.parseInt(levelInfo.get("blocks_start_y"));
        this.rowHeight = Integer.parseInt(levelInfo.get("row_height"));
        this.numOfBlocks = Integer.parseInt(levelInfo.get("num_blocks"));
        List<String> blocksInfo = blockInfoLines(levelInfo);
        this.blocksLayout = parseToBlocks(blocksInfo);
    }

    /**
     * @param value the string to parse to a background.
     * @return Sprite of the background.
     */
    private static Sprite stringToBackground(String value) {
        try {
            // If it's a color background.
            if (value.startsWith("color(")) {
                ColorsParser parser = new ColorsParser();
                Color color = parser.colorFromString(value);
                ColorBackground colorBackground = new ColorBackground(color);
                Rectangle rectangle = new Rectangle(new Point(0, 0), 800, 600);
                colorBackground.setRecBackground(rectangle);
                return colorBackground;
            // If it's an image background.
            } else if (value.startsWith("image(")) {
                value = value.replace("image(", "");
                value = value.replace(")", "");
                java.io.InputStream is;
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(value);
                java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(Objects.requireNonNull(is));
                ImageBackground img = new ImageBackground(image);
                img.setStartPoint(new Point(0, 0));
                return img;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    /**
     * @param velocities the information about the velocities of the balls.
     * @return a list of velocities.
     */
    public List<Velocity> parseToBallVelocities(String velocities) {
        String[] splitedVelocitiesString;
        if (velocities.contains(" ")) {
            splitedVelocitiesString = velocities.split(" ");
        } else {
            splitedVelocitiesString =  new String[1];
            splitedVelocitiesString[0] = velocities;
        }
        List<Velocity> splitedballVelocities = new ArrayList<>();
        String[] splitedCurrentDxDy;
        for (String s : splitedVelocitiesString) {
            splitedCurrentDxDy = s.split(",");
            int currentDx = Integer.parseInt(splitedCurrentDxDy[0]);
            int currentDy = Integer.parseInt(splitedCurrentDxDy[1]);
            splitedballVelocities.add(Velocity.fromAngleAndSpeed(currentDx, currentDy));
        }
        return splitedballVelocities;
    }

    /**
     * @param levelInfo the level information.
     * @return the lines of the block info.
     */
    public List<String> blockInfoLines(Map<String, String> levelInfo) {
        List<String> blocksLines = new ArrayList<>();
        int numOfBlockLines = Integer.parseInt(levelInfo.get("num_of_blocks_lines"));
        for (int i = 1; i < numOfBlockLines; i++) {
            blocksLines.add(levelInfo.get("blocks_line_" + i));
        }
        return blocksLines;
    }

    /**
     * @return BlocksFromSymbolsFactory that is be in charge of creating blocks.
     */
    private BlocksFromSymbolsFactory fileToBlockFactory() {
        Reader reader = null;
        try {
            reader = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(this.blockDefinitions)));
        } catch (Exception e) {
            System.err.println("Failed");
        }
        return BlocksDefinitionReader.fromReader(reader);
    }

    /**
     * @param blockInfo a list that contains the information of the block.
     * @return a list of blocks.
     */
    private List<Block> parseToBlocks(List<String> blockInfo) {
        BlocksFromSymbolsFactory bfsf = fileToBlockFactory();
        List<Block> blocks = new ArrayList<>();
        int currentPositionX;
        int currentPositionY = this.blocksStartY;
        int blockWidth;
        String currentChar;
        // Create the layout of the blocks.
        for (String s : blockInfo) {
            currentPositionX = this.blocksStartX;
            for (int j = 0; j < s.length(); j++) {
                currentChar = Character.toString(s.charAt(j));
                if (bfsf.isBlockSymbol(currentChar)) {
                    int numOfHits = bfsf.getNumOfHits((currentChar));
                    blockWidth = bfsf.getBlockWidth(currentChar);
                    Block b = bfsf.getBlock(currentChar, currentPositionX, currentPositionY);
                    Rectangle rec = new Rectangle(new Point(
                            currentPositionX, currentPositionY), blockWidth, this.rowHeight);
                    b.setRectangle(rec);
                    b.setStroke((bfsf.getStroke(currentChar)));
                    b.setNumOfHits(numOfHits);
                    blocks.add(b);
                    currentPositionX = currentPositionX + blockWidth;
                } else if (bfsf.isSpaceSymbol(currentChar)) {
                    currentPositionX = currentPositionX + bfsf.getSpaceWidth(currentChar);
                } else {
                    throw new RuntimeException("The symbol is unknown");
                }
            }
            currentPositionY = currentPositionY + this.rowHeight;
        }
        return blocks;
}

    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocksLayout;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numOfBlocks;
    }

}   // class LevelCreation