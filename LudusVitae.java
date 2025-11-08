/*
 * File: LudusVitae.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Main class for the Ludus Vitae application.
 */

 import java.lang.Thread;

public class LudusVitae {
    public static void main(String[] args) {
        System.out.println("Welcome to Ludus Vitae!\n");

        PrintBoard board = new PrintBoard(20, 60);

        board.cells.get(5).get(10).setState(1);
        board.cells.get(6).get(11).setState(1);
        board.cells.get(7).get(11).setState(1);
        board.cells.get(7).get(10).setState(1);
        board.cells.get(7).get(9).setState(1);

        try { 
            while (true) {
                System.out.print(board);
                board.step();
                Thread.sleep(100);
                System.out.print(board.clearDisplay());
            }
        }
        catch (Exception e) {
            
        }
        
    }
}