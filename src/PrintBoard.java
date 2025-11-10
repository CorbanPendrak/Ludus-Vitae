package src;
/*
 * File: PrintBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display board for printing
 */

public class PrintBoard extends Board implements DisplayBoard {
    // Attributes

    // Constructors
    public PrintBoard() { super(); }
    public PrintBoard(int height, int width) { super(height, width); }

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
    public void display() {
        System.out.print(this);
    }

    public String toString() {
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

    public void clearDisplay() {
        System.out.print(String.format("\033[%dA\033[2K", (height + 2)));
    }
}
