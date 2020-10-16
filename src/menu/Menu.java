package menu;
import animation.Animation;

/**
 * The Menu interface - the menu is displayed on the screen, thus it extends Animation class.
 * @param <T> the type of the menu.
 */
public interface Menu<T> extends Animation {

    /**
     * Add a menu selection.
     * @param key a key press that the menu waits for.
     * @param message a menu option to print.
     * @param returnVal the result of selecting this menu option.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the status of the menu.
     */
    T getStatus();

    /**
     * Add a sub-menu to the primary menu.
     * @param key a key press that the menu waits for.
     * @param message a menu option to print.
     * @param subMenu the sub-menu resulting of selecting this menu option.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Initialize the status of the menu.
     */
    void reset();

} // interface Menu<T>