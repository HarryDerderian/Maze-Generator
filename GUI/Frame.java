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
    private int FRAME_HEIGHT = 900;
    private int FRAME_WIDTH = 1200;
    private String BACKGROUND_IMG_PATH = "GUI/background.jpg";
    private JLabel backgroundImg;
    private Panel mazePanel;

    public Frame()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(null); 
        mazePanel = new Panel();
        add(mazePanel);
        mazePanel.setBounds(0, 0, getWidth(), getHeight());
        add(mazePanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildBackgroundImg()
    {

    }

}
