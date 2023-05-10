package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{   private String BACKGROUND_IMG_PATH = "GUI/control_background.jpg";
    
    private Image background;
    private JButton buildMazeButton;
    private JButton depthFirstSearchButton;
    
    
    public ButtonPanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(3,1));

        try
        {
            background = ImageIO.read(new File(BACKGROUND_IMG_PATH));
        }
        catch(Exception e)
        {
            setBackground(Color.BLACK);
        }


        buildMazeButton = new JButton("NEW MAZE");
        buildMazeButton.setPreferredSize(new Dimension(150, 30));
        depthFirstSearchButton = new JButton("DFS");
        add(buildMazeButton);
        
       // add(depthFirstSearchButton);
    }    


    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, null);
    }
}
