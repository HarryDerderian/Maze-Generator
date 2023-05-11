package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame
{
    private int MAZE_PANEL_HEIGHT = 900;
    private int MAZE_PANEL_WIDTH = 1200;
    private Panel mazePanel;
    private ButtonPanel buttonPanel;

    public Frame()
    {
        

        setLayout(new BorderLayout()); 
        mazePanel = new Panel(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT);
        add(mazePanel,BorderLayout.CENTER);
        buttonPanel = new ButtonPanel(300,MAZE_PANEL_HEIGHT);
        add(buttonPanel, BorderLayout.EAST);
        pack();
        mazePanel.buildMaze();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
