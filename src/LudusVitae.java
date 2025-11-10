package src;
/*
 * File: LudusVitae.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Main class for the Ludus Vitae application.
 */

public class LudusVitae {
    public static void main(String[] args) {
        System.out.println("Welcome to Ludus Vitae!\n");

        DisplayBoard board = new GraphicBoard();

        board.toggleState(5, 10);
        board.toggleState(6, 11);
        board.toggleState(7, 11);
        board.toggleState(7, 10);
        board.toggleState(7, 9);

        board.display();
    }
}