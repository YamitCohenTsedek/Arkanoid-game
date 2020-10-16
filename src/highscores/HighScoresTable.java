package highscores;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * The HighScoresTable class manages the table of the high scores.
 */
public class HighScoresTable {
    // Declare the members of the class.
    private List<ScoreInfo> highScores;
    // Set a separator string to separate the different users & scores when reading and writing the data.
    private static final String SEPARATOR = ";";
    private int size;

    /**
     * Create an empty high scores table of the specified size.
     * @param size the size of the high scores table - the amount of the top scores that the table holds.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            this.highScores.add(new ScoreInfo("", -1));
        }
    }

    /**
     * Add a high score to the table.
     * @param score the information of the score that should be added to the table.
     */
    public void add(ScoreInfo score) {
        int rank = getRank(score.getScore());
        // Add the score only if its rank is not bigger than the size of the table.
        if (rank <= this.size) {
            this.highScores.add(rank - 1, score);
            // Remove the last number in the list, to keep the size of the table.
            if (this.highScores.size() > this.size) {
                this.highScores.remove(this.highScores.size() - 1);
            }
        }
    }

    /**
     * @return the size of the table.
     */
    public int size() {
        return this.size;
    }

    /**
     * The list is sorted such that the highest scores come first.
     * @return the current top high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * Return the rank of the current score.
     * Rank 1 means this score is the current highest score in the high scores table.
     * Rank 'size' means this score is the current lowest score in the high scores table.
     * Rank > 'size' means this score is too low and will not be added to the high scores table.
     * @param score the current score we want to add to the high scores table.
     * @return the rank of the current score.
     */
    public int getRank(int score) {
        if (this.highScores.size() == 0) {
            return 1;
        }
        for (int i = 0; i < this.highScores.size(); i++) {
            if (score > this.highScores.get(i).getScore()) {
                return i + 1;
            }
        }
        return this.highScores.size() + 1;
    }

    /**
     * Load the data of the high scores table from the given file. The Current table is cleared.
     * @param filename the name of the file to load the data from it.
     * @throws IOException in case the file loading failed.
     */
    public void load(File filename) throws IOException {
        HighScoresTable highScoresTable = loadFromFile(filename);
        if (highScoresTable == null) {
            throw new IOException();
        } else {
            this.highScores = highScoresTable.highScores;
        }
    }

    /**
     * Save the data of the high scores table in the specified file.
     * @param filename the name of the file to save the data in it.
     * @throws IOException in case the file loading failed.
     */
    public void save(File filename) throws IOException {
        // Create a string buffer for fast appending.
        StringBuilder sb = new StringBuilder();
        String nameOfFile = filename.getName();

        // Append all the fields, separating them with a separator.
        for (ScoreInfo highScore : this.highScores) {
            sb.append(highScore.getName());
            sb.append(SEPARATOR);
            sb.append(highScore.getScore());
            sb.append(SEPARATOR);
        }

        PrintWriter writer = null;
        // Write the result in the file.
        try {
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(nameOfFile)));
            writer.print("");
            writer.print(sb.toString());
        // Finally block to make sure the writer is closed even if an exception occurred.
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Read the data of the high scores table from the specified file and return it.
     * If the file does not exist, or if there is a problem with reading it, return an empty table.
     * @param filename the name of the file to load the data from it.
     * @return the data of the high scores table, or an empty table.
     */
    public static HighScoresTable loadFromFile(File filename) throws IOException {
        String nameOfFile = filename.getName();
        StringBuilder contentBuffer = new StringBuilder();
        BufferedReader reader = null;
        HighScoresTable highScoresTable;
        try {
             reader = new BufferedReader(
                          new InputStreamReader(
                             new FileInputStream(nameOfFile)));
               // Read all lines into the buffer.
               String line = reader.readLine();
               while (line != null) {
                   contentBuffer.append(line.trim());
                   line = reader.readLine();
               }
        } catch (IOException e) {
            throw new IOException();
        } // Finally block to make sure the reader is closed even if an exception occurred.
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            highScoresTable = new HighScoresTable(5);
            String[] parts = contentBuffer.toString().split(SEPARATOR);
            String name = "";
            int score;
            for (int i = 0; i < parts.length; i++) {
                if (i % 2 == 0) {
                    name = parts[i];
                } else {
                    score = Integer.parseInt(parts[i]);
                    ScoreInfo scoreInfo = new ScoreInfo(name, score);
                    highScoresTable.add(scoreInfo);
                }
            }
            return highScoresTable;
         } catch (RuntimeException e) {
            return null;
         }
    }

} // class HighScoresTable