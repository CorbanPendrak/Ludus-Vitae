/*
 * File: DisplayBoard.java
 * Author: Corban Pendrak
 * Date: 08Nov25
 * Purpose: Interface for display boards
 */

public interface DisplayBoard {
    public void display();
    public void toggleState(int i, int j);
    public void setState(int i, int j, int state);
    public void step();
}
