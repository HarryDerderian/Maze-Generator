package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{   private String BACKGROUND_IMG_PATH = "GUI/button_background.jpg";
    private final Color BUTTON_COLOR = new Color(88, 222, 224);

    private Image background;
    private JButton bfsButton;
    private JButton dfsButton;
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JButton impossible;
    
    public ButtonPanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridBagLayout());
        buildButtons();
        addButtons();
        try{
            background = ImageIO.read(new File(BACKGROUND_IMG_PATH));
        }
        catch(Exception e){
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

        dfsButton = new JButton("Depth first search (DFS)");
        dfsButton.setFocusPainted(false);
        dfsButton.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        dfsButton.setBackground(Color.BLACK);
        dfsButton.setForeground(BUTTON_COLOR);

        easy = new JButton("EASY");
        easy.setFocusPainted(false);
        easy.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        easy.setBackground(Color.BLACK);
        easy.setForeground(BUTTON_COLOR);

        normal = new JButton("NORMAL");
        normal.setFocusPainted(false);
        normal.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        normal.setBackground(Color.BLACK);
        normal.setForeground(BUTTON_COLOR);

        hard = new JButton("HARD");
        hard.setFocusPainted(false);
        hard.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        hard.setBackground(Color.BLACK);
        hard.setForeground(BUTTON_COLOR);

        impossible = new JButton("IMPOSSIBLE");
        impossible.setFocusPainted(false);
        impossible.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        impossible.setBackground(Color.BLACK);
        impossible.setForeground(BUTTON_COLOR);

    }

    private void addButtons()
    {
        GridBagConstraints constratins = new GridBagConstraints();        
        constratins.fill = GridBagConstraints.HORIZONTAL;
        constratins.weightx = 1;
        constratins.insets = new Insets(40, 20,40, 20);
        constratins.ipady = 25;
        constratins.gridx = 0;
        
        constratins.gridy = 0;
        add(dfsButton, constratins);
        
        constratins.gridy = 1;
        add(bfsButton, constratins);

        constratins.gridy = 2;
        add(easy, constratins);

        constratins.gridy = 3;
        add(normal, constratins);

        constratins.gridy = 4;
        add(hard, constratins);

        constratins.gridy = 5;
        add(impossible, constratins);
    }
}
