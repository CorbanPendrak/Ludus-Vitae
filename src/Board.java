package src;

import java.util.ArrayList;

/**
 * Base class for boards
 * @author Corban Pendrak
 * @since 08Nov25
 */
public class Board {
    /** Width of the board */
    protected int width;
    /** Height of the board */
    protected int height;
    /** List of PrintCells */
    protected ArrayList<ArrayList<PrintCell>> cells;

    /**
     * Class constructor with default 20 height and 60 width
     */
    public Board() {
        this(20, 60);
    }

    /** 
     * Class constructor with input height and width
     * @param height Height of the board
     * @param width Width of the board
     */
    public Board(int height, int width) {
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

    /**
     * Toggle the state of a specific cell
     * @param i Row value of cell to change
     * @param j Column value of cell to change
     */
    public void toggleState(int i, int j) {
        Cell cell = cells.get(i).get(j);
        setState(i, j, (cell.getState() + 1) % cell.displayOptions());
    }

    /**
     * Set state of a spefic cell directly
     * @param i Row value of cell to change
     * @param j Column value of cell to change
     * @param state State to change cell to
     */
    public void setState(int i, int j, int state) {
        cells.get(i).get(j).setState(state);
    }

    /**
     * Count alive neighbors of specific cell
     * @param i Row value of cell
     * @param j Column of cell
     * @return Number of alive cells
     */
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

    /**
     * Count alive neighbors of specific cell with wrapping
     * @param i Row value of cell
     * @param j Column of cell
     * @return Number of alive cells with wrap
     */
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

    /**
     * Iterate entire board
     */
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
