package GUI;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class Frame extends JFrame
{
    private int FRAME_HEIGHT = 938;
    private int FRAME_WIDTH = 1515;
    private String BACKGROUND_IMG_PATH = "GUI\\background.jpg";
    private JLabel backgroundImg;
    private Panel mazePanel;

    public Frame()
    {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(null);
        mazePanel = new Panel(1500,900);
        mazePanel.setBounds(0,0,1500,900);
        add(mazePanel);
        buildBackgroundImg();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void buildBackgroundImg()
    {
        Icon img = new ImageIcon(BACKGROUND_IMG_PATH);
        backgroundImg = new JLabel(img);
        backgroundImg.setBounds(0, 0, 1500, 900);
        add(backgroundImg);
    }
}
