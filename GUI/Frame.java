package GUI;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

public class Frame extends JFrame implements ComponentListener
{
    private final String TITLE = "MAZE GENERATOR";
    private final int MAZE_PANEL_HEIGHT = 900;
    private final int MAZE_PANEL_WIDTH = 1200;
    private final int SIDE_PANEL_WIDTH = 300;
    private MazePanel mazePanel;
    private ButtonPanel buttonPanel;
    private ScorePanel scorePanel;

    public Frame()
    {
        setTitle(TITLE);
        scorePanel = new ScorePanel(SIDE_PANEL_WIDTH, MAZE_PANEL_HEIGHT);
        add(scorePanel,BorderLayout.WEST);
        pack();
        mazePanel = new MazePanel(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT, scorePanel);
        add(mazePanel, BorderLayout.CENTER);
        buttonPanel = new ButtonPanel(SIDE_PANEL_WIDTH, MAZE_PANEL_HEIGHT, mazePanel);
        add(buttonPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    @Override
    public void componentResized(ComponentEvent e) 
    {
        mazePanel.clear();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}

}
