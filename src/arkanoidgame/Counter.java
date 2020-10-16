package arkanoidgame;

/**
 * The Counter class is used for counting.
 */
public class Counter {
    // Declare the members of the class.
    private int value;

    /**
     * Constructor.
     * @param value the value that the counter initialized with it.
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * @param number the number that should be added to the current value of the counter.
     */
    public void increase(int number) {
        this.value = this.value + number;
    }

    /**
     * @param number the number that should be subtracted from the current value of the counter.
     */
    public void decrease(int number) {
        this.value = this.value - number;
    }

    /**
     * @return the current value of the counter.
     */
    public int getValue() {
        return this.value;
    }

} // class Counter