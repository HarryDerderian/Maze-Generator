package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel 
{
    private String BACKGROUND_IMG_PATH = "GUI/Backgrounds/button_background.jpg";
    private final String SCORE_LABEL_TEXT = "Mazes beat: ";

    private Image background;
    private JLabel scoreLabel;
    private int currentScore;

    public ScorePanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        buildBackground();
        currentScore = 0;
        buildLabel();
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


    public void updateScore()
    {
        currentScore++;
        scoreLabel.setText(SCORE_LABEL_TEXT + currentScore);
    }

    private void buildLabel()
    {
        scoreLabel = new JLabel(SCORE_LABEL_TEXT + currentScore);
        scoreLabel.setSize(100, 25);
        scoreLabel.setForeground(Color.ORANGE);
        scoreLabel.setBackground(Color.BLACK);
        add(scoreLabel, BorderLayout.SOUTH);
        scoreLabel.setFont(new Font("Roboto",Font.BOLD, 35));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, null);
    }
    
}
