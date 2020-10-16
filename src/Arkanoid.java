
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.util.Objects;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import highscores.HighScoresTable;
import levels.GameFlow;
import levels.Tools;
import levels.LevelInformation;
import animation.MenuAnimation;
import menu.ShowHighScoresTask;
import menu.Task;
import readingfiles.LevelSets;
import readingfiles.LevelSpecificationReader;

/**
 * The Arkanoid class contains the main function that runs the Arkanoid game.
 */
public class Arkanoid {
    // Set the sizes of some static final variables for our Arkanoid game.
    public static final int SURFACE_WIDTH = 800;
    public static final int SURFACE_HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;

    /**
     * The main function creates a game object, initializes and runs it.
     * @param args the levels sets that the user wants to run.
     */
    public static void main(String[] args) {
        // The path of the default level settings.
        String pathOfLevelSets = "level_sets.txt";
        // If the user wants to run his own levels, he sends the path of the settings file as the 1st argument to main.
        if (args.length > 0) {
            pathOfLevelSets = args[0];
        }
        // Initialize some elementary objects that will be used for building the game.
        GUI gui = new GUI("Arkanoid", SURFACE_WIDTH, SURFACE_HEIGHT);
        DialogManager dialog = gui.getDialogManager();
        AnimationRunner animationRunner = new AnimationRunner(gui, FRAMES_PER_SECOND);
        KeyboardSensor keyboard = animationRunner.getKeyboardSensor();
        HighScoresTable highScoresTable = new HighScoresTable(5);
        // Tools is an object that helps to build the game.
        Tools gameBuilding = new Tools();
        gameBuilding.tryToLoadHighScores(highScoresTable);
        InputStream inputS = ClassLoader.getSystemClassLoader().getResourceAsStream(pathOfLevelSets);
        Reader reader = new InputStreamReader(Objects.requireNonNull(inputS));
        // Set the menu animation.
        MenuAnimation<Task<Void>> menu = new MenuAnimation<>(animationRunner, keyboard);
        // Set the sub-menu animation.
        MenuAnimation<Task<Void>> subMenu = new MenuAnimation<>(animationRunner, keyboard);
        // Initialize a map that maps between the name of a level and its path.
        Map<String, String> nameAndPath = LevelSets.mapToNameAndPath(reader);
        // Iterate over the map and map the information to level sets.
        for (Map.Entry<String, String> entry: nameAndPath.entrySet()) {
            subMenu.addSelection(entry.getKey().split(":")[0],
                    "for " + entry.getKey().split(":")[1] + " levels", () -> {
                        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(
                            entry.getValue());
                        Reader secondReader = new InputStreamReader(Objects.requireNonNull(input));
                        List<LevelInformation> levels = LevelSpecificationReader.fromReader(secondReader);
                        // Create the animation of the game.
                        GameFlow game = new GameFlow(animationRunner, animationRunner.getKeyboardSensor(),
                            dialog, highScoresTable);
                        game.runLevels(levels);
                        return null;
                    });
        }
        // Add the sub-menu option of starting a game.
        menu.addSubMenu("s", "to start a new game", subMenu);
        // Add the selection of showing the High Scores table.
        menu.addSelection("t", "for high scores table",
                new ShowHighScoresTask(animationRunner, new KeyPressStoppableAnimation(keyboard,
                        KeyboardSensor.SPACE_KEY, new HighScoresAnimation(highScoresTable))));
        // Add the selection of quiting the game.
        menu.addSelection("q", "to quit",
                () -> {
                    System.exit(0);
                    return null;
                });

        // Run the tasks of the menu.
        while (true) {
            animationRunner.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.reset();
        }
    }

} // Arkanoid class