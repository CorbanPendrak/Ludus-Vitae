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
import java.awt.Point;

public class GraphicBoard implements DisplayBoard, KeyListener {
    protected int width;
    protected int height;
    protected Boolean paused;
    protected int speed;
    protected Point corner;
    protected Timer resizeTimer;
    protected ArrayList<ArrayList<GraphicCell>> cells;

    public GraphicBoard() {
        this(20, 60);
    }

    public GraphicBoard(int height, int width) {
        this.width = width;
        this.height = height;
        this.paused = false;
        this.speed = 100;

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
        this.corner = frame.getLocation();
        frame.setResizable(true);
        frame.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                paused = !paused;
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        /*this.resizeTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });*/
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                /*if (resizeTimer == null) {
                    resizeTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(final ActionEvent e) {
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
                resize(frame);
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

        // Setup Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Speed");
        menu.setMnemonic(KeyEvent.VK_S);
        JMenuItem menuItem = new JMenuItem("x0.25");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 800;
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("x1");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 200;
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("x2");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 100;
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("x8");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 25;
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        

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
                Thread.sleep(speed);
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

    private void resize(JFrame frame) {
        //frame.setResizable(false);
        int newHeight = (int) (frame.getSize().getHeight() / 15);
        int newWidth = (int) (frame.getSize().getWidth() / 15);
        height = cells.size();
        width = cells.get(0).size();
        if (newHeight == height && newWidth == width) {
            return;
        }
        Point newCorner = frame.getLocation();
        for (int i = height; i < newHeight; i++) {
            cells.add((newCorner.getY() == corner.getY()) ? i : i-height, new ArrayList<GraphicCell>());
            for (int j = 0; j < width; j++) {
                cells.get((newCorner.getY() == corner.getY()) ? i : i-height)
                    .add(new GraphicCell());
            }
        }
        for (int i = newHeight; i < height; i++) {
            cells.remove((newCorner.getY() == corner.getY()) ? i : i-newHeight);
        }
        for (int i = 0; i < height; i++) {
            for (int j = width; j < newWidth; j++) {
                cells.get(i).add((newCorner.getX() == corner.getX()) ? j : j-width, new GraphicCell());
            }
            for (int j = newWidth; j < width; j++) {
                System.out.println("Removing " + newWidth + "-" + width);
                cells.get(i).remove((newCorner.getX() == corner.getX()) ? j : j-newWidth);
            }
        }
        height = (int) newHeight;
        width = (int) newWidth;
        corner = newCorner;

        try {
            frame.setLayout(new GridLayout(height, width));
            frame.getContentPane().removeAll();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    frame.add((cells.get(i).get(j)).button);
                }
            }
        } catch (Exception e) {}
        //frame.setResizable(true);
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
