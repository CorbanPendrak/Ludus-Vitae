/*
 * File: Options.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: GUI Tool bar options
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Point;

public class Options extends JToolBar {
    public Boolean paused;
    public Boolean resizing;
    public Boolean wrapping;
    public int speed;
    public Point corner;
    public GraphicBoard board;
    //public JCheckBoxMenuItem pausedMenuItem;
    public JFrame frame;
    protected int width;
    protected int height;

    public Options(GraphicBoard board) {
        this.paused = false;
        this.resizing = false;
        this.wrapping = true;
        this.speed = 100;
        this.board = board;
        this.frame = board.frame;
        this.corner = frame.getLocation();

        // Setup Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu;
        JMenuItem menuItem;
        ButtonGroup buttonGroup;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

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
                paused = true;
                load(frame);
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        Examples examples = new Examples(board);
        JMenu exampleMenu = new JMenu("Examples");
        for (int i = 0; i < Examples.exampleNames.length; i++) {
            int index = i;
            menuItem = new JMenuItem(Examples.exampleNames[i]);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    examples.runExample(index);
                }
            });
            exampleMenu.add(menuItem);
        }
        menu.add(exampleMenu);
        menuBar.add(menu);

        menu = new JMenu("Speed");
        menu.setMnemonic(KeyEvent.VK_S);
        buttonGroup = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("x0.25");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 800;
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("x1");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 200;
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("x2");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 100;
            }
        });
        rbMenuItem.setSelected(true);
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("x8");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                speed = 25;
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        menuBar.add(menu);

        menu = new JMenu("Board");
        menuItem = new JMenuItem("Resize");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                board.resize(frame);
                frame.pack();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("Wrap");
        cbMenuItem.setMnemonic(KeyEvent.VK_W);
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                wrapping = !wrapping;
            }
        });
        cbMenuItem.setSelected(true);
        menu.add(cbMenuItem);
        cbMenuItem = new JCheckBoxMenuItem("Pause");
        cbMenuItem.setMnemonic(KeyEvent.VK_P);
        cbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                paused = !paused;
            }
        });
        //this.pausedMenuItem = cbMenuItem;
        menu.add(cbMenuItem);
        menu.addSeparator();
        buttonGroup = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Small");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                board.changeSize(5);
            }
        });
        buttonGroup.add(rbMenuItem);
        menu.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Medium");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                board.changeSize(15);
            }
        });
        rbMenuItem.setSelected(true);
        buttonGroup.add(rbMenuItem);
        menu.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Large");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                board.changeSize(25);
            }
        });
        buttonGroup.add(rbMenuItem);
        menu.add(rbMenuItem);
        menuBar.add(menu);

        menu = new JMenu("Theme");
        rbMenuItem = new JRadioButtonMenuItem("Standard");
        buttonGroup = new ButtonGroup();
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                GraphicCell.display[0] = Color.WHITE;
                GraphicCell.display[1] = Color.BLACK;
                frame.getContentPane().setBackground(GraphicCell.display[0]);
                frame.getContentPane().setForeground(GraphicCell.display[0]);
                board.toggleAll();
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Dark");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                GraphicCell.display[0] = new Color(53, 53, 53);
                GraphicCell.display[1] = new Color(122, 122, 122);
                frame.getContentPane().setBackground(GraphicCell.display[0]);
                frame.getContentPane().setForeground(GraphicCell.display[0]);
                board.toggleAll();
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Neon");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                GraphicCell.display[0] = new Color(15, 15, 15);
                GraphicCell.display[1] = new Color(17, 255, 5);
                frame.getContentPane().setBackground(GraphicCell.display[0]);
                frame.getContentPane().setForeground(GraphicCell.display[0]);
                board.toggleAll();
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        rbMenuItem.setSelected(true);
        rbMenuItem = new JRadioButtonMenuItem("Custom");
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Color background = JColorChooser.showDialog(frame, "Background", GraphicCell.display[0]);
                Color foreground = JColorChooser.showDialog(frame, "Highlighted", GraphicCell.display[1]);
                GraphicCell.display[0] = background;
                GraphicCell.display[1] = foreground;
                frame.getContentPane().setBackground(GraphicCell.display[0]);
                frame.getContentPane().setForeground(GraphicCell.display[0]);
                board.toggleAll();
            }
        });
        menu.add(rbMenuItem);
        buttonGroup.add(rbMenuItem);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
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
                        curr |= board.cells.get(i).get(j).state;
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
                ArrayList<ArrayList<GraphicCell>> cells = new ArrayList<ArrayList<GraphicCell>>();
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
                board.setCells(cells);
                frame.pack();

                stream.close();
            } catch (Exception e) { System.out.println(e); }
        }
        resizing = false;
    }

    public void setPaused(Boolean isPaused) {
        paused = isPaused;
        //pausedMenuItem.setSelected(isPaused);
    }

    public void togglePaused() {
        setPaused(!paused);
    }
}
