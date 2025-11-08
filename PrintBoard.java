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

    public String clearDisplay() {
        return String.format("\033[%dA\033[2K", (height + 2));
    }

    public int countNeighbors(int i, int j) {
        int neighbors = 0;

        // Upper row
        if (i > 0) {
            if (j > 0) {
                neighbors += cells.get(i-1).get(j-1).getState() == 1 ? 1 : 0; 
            }
            neighbors += cells.get(i-1).get(j).getState() == 1 ? 1 : 0;
            if (j < width - 1) {
                neighbors += cells.get(i-1).get(j+1).getState() == 1 ? 1 : 0;
            }
        }

        // Middle row
        if (j > 0) {
            neighbors += cells.get(i).get(j-1).getState() == 1 ? 1 : 0; 
        }
        if (j < width - 1) {
            neighbors += cells.get(i).get(j+1).getState() == 1 ? 1 : 0;
        }

        // Lower row
        if (i < height - 1) {
            if (j > 0) {
                neighbors += cells.get(i+1).get(j-1).getState() == 1 ? 1 : 0; 
            }
            neighbors += cells.get(i+1).get(j).getState() == 1 ? 1 : 0;
            if (j < width - 1) {
                neighbors += cells.get(i+1).get(j+1).getState() == 1 ? 1 : 0;
            }
        }
        
        return neighbors;
    }

    public int countNeighborsWrap(int i, int j) {
        int neighbors = 0;

        neighbors += cells.get((i-1 + height) % height).get((j-1 + width) % width).getState() == 1 ? 1 : 0;
        neighbors += cells.get((i-1 + height) % height).get(j).getState() == 1 ? 1 : 0;
        neighbors += cells.get((i-1 + height) % height).get((j+1) % width).getState() == 1 ? 1 : 0;

        neighbors += cells.get(i).get((j-1 + width) % width).getState() == 1 ? 1 : 0;
        neighbors += cells.get(i).get((j+1) % width).getState() == 1 ? 1 : 0;

        neighbors += cells.get((i+1) % height).get((j-1 + width) % width).getState() == 1 ? 1 : 0;
        neighbors += cells.get((i+1) % height).get(j).getState() == 1 ? 1 : 0;
        neighbors += cells.get((i+1) % height).get((j+1) % width).getState() == 1 ? 1 : 0; 

        return neighbors;
    }

    public void step() {
        ArrayList<ArrayList<PrintCell>> newCells = new ArrayList<ArrayList<PrintCell>>();

        for (int i = 0; i < height; i++) {
            newCells.add(new ArrayList<PrintCell>());
            for (int j = 0; j < width; j++) {
                int neighbors = countNeighborsWrap(i, j);

                if (cells.get(i).get(j).getState() == 0) {
                    // Cell is dead
                    if (neighbors == 3) {
                        newCells.get(i).add(new PrintCell(1));
                    } else {
                        newCells.get(i).add(new PrintCell(0));
                    }
                } else {
                    // Cell is alive
                    if (neighbors < 2 || neighbors > 3) {
                        newCells.get(i).add(new PrintCell(0));
                    } else {
                        newCells.get(i).add(new PrintCell(1));
                    }
                }
            }
        }

        this.cells = newCells;
    }
}
