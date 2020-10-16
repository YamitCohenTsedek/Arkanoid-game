package levels;

import java.awt.Color;

import biuoop.DrawSurface;
import sprites.Sprite;

/**
 * The LevelNameIndicator class represents the name of the level that is displayed at the top of the screen.
 */
public class LevelNameIndicator implements Sprite {
    // Declare the members of the class.
    private String gameLevelName;

    /**
     * Constructor.
     * @param gameLevelName the name of the level.
     */
    public LevelNameIndicator(String gameLevelName) {
        this.gameLevelName = gameLevelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Draw the name of the level.
        d.setColor(new Color(255, 255, 204));
        int centerBlockX = 500;
        int centerBlockY = 25;
        d.fillRectangle(0, 0, 800, 35);
        Color textColor = Color.black;
        d.setColor(textColor);
        d.drawText(centerBlockX, centerBlockY, "Level Name: " + this.gameLevelName, 18);
        d.drawText(75, 25, "\"p\" - pause" , 18);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

} // class LevelNameIndicator
