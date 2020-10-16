package menu;

/**
 * The Task interface represents something that needs to happen or something that can run() and return a value.
 * @param <T> the specific type of the object that Task gets.
 */
public interface Task<T> {
    /**
     * Run the task.
     * @return T return value of the task.
     */
    T run();

} // Task interface