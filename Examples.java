/*
 * File: Options.java
 * Author: Corban Pendrak
 * Date: █ Nov25
 * Purpose: Examples
 */

import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.Dimension;

public class Examples {
    public static String[] exampleNames = {"Glider", "Spaceship", "Spaceships!!!"};
    public GraphicBoard board;

    public Examples(GraphicBoard board) {
        this.board = board;
    }

    public void runExample(int index) {
        String[][] states = {
            {
                "          ",
                "          ",
                "          ",
                "     █    ",
                "      █   ",
                "    ███   ",
                "          ",
                "          ",
                "          ",
                "          ",
            },
            {
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                 ██                     ",
                "               █    █                   ",
                "                     █                  ",
                "               █     █                  ",
                "                ██████                  ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
                "                                        ",
            },
            {
                "                                            ",
                "  ██         ██         ██         ██       ",
                "█    █     █    █     █    █     █    █     ",
                "      █          █          █          █    ",
                "█     █    █     █    █     █    █     █    ",
                " ██████     ██████     ██████     ██████    ",
                "                                            ",
                "                                            ",
                "  ██         ██         ██         ██       ",
                "█    █     █    █     █    █     █    █     ",
                "      █          █          █          █    ",
                "█     █    █     █    █     █    █     █    ",
                " ██████     ██████     ██████     ██████    ",
                "                                            ",
                "                                            ",
                "  ██         ██         ██         ██       ",
                "█    █     █    █     █    █     █    █     ",
                "      █          █          █          █    ",
                "█     █    █     █    █     █    █     █    ",
                " ██████     ██████     ██████     ██████    ",
                "                                            ",
                "                                            ",
            },
        };

        try {
            int height = states[index].length;
            int width = states[index][0].length();
            System.out.println("(" + height + ", " + width + ")");
            board.frame.getContentPane().removeAll();
            board.frame.setSize(new Dimension(width*GraphicCell.size + 1, height*GraphicCell.size + 1));
            board.frame.setLayout(new GridLayout(height, width));
            ArrayList<ArrayList<GraphicCell>> cells = new ArrayList<ArrayList<GraphicCell>>();
            for (int i = 0; i < height; i++) {
                cells.add(new ArrayList<GraphicCell>());
                for (int j = 0; j < width; j++) {
                    char c;
                    try {
                        c = states[index][i].charAt(j);
                    } catch (Exception e) {
                        c = '0';
                    }
                    GraphicCell newCell = new GraphicCell((c == '1' || c == '█') ? 1 : 0 );
                    cells.get(i).add(newCell);
                    board.frame.add(newCell.button);
                }
            }

            board.setCells(cells);
            board.frame.pack();
        } catch (Exception e) { System.out.println(e); }
    }
}
