package GUI;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;


public class Frame extends JFrame
{
    private int FRAME_HEIGHT = 600;
    private int FRAME_WIDTH = 1200;
    private String BACKGROUND_IMG_PATH = "C:\\Users\\17143\\Desktop\\Maze Generator\\GUI\\mars.jpg";
    private JLabel backgroundImg;
    private Panel mazePanel;

    public Frame()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(null);
        mazePanel = new Panel(1200,600);
        mazePanel.setBounds(0,0,1200,600);
        add(mazePanel);
        buildBackgroundImg();
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    private void buildBackgroundImg()
    {
        Icon img = new ImageIcon(BACKGROUND_IMG_PATH);
        int imgHeight = img.getIconHeight();
        int imgWidth = img.getIconWidth();
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, -50, imgWidth, imgHeight);
        add(backgroundImg);
    }
}
