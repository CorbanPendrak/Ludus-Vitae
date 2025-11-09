/*
 * File: Cell.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Base class for cells
 */

public abstract class Cell {
    protected int state;

    public int getState() {
        return state;
    }

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
