/*
 * File: Cell.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Abstract class for cells
 */

public abstract class Cell {
    protected int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
