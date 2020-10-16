package readingfiles;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import levels.LevelInformation;

/**
 * The LevelSpecificationReader class is in charge of reading the levels' information from a file and returning
 * a LevelInformation object.
 */
public class LevelSpecificationReader {

     /**
      * Take a file and split it to a list of strings, where each string represents a level.
      * @param reader is connected to the required file. Reads character streams.
      * @return a list of strings where each string represents a level.
      */
     public static List<String> splitIntoLevels(java.io.Reader reader) {
         // The buffered reader reads lines.
         BufferedReader bufferedReader;
         bufferedReader = new BufferedReader(reader);
         List<String> splitedLevels = new ArrayList<>();
         StringBuilder currentLevel = new StringBuilder();
         // Read all the relevant lines into the buffer.
         try {
             String line = bufferedReader.readLine();
             while (!(line == null)) {
                 while (!line.equals("END_LEVEL")) {
                     line = bufferedReader.readLine();
                     // Ignore the line in the following cases.
                     if (!line.startsWith("#") && !line.trim().equals("") && !line.equals("START_LEVEL")) {
                         // Add the content of the line as a string to the list.
                         currentLevel.append(line).append("\n");
                     }
                 }
                     splitedLevels.add(currentLevel.toString());
                     currentLevel = new StringBuilder();
                     line = bufferedReader.readLine();
             }
        } catch (Exception e) {
            // Throw an exception in case the file reading failed.
             throw new RuntimeException(e.getMessage());
        }
        return splitedLevels;
    }

    /**
     * @param levelContent a string that contains all the information about a certain level.
     * @return a map that maps between the names of the features of a level information and
     * the data of the definitions.
     */
    public static Map<String, String> splitLevelInformation(String levelContent) {
        Map<String, String> levelInfoMap = new TreeMap<>();
        String[] splitedLevelInfo = levelContent.split("\n");
        String[] splitLine;
        String runner;
        int i;
        int j = 1;
        for (i = 0; i < splitedLevelInfo.length; i++) {
            runner = splitedLevelInfo[i];
            if (runner.equals("START_BLOCKS")) {
                break;
            } else {
                splitLine = splitedLevelInfo[i].split(":");
                levelInfoMap.put(splitLine[0], splitLine[1]);
            }
        }
        i++;
        for (; i < splitedLevelInfo.length; i++) {
            runner = splitedLevelInfo[i];
            if (runner.equals("END_BLOCKS")) {
                break;
            } else {
                levelInfoMap.put("blocks_line_" + j, runner);
                j++;
            }
            levelInfoMap.put("blocks_line_" + j, runner);
        }
        levelInfoMap.put("num_of_blocks_lines", Integer.toString(j));
        return levelInfoMap;
    }

    /**
     * @param map a map that maps between the names of the features of a level information and
     * the data of the definitions (strings).
     * @return an object that implements the LevelInformation interface.
     */
    public static LevelCreation mapToObjects(Map<String, String> map) {
        return new LevelCreation(map);
    }

    /**
     * @param reader the reader is connected to the file that contains all the information of the levels.
     * @return LevelInformation list - LevelInformation is an object that contains an information of a level.
     */
    public static List<LevelInformation> fromReader(java.io.Reader reader) {
       List<LevelInformation> levels = new ArrayList<>();
       // Use a helper function to split the information of all the levels to information about each level separately.
       List<String> levelsContent = splitIntoLevels(reader);
        for (String s : levelsContent) {
            // Map between the features of the level and their values.
            Map<String, String> splitedLevelInfo = splitLevelInformation(s);
            LevelCreation newLevel = mapToObjects(splitedLevelInfo);
            levels.add(newLevel);
        }
        return levels;
    }

} // class LevelSpecificationReader.