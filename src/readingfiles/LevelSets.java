package readingfiles;

import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

/**
 * The LevelSets class maps a name of level set to its path.
 */
public class LevelSets {

    /**
     * @param reader a reader that is connected to a file that contains the information about the sets of the game.
     * @return a map that maps between level names to their paths.
     */
    public static Map<String, String> mapToNameAndPath(Reader reader) {
        Map<String, String> nameAndPath = new TreeMap<>();
        String line;
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String path;
        String name = "";
        try {
            line = lineNumberReader.readLine();
            while (line != null) {
                // Odd-numbered lines contain the level names.
                if (lineNumberReader.getLineNumber() % 2 != 0) {
                    name = line;
                // Even-numbered lines contain the corresponding filenames, containing the level specifications.
                } else {
                    path = line;
                    nameAndPath.put(name, path);
                }
                line = lineNumberReader.readLine();
             }

        } catch (Exception e) {
             System.out.println(e.getMessage());
        }
        return nameAndPath;
    } // mapToNameAndPath

} // class LevelSets.