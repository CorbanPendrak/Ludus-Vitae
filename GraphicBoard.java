/*
 * File: GraphicBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display board for GUI
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.lang.Thread;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Point;

public class GraphicBoard implements DisplayBoard{
    protected int width;
    protected int height;
    public ArrayList<ArrayList<GraphicCell>> cells;
    protected Options options;
    public JFrame frame;

    public GraphicBoard() {
        this(20, 60);
    }

    public GraphicBoard(int height, int width) {
        this.width = width;
        this.height = height;

        // Default theme
        GraphicCell.display[0] = new Color(15, 15, 15);
        GraphicCell.display[1] = new Color(17, 255, 5);

        cells = new ArrayList<ArrayList<GraphicCell>>();
        for (int i = 0; i < height; i++) {
            cells.add(new ArrayList<GraphicCell>());
            for (int j = 0; j < width; j++) {
                cells.get(i).add(new GraphicCell());
            }
        }
    }

    public void display() {
        frame = new JFrame("Ludus Vitae");
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace(); 
        }

        frame.setLayout(new GridLayout(height, width));
        frame.setResizable(true);
        frame.getContentPane().setBackground(GraphicCell.display[0]);
        frame.getContentPane().setForeground(GraphicCell.display[0]);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                options.togglePaused();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                options.setPaused(true);
                resize(frame);
                frame.pack();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        cells.get(i).get(j).setState(0);
                    }
                }
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        /*this.resizeTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });*/
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                /* 
                if (resizeTimer == null) {
                    resizeTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                            System.out.println(e.getSource() + " " + resizeTimer);
                            if (e.getSource() == resizeTimer) {
                                resizeTimer.stop();
                                resizeTimer = null;
                                resize(frame);
                            }
                        }
                    });
                    resizeTimer.start();
                } else {
                    resizeTimer.restart();
                }*/
                //resize(frame);
            }
        });
        //frame.setBackground(Color.WHITE);
        //frame.setForeground(Color.WHITE);
        //frame.setUndecorated(false);
        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            frame.setOpacity(0.8f);
        } catch (Exception e) {
            System.out.print("Transpareny Error: ");
            System.out.println(e);
        }


        this.options = new Options(this);
        

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
                Thread.sleep(options.speed);
                if (!options.paused) {
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
                int neighbors = options.wrapping ? countNeighborsWrap(i, j) : countNeighbors(i, j);

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

    public void resize(JFrame frame) {
        if (options.resizing) {
            return;
        }
        //resizing = true;
        //frame.setResizable(false);
        int newHeight = (int) (frame.getSize().getHeight() / GraphicCell.size);
        int newWidth = (int) (frame.getSize().getWidth() / GraphicCell.size);
        //System.out.println("(" + height + ", " + width + ")");
        height = cells.size();
        width = cells.get(0).size();
        if (newHeight == height && newWidth == width) {
            return;
        }
        Point newCorner = frame.getLocation();
        for (int i = height; i < newHeight; i++) {
            cells.add((newCorner.getY() == options.corner.getY()) ? i : i-height, new ArrayList<GraphicCell>());
            for (int j = 0; j < width; j++) {
                cells.get((newCorner.getY() == options.corner.getY()) ? i : i-height)
                    .add(new GraphicCell());
            }
        }
        for (int i = newHeight; i < height; i++) {
            try {
                cells.remove((newCorner.getY() == options.corner.getY()) ? i : i-newHeight);
            } catch (Exception e) {}
        }
        for (int i = 0; i < newHeight; i++) {
            for (int j = width; j < newWidth; j++) {
                cells.get(i).add((newCorner.getX() == options.corner.getX()) ? j : j-width, new GraphicCell());
            }
            for (int j = newWidth; j < width; j++) {
                try {
                    cells.get(i).remove((newCorner.getX() == options.corner.getX()) ? j : j-newWidth);
                } catch (Exception e) {}
            }
        }
        height = (int) newHeight;
        width = (int) newWidth;
        options.corner = newCorner;

        try {
            frame.setLayout(new GridLayout(height, width));
            frame.getContentPane().removeAll();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    frame.add((cells.get(i).get(j)).button);
                }
            }
            //frame.pack();
        } catch (Exception e) { System.out.println(e); }
        //resizing = false;
        //frame.setResizable(true);
    }

    public void toggleState(int i, int j) {
        Cell cell = cells.get(i).get(j);
        setState(i, j, (cell.getState() + 1) % cell.displayOptions());
    }

    public void toggleAll() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells.get(i).get(j).toggleState();
                cells.get(i).get(j).toggleState();
            }
        }
    }

    public void changeSize(int newSize) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells.get(i).get(j).changeSize(newSize);
            }
        }
        try {
            frame.setLayout(new GridLayout(height, width));
            frame.getContentPane().removeAll();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    frame.add((cells.get(i).get(j)).button);
                }
            }
            frame.pack();
        } catch (Exception ex) {}
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

    public void setCells(ArrayList<ArrayList<GraphicCell>> cells) {
        this.height = cells.size();
        this.width = cells.get(0).size();
        this.cells = cells;
    }
}
