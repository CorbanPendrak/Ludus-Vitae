package src;

/**
 * Interface for display boards
 * @author Corban Pendrak
 * @since 08Nov25
 */
public interface DisplayBoard {
    /** Graphical display */
    public void display();
    /** Toggle the state of a cell */
    public void toggleState(int i, int j);
    /** Set the state of a cell */
    public void setState(int i, int j, int state);
    /** Interate the board's state */
    public void step();
}
