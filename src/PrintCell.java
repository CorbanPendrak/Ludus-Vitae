package src;
/*
 * File: PrintCell.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display cell for printing
 */


public class PrintCell extends Cell implements DisplayCell {
    // Attributes
    //private static String[] display = {"▢", "▣"}; 
    public static String[] display = {" ", "▣"}; //█"};

    // Constructors

    public PrintCell() {
        setState(0);
    }

    public PrintCell(int state) {
        if (state >= displayOptions()) {
            state = displayOptions() - 1;
        }

        setState(state);
    }

    // Methods
    public void display() {
        System.out.print(this);
    }

    public String toString() {
        return display[getState()];
    }

    public int displayOptions() {
        return display.length;
    }
}
