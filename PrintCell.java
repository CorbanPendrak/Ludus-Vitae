/*
 * File: PrintCell.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display cell for printing
 */


public class PrintCell extends Cell {
    // Attributes
    //private static String[] display = {"▢", "▣"}; 
    private static String[] display = {" ", "▣"}; //█"};

    // Constructors
    public PrintCell() {
        setState(0);
    }

    public PrintCell(int state) {
        if (state > display.length) {
            state = display.length;
        }

        setState(state);
    }

    // Methods
    public String display() {
        return display[getState()];
    }

    public String toString() {
        return display();
    }
}
