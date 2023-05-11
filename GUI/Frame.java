package GUI;
import java.awt.BorderLayout;
import javax.swing.JFrame;


public class Frame extends JFrame
{
    private final int MAZE_PANEL_HEIGHT = 900;
    private final int MAZE_PANEL_WIDTH = 1200;
    private final int BUTTON_PANEL_WIDTH = 300;
    private Panel mazePanel;
    private ButtonPanel buttonPanel;

    public Frame()
    {
        setLayout(new BorderLayout()); 
        mazePanel = new Panel(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT);
        add(mazePanel,BorderLayout.CENTER);
        buttonPanel = new ButtonPanel(BUTTON_PANEL_WIDTH, MAZE_PANEL_HEIGHT, mazePanel);
        add(buttonPanel, BorderLayout.EAST);
        pack();
        //mazePanel.buildMaze();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
