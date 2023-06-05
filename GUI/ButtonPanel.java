package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{   private String BACKGROUND_IMG_PATH = "GUI/Backgrounds/button_background.jpg";
    private final Color BUTTON_COLOR = new Color(88, 222, 224);

    private Image background;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JButton start;
    private MazePanel maze;
    
    public ButtonPanel(int width, int height, MazePanel maze)
    {
        this.maze = maze;
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        buildButtons();
        addButtons();
        buildBackground();
    }
    
    private void buildBackground()
    {
        try
        {
            background = ImageIO.read(new File(BACKGROUND_IMG_PATH));
        }
        catch(Exception e)
        {
            setBackground(Color.BLACK);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, null);
    }

    private void buildButtons()
    {
        bfsButton = new JButton("Breadth first search (BFS)");
        bfsButton.setFocusPainted(false);
        bfsButton.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        bfsButton.setBackground(Color.BLACK);
        bfsButton.setForeground(BUTTON_COLOR);
        bfsButton.addActionListener(e->{maze.updateBFS();});

        dfsButton = new JButton("Depth first search (DFS)");
        dfsButton.setFocusPainted(false);
        dfsButton.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        dfsButton.setBackground(Color.BLACK);
        dfsButton.setForeground(BUTTON_COLOR);
        dfsButton.addActionListener(e->maze.updateDFS());

        easy = new JButton("EASY");
        easy.setFocusPainted(false);
        easy.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        easy.setBackground(Color.BLACK);
        easy.setForeground(BUTTON_COLOR);
        easy.addActionListener(e->maze.renderNewMaze(10,10));

        normal = new JButton("NORMAL");
        normal.setFocusPainted(false);
        normal.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        normal.setBackground(Color.BLACK);
        normal.setForeground(BUTTON_COLOR);
        normal.addActionListener(e->maze.renderNewMaze(25,25));

        hard = new JButton("HARD");
        hard.setFocusPainted(false);
        hard.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        hard.setBackground(Color.BLACK);
        hard.setForeground(BUTTON_COLOR);
        hard.addActionListener(e->maze.renderNewMaze(50,50));

        start = new JButton("START");
        start.setFocusPainted(false);
        start.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        start.setBackground(Color.BLACK);
        start.setForeground(BUTTON_COLOR);
    }

    private void addButtons()
    {
        GridBagConstraints constratins = new GridBagConstraints();        
        
        // Padding/sizing buttons:
        constratins.fill = GridBagConstraints.HORIZONTAL;
        constratins.weightx = 1;
        constratins.insets = new Insets(40, 20,40, 20);
        constratins.ipady = 25;

        // Nx1 matrix I.E. Row Vector
        constratins.gridx = 0; // column 1 (only column) 
        
        constratins.gridy = 0; // row 1
        add(dfsButton, constratins);
        
        constratins.gridy = 1; // row 2
        add(bfsButton, constratins);

        constratins.gridy = 2; // row 3
        add(easy, constratins);

        constratins.gridy = 3; // row 4
        add(normal, constratins);

        constratins.gridy = 4; // row 5
        add(hard, constratins);

        constratins.gridy = 5; // row 6
        add(start, constratins);
    }
}
