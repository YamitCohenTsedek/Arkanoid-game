package readingfiles;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.util.*;

import javax.imageio.ImageIO;

import sprites.BlockBackground;
import sprites.BlockColorBackground;
import sprites.BlockGenerator;
import sprites.BlockImageBackground;
import sprites.ColorsParser;
import sprites.Stroke;

/**
 * The BlocksDefinitionReader class is in charge of reading the block-definitions file and returning a
 * BlocksFromSymbolsFactory object.
 */
public class BlocksDefinitionReader {

    /**
     * @param reader is connected to the source file. Reads character streams.
     * @return a list of the relevant definition lines from the block definitions file.
     */
    private static List<String> relevantLines(java.io.Reader reader) {
        List<String> relevantLines = new ArrayList<>();
         // Wrap the reader with a bufferedReader which read lines.
        BufferedReader bufferedReader = new BufferedReader(reader);
         // Read all the relevant lines and add them to the list.
         try {
             String line = bufferedReader.readLine();
             while (line != null) {
                 // Ignore the line in the following cases.
                 if (line.startsWith("#") || line.trim().equals("")) {
                     line = bufferedReader.readLine();
                     continue;
                 // Else - add the content of the line as a string to the list.
                 } else {
                     relevantLines.add(line);
                 }
                 line = bufferedReader.readLine();
             }
         } catch (Exception e) {
             // Throw runtime exception if an error occurred while reading the file.
             throw new RuntimeException(e.getMessage());
         }
        return relevantLines;
    }

    /**
     * Draw the background of a block.
     * @param value the string that contains the info about the background.
     * @return a block background.
     */
    private static BlockBackground blockBackgronud(String value) {
        try {
            // If it's a color background.
            if (value.startsWith("color(")) {
                ColorsParser parser = new ColorsParser();
                Color color = parser.colorFromString(value);
                return new BlockColorBackground(color);
            // If it's an image background.
            } else if (value.startsWith("image(")) {
                value = value.replace("image(", "");
                value = value.replace(")", "");
                // value = value.split("/")[1];
                Image image = ImageIO.read(
                        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(value)));
                return new BlockImageBackground(image);
            }
        // Throw exception if an error occurred while reading the file.
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    /**
     * Add the values that contain the information about the block to the block creator.
     * @param feature the name of the feature of the block.
     * @param value the string that contains the info about the background.
     * @param requiredValues a map that helps to ensure that all the required values were added.
     * @param blocksGenerator a blocks creator that should get all the required Values of the block.
     */
    private static void addValuesToBlockCreator(String feature, String value,
                                                Map<String, Integer> requiredValues, BlockGenerator blocksGenerator) {
        // Check which feature to add to the blocks generator, and map it to the number 1, indicating the value exists.
        if (feature.equals("symbol")) {
            blocksGenerator.setSymbol(value);
            requiredValues.put("symbol", 1);
        } else if (feature.equals("width")) {
            blocksGenerator.setWidth(Integer.parseInt(value));
            requiredValues.put("width", 1);
        } else if (feature.equals("height")) {
            blocksGenerator.setHeight(Integer.parseInt(value));
            requiredValues.put("height", 1);
        } else if (feature.equals("hit_points")) {
            blocksGenerator.setNumOfHits(Integer.parseInt(value));
            requiredValues.put("hit_points", 1);
        // The filling of the block - the block should be filled using one of the following value formats:
        // color(colorname) or color(RGB(x,y,z)) or image(filaneme.png).
        } else if (feature.startsWith("fill")) {
            requiredValues.put("fill", 1);
            // fill-k - if the number of hit-points of a block is > 1, it is possible to give a different appearance
            // for each number of remaining hit-points.
            if (feature.startsWith("fill-")) {
                feature = feature.replace("fill-", "");
                int hitsToBackground = Integer.parseInt(feature);
                BlockBackground background = blockBackgronud(value);
                blocksGenerator.addBackground(hitsToBackground, background);
            // Else - it's a regular fill.
            } else if (feature.startsWith("fill")) {
                BlockBackground background = blockBackgronud(value);
                blocksGenerator.setDefBackground(background);
            }
        } else if (feature.equals("stroke")) {
            ColorsParser parser = new ColorsParser();
            Color color = parser.colorFromString(value);
            blocksGenerator.setStroke(new Stroke(color));
        }
    }

    /**
     * Map between the number of hits of the block to the appropriate block background.
     * @param blockGenerator a block generator that contains all the required values of the block.
     */
    private static void mapHitsToBackgrounds(BlockGenerator blockGenerator) {
        Map<Integer, BlockBackground> hitsToBackground = blockGenerator.getHitsToBackground();
        BlockBackground defBackground = blockGenerator.getDefBackground();
        // Run from 1 to the number of hits and map each number of hits the to appropriate backgrounds.
        for (int i = 1; i <= blockGenerator.getNumOfHits(); i++) {
            if (!hitsToBackground.containsKey(i)) {
                if (blockGenerator.getDefBackground() == null) {
                    throw new RuntimeException("there is no background for the current num of hits");
                } else {
                    blockGenerator.addBackground(i, defBackground);
                }
            }
        }
    }


    /**
     * Check the validity of the Block Creator.
     * @param requiredValues a map that helps to check whether the block contains all the required values.
     */
    private static void checkBlockCreatorValidity(Map<String, Integer> requiredValues) {
        for (String s:requiredValues.keySet()) {
            if (requiredValues.get(s) != 1) {
                throw new RuntimeException("there are missing types on the block creator");
            }
        }
    }

    /**
     * Parse strings that contain information about blocks to block generators.
     * @param defaultValues an array that contains all the default values of the block.
     * @param blockDefinitions an array that contains all the definitions of the block.
     * (lines begin with the token bdef, followed by a space-separated list of properties.
     *  Each property has the form key:value).
     * @return a map that maps between strings and block generators.
     */
    private static Map<String, BlockGenerator> parseStringsToBlockCreators(String[] defaultValues,
        List<String> blockDefinitions) {
        Map<String, BlockGenerator> stringsToBlockCreators = new TreeMap<>();
        String[] splitedDefinitionsInfo;
        String[] featureAndValue;
        // Initialize the list of the required values.
        Map<String, Integer> requiredValues = new TreeMap<>();
        requiredValues.put("symbol", 0);
        requiredValues.put("width", 0);
        requiredValues.put("height", 0);
        requiredValues.put("hit_points", 0);
        requiredValues.put("fill", 0);
        // running on the block definitions.
        for (String blockDefinition : blockDefinitions) {
            BlockGenerator blocksGenerator = new BlockGenerator();
            // at first we initialize the block with the default values, if exist.
            if (defaultValues != null) {
                for (String defaultValue : defaultValues) {
                    featureAndValue = defaultValue.split(":");
                    String feature = featureAndValue[0];
                    String value = featureAndValue[1];
                    addValuesToBlockCreator(feature, value, requiredValues, blocksGenerator);
                }
            }
            splitedDefinitionsInfo = blockDefinition.split(" ");
            // after that we override the default values by the block definitions, if exist.
            for (String s : splitedDefinitionsInfo) {
                featureAndValue = s.split(":");
                String feature = featureAndValue[0];
                String value = featureAndValue[1];
                addValuesToBlockCreator(feature, value, requiredValues, blocksGenerator);
            }
            // mapping between the number of hits to the background.
            mapHitsToBackgrounds(blocksGenerator);
            // checking the block creator validity - if there are missing types, it will throw an exception.
            checkBlockCreatorValidity(requiredValues);
            // put in the map the mapping between the block symbol to the blocks generator
            stringsToBlockCreators.put(blocksGenerator.getSymbol(), blocksGenerator);
        }
        return stringsToBlockCreators;
    }

    /**
     * @param spacerDefinitions lines begin with the token sdef, followed by a space-separated list of properties.
     * Each property has the form key:value
     * @return a map that maps strings to integers which represent the widths of the spacers.
     */
    private static Map<String, Integer> parseStringsToSpacers(List<String> spacerDefinitions) {
        Map<String, Integer> stringsToSpacers = new TreeMap<>();
        String[] featuresAndValues;
        for (String spacerDefinition : spacerDefinitions) {
            // Find the definitions space.
            featuresAndValues = spacerDefinition.split(" ");
            String[] symbolAndValue = featuresAndValues[0].split(":");
            String[] widthAndValue = featuresAndValues[1].split(":");
            stringsToSpacers.put(symbolAndValue[1], (Integer.parseInt(widthAndValue[1])));
        }
        return stringsToSpacers;
    }

    /**
     * @param relevantLines a list of the relevant lines from the file of the block definitions.
     * @return BlocksFromSymbolsFactory object, which is in charge of creating blocks from symbols.
     */
    private static BlocksFromSymbolsFactory blocksFactory(List<String> relevantLines) {
        String[] defaultValues = null;
        List<String> blockDefinitions  = new ArrayList<>();
        List<String> spacerDefinitions  = new ArrayList<>();
        Map<String, BlockGenerator> blockCreators;
        Map<String, Integer> spacerWidths;
        String currentLine;
        for (String relevantLine : relevantLines) {
            currentLine = relevantLine;
            // Check whether the definition belongs to block or spacer.
            if (currentLine.startsWith("default ")) {
                currentLine = currentLine.replace("default ", "");
                defaultValues = currentLine.split(" ");
            } else if (currentLine.startsWith("bdef ")) {
                currentLine = currentLine.replace("bdef ", "");
                blockDefinitions.add(currentLine);
            } else if (currentLine.startsWith("sdef ")) {
                currentLine = currentLine.replace("sdef ", "");
                spacerDefinitions.add(currentLine);
            } else {
                throw new RuntimeException("The definition is not valid");
            }
        }
        blockCreators = parseStringsToBlockCreators(defaultValues, blockDefinitions);
        spacerWidths = parseStringsToSpacers(spacerDefinitions);
        // Return a BlocksFromSymbolsFactory object, which is in charge of creating blocks from symbols.
        return new BlocksFromSymbolsFactory(blockCreators, spacerWidths);
    }

    /**
     * Get a reader that is connected to the block-definitions file, read the definitions and return a
     * BlocksFromSymbolsFactory object.
     * @param reader is connected to the required file.
     * @return BlocksFromSymbolsFactory which is in charge of creating blocks from symbols.
     */
   public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
       List<String> relevantLines;
       try {
           relevantLines = relevantLines(reader);
           return blocksFactory(relevantLines);
       } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
       }
    }

} // class BlocksDefinitionReader