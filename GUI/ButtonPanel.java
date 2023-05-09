package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ButtonPanel extends JFrame
{
    
    
    private final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private JButton buildMazeButton;
    private JButton depthFirstSearchButton;
    
    
    public ButtonPanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setBackground(TRANSPARENT);
    }    
}
