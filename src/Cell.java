package src;

/**
 * Base class for cells
 * @author Corban Pendrak
 * @since 08Nov25
 */
public abstract class Cell {
    /** Current state of cell */
    protected int state;

    /**
     * Get the current state
     * @return Current state
     */
    public int getState() {
        return state;
    }

    /**
     * Set the current state
     * @param state New current state
     */
    public void setState(int state) {
        if (state >= displayOptions()) {
            state = displayOptions() - 1;
        }

        this.state = state;
    }
    
    public int displayOptions() {
        return 2;
    }
}
