/*
 * File: GraphicBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display board for GUI
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.lang.Thread;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Point;

public class GraphicBoard implements DisplayBoard{
    protected int width;
    protected int height;
    protected Boolean paused;
    protected Boolean resizing;
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
        this.resizing = false;
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
        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("File");
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                save(frame);
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                load(frame);
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);

        menu = new JMenu("Speed");
        menu.setMnemonic(KeyEvent.VK_S);
        menuItem = new JMenuItem("x0.25");
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

        menu = new JMenu("Size");
        menuItem = new JMenuItem("Small");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int newSize = 5;
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
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Medium");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int newSize = 15;
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
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Large");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                int newSize = 25;
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
        if (resizing) {
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
            cells.add((newCorner.getY() == corner.getY()) ? i : i-height, new ArrayList<GraphicCell>());
            for (int j = 0; j < width; j++) {
                cells.get((newCorner.getY() == corner.getY()) ? i : i-height)
                    .add(new GraphicCell());
            }
        }
        for (int i = newHeight; i < height; i++) {
            try {
                cells.remove((newCorner.getY() == corner.getY()) ? i : i-newHeight);
            } catch (Exception e) {}
        }
        for (int i = 0; i < newHeight; i++) {
            for (int j = width; j < newWidth; j++) {
                cells.get(i).add((newCorner.getX() == corner.getX()) ? j : j-width, new GraphicCell());
            }
            for (int j = newWidth; j < width; j++) {
                try {
                    cells.get(i).remove((newCorner.getX() == corner.getX()) ? j : j-newWidth);
                } catch (Exception e) {}
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
            //frame.pack();
        } catch (Exception e) { System.out.println(e); }
        //resizing = false;
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

    public void save(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                System.out.println("Saving to " + file.getAbsolutePath());
                OutputStream stream = new FileOutputStream(file.getAbsolutePath() + ".bin");
                stream.write(height);
                stream.write(width);
                //System.out.println("Height: " + height + "; Width: " + width);

                byte[] bytes = new byte[(height*width)/ 8];
                byte curr = 0x0;
                int count = 0;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        curr <<= 1;
                        curr |= cells.get(i).get(j).state;
                        //System.out.print(cells.get(i).get(j).state);
                        count++;
                        if (count >= 8) {
                            bytes[(width*i + j)/8] = curr;
                            curr = 0x0;
                            count = 0;
                        }
                    }
                    //System.out.println();
                }
                if (curr != 0) {
                    curr <<= 8 - count;
                    bytes[bytes.length - 1] = curr;
                }
                stream.write(bytes);

                stream.close();
                
            } catch (Exception e) { System.out.println(e); }
        }
    }

    public void load(JFrame frame) {
        resizing = true;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                InputStream stream = new FileInputStream(file.getAbsolutePath());
                height = stream.read();
                width = stream.read();
                //System.out.println("Height: " + height + "; Width: " + width);

                byte[] data = stream.readAllBytes();
                byte curr;
                
                frame.getContentPane().removeAll();
                frame.setSize(new Dimension(width*GraphicCell.size + 1, height*GraphicCell.size + 1));
                frame.setLayout(new GridLayout(height, width));
                cells = new ArrayList<ArrayList<GraphicCell>>();
                for (int i = 0; i < data.length; i++) {
                    curr = data[i];
                    for (int j = 0; j < 8; j++) {
                        int pos = i*8 +j;
                        if (pos % width == 0) {
                            cells.add(new ArrayList<GraphicCell>());
                            //System.out.println();
                        }
                        GraphicCell newCell = new GraphicCell((curr & 0x80) == 128 ? 1 : 0);
                        //System.out.print((curr & 0x80) == 128 ? 1 : 0);
                        cells.get(pos / width).add(newCell);
                        frame.add(newCell.button);
                        curr <<= 1;
                    }
                }
                frame.pack();

                stream.close();
            } catch (Exception e) { System.out.println(e); }
        }
        resizing = false;
    }
}
