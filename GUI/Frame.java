package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame
{
    private int MAZE_PANEL_HEIGHT = 900;
    private int MAZE_PANEL_WIDTH = 900;
   // private String BACKGROUND_IMG_PATH = "GUI/background.jpg";
   // private JLabel backgroundImg;
    private Panel mazePanel;

    public Frame()
    {
        
        setLayout(new BorderLayout()); 
        mazePanel = new Panel(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT);
        add(mazePanel,BorderLayout.CENTER);
        
        
        pack();
        mazePanel.buildMaze();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
