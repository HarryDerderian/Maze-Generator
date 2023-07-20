package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{   private String BACKGROUND_IMG_PATH = "/GUI/Backgrounds/button_background.jpg";
    private final Color BUTTON_COLOR = Color.ORANGE;

    private Image background;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton small;
    private JButton normal;
    private JButton large;
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
            background = ImageIO.read(getClass().getResource(BACKGROUND_IMG_PATH));
        }
        catch(Exception e)
        {
            System.out.println("(Button Panel) Failed to load images: fallback background set to black.");
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

        small = new JButton("SMALL");
        small.setFocusPainted(false);
        small.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        small.setBackground(Color.BLACK);
        small.setForeground(BUTTON_COLOR);
        small.addActionListener(e->maze.renderNewMaze(10,10));

        normal = new JButton("NORMAL");
        normal.setFocusPainted(false);
        normal.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        normal.setBackground(Color.BLACK);
        normal.setForeground(BUTTON_COLOR);
        normal.addActionListener(e->maze.renderNewMaze(25,25));

        large = new JButton("LARGE");
        large.setFocusPainted(false);
        large.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        large.setBackground(Color.BLACK);
        large.setForeground(BUTTON_COLOR);
        large.addActionListener(e->maze.renderNewMaze(50,50));

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
        add(small, constratins);

        constratins.gridy = 3; // row 4
        add(normal, constratins);

        constratins.gridy = 4; // row 5
        add(large, constratins);

    }
}
