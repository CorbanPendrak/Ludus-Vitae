package src;
/*
 * File: GraphicCell.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Display cell for GUI
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;

public class GraphicCell extends Cell implements DisplayCell, ActionListener {
    public static Color[] display = {Color.WHITE, Color.BLACK};
    public JButton button;
    public static int size;

    public GraphicCell() { this(0); }
    public GraphicCell(int state) {
        if (state >= display.length) {
            state = display.length - 1;
        }
        if (size == 0) {
            size = 15;
        }

        this.button = new JButton();
        this.button.setBackground(display[state]);
        button.setPreferredSize(new Dimension(size, size));
        button.setSize(new Dimension(size, size));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addActionListener(this);
        //button.removeKeyListener(button.getKeyListeners());
        
        setState(state);
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        button.setBackground(display[state]);
    }

    public void toggleState() {
        setState((state + 1) % display.length);
    }

    public void display() {}
    public void display(JFrame frame) {
        frame.add(button);
    }

    public int displayOptions() {
        return display.length;
    }

    public void actionPerformed(ActionEvent e) {
        toggleState();
    }

    public void changeSize(int width) {
        size = width;
        button.setSize(new Dimension(size, size));
        button.setPreferredSize(new Dimension(size, size));
    }
}
