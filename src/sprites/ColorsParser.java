package sprites;

import java.awt.Color;

/**
 * The ColorsParser class parses a color definition and returns the specified color.
 */
public class ColorsParser {

     /**
      * @param s a string that contains the color definition.
      * @return the specified color.
      */
    public Color colorFromString(String s) {
        // Color definition by RGB.
        if (s.startsWith("color(RGB(")) {
            s = s.replace("color(RGB(", "");
            s = s.replace("))", "");
            String[] splitReg = s.split(",");
            if (splitReg.length != 3) {
                throw new RuntimeException("missing RGB definitions");
            }
            int[] intsColorByRGB = new int[3];
            for (int i = 0; i < 3; i++) {
                intsColorByRGB[i] = Integer.parseInt(splitReg[i]);
            }
            return new Color(intsColorByRGB[0], intsColorByRGB[1], intsColorByRGB[2]);

        // Color definition by the name of the color.
        } else {
            s = s.replace("color(", "");
            s = s.replace(")", "");
            switch (s) {
                case "black":
                    return Color.black;
                case "blue":
                    return Color.blue;
                case "cyan":
                    return Color.cyan;
                case "gray":
                    return Color.gray;
                case "lightGray":
                    return Color.lightGray;
                case "darkGray":
                    return Color.darkGray;
                case "green":
                    return Color.green;
                case "orange":
                    return Color.orange;
                case "pink":
                    return Color.pink;
                case "red":
                    return Color.red;
                case "white":
                    return Color.white;
                case "yellow":
                    return Color.yellow;
                default:
                   throw new RuntimeException("this color is undefined");
           } // switch case
       } // else
    } // colorFromString

} // class ColorsParser
