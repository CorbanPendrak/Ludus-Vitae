/*
 * File: PrintBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display board for printing
 */

import java.util.ArrayList; 

public class PrintBoard {
    // Attributes
    protected ArrayList<ArrayList<PrintCell>> cells;
    protected int width;
    protected int height;

    // Constructors
    public PrintBoard() {
        this(10, 20);
    }

    public PrintBoard(int height, int width) {
        this.width = width;
        this.height = height;

        cells = new ArrayList<ArrayList<PrintCell>>();
        for (int i = 0; i < height; i++) {
            cells.add(new ArrayList<PrintCell>());
            for (int j = 0; j < width; j++) {
                cells.get(i).add(new PrintCell());
            }
        }
    }

    /* Methods */
    
    // Purpose: display top/bottom border
    private String displayBorder() {
        String border = "+-";
        for (int i = 0; i < width; i++) {
            border += "-";
        }
        border += "-+\n";

        return border;
    }

    // Purpose: display board returned as string
    public String display() {
        String board = "";
        
        board += displayBorder();

        for (int i = 0; i < height; i++) {
            board += "| ";

            for (int j = 0; j < width; j++) {
                board += cells.get(i).get(j);
            }

            board += " |\n";
        }

        board += displayBorder();

        return board;
    }

    public String toString() {
        return display();
    }
}
