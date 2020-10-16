package highscores;

import java.io.Serializable;

/**
 * The ScoreInfo class represents the info of a new score that should be added to the high scores table.
 */
public class ScoreInfo implements Serializable {
    // Declare the members of the class.
    private String name;
    private int score;
     // serialVersionUID facilitates versioning of serialized data. Its value is stored with the data when serializing.
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param name the name of the user.
     * @param score the score of the user.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the name of the user with the current score.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the current score.
     */
    public int getScore() {
        return this.score;
    }

} // class ScoreInfo