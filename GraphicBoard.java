/*
 * File: GraphicBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display board for GUI
 */

import java.awt.GridLayout;
import java.util.ArrayList;
import java.lang.Thread;
import javax.swing.*;
import java.awt.event.*;

public class GraphicBoard implements DisplayBoard, KeyListener {
    protected int width;
    protected int height;
    protected Boolean paused;
    protected ArrayList<ArrayList<GraphicCell>> cells;

    public GraphicBoard() {
        this(20, 60);
    }

    public GraphicBoard(int height, int width) {
        this.width = width;
        this.height = height;
        this.paused = false;

        cells = new ArrayList<ArrayList<GraphicCell>>();
        for (int i = 0; i < height; i++) {
            cells.add(new ArrayList<GraphicCell>());
            for (int j = 0; j < width; j++) {
                cells.get(i).add(new GraphicCell());
            }
        }
    }

    public void display() {
        JFrame frame = new JFrame("Ludus Vitae");
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace(); 
        }

        frame.setLayout(new GridLayout(height, width));
        frame.addKeyListener(this);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                paused = !paused;
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //frame.setBackground(Color.WHITE);
        //frame.setForeground(Color.WHITE);
        //frame.setUndecorated(false);
        try {
            frame.setOpacity(0.8f);
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                frame.add((cells.get(i).get(j)).button);
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        try{ 
            while (true) {
                Thread.sleep(100);
                if (!paused) {
                    step();
                }
            }
        }
        catch (Exception e) {
            
            System.out.println("Exception: " + e);
        }
    }

    public void step() {
        ArrayList<Pair> changes = new ArrayList<Pair>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbors = countNeighborsWrap(i, j);

                if (cells.get(i).get(j).getState() == 0) {
                    // Cell is dead
                    if (neighbors == 3) {
                        changes.add(new Pair(i, j));
                    }
                } else {
                    // Cell is alive
                    if (neighbors < 2 || neighbors > 3) {
                        changes.add(new Pair(i, j));
                    }
                }
            }
        }

        for (int i = 0; i < changes.size(); i++) {
            toggleState(changes.get(i).i, changes.get(i).j);
        }
    }

    public void toggleState(int i, int j) {
        Cell cell = cells.get(i).get(j);
        setState(i, j, (cell.getState() + 1) % cell.displayOptions());
    }

    public void setState(int i, int j, int state) {
        cells.get(i).get(j).setState(state);
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

    class Pair {
        public int i;
        public int j;
        public Pair(int i, int j) { this.i = i; this.j = j; }
    }

    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());

    }

    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode());

    }
}
