package sprites;

import java.awt.Color;
import java.util.Random;

/**
 * The Colors class is a helper class that helps to create colors for the game.
 */
public class Colors {

    /**
     * Return a rainbow color, depending on the given argument. Each number in the range 0-6 represents a rainbow color,
     * in accordance with the order of the rainbow colors (0- red, 1- orange etc..).
     * If the given number is not in the range 0-6, return a random rainbow color.
     * @return the rainbow color corresponding to given the number.
     * @param colorIndex the number that represents the rainbow color.
     */
    public Color setRainbowColor(int colorIndex) {
        Random rand = new Random();
        Color[] rainbowColor = {Color.red, Color.orange, Color.yellow.brighter(), Color.green, Color.cyan,
        Color.blue, Color.magenta};
        // If the number is in the range 0-6, return the rainbow color corresponding to the index.
        if (colorIndex >= 0 && colorIndex < rainbowColor.length) {
            return rainbowColor[colorIndex];
        }
        // Else - return a random rainbow color.
        return rainbowColor[rand.nextInt(rainbowColor.length)];
    }

} // class Colors.