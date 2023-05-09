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
    private JPanel container;
    
    private Panel mazePanel;

    public Frame()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout()); 
        container = new JPanel();
        //container.setBounds(0, 0, WIDTH, HEIGHT);
        container.setLayout(null);
        container.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        add(container, BorderLayout.CENTER);
        mazePanel = new Panel(1000,850);
        //container.add(mazePanel);
       // add(mazePanel); 
       buildBackgroundImg(); 
       
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildBackgroundImg()
    {
        Icon img = new ImageIcon(BACKGROUND_IMG_PATH);
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, 0, container.getWidth(), container.getHeight());
        container.add(backgroundImg); 
    }

}
