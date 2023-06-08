package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
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

    public Frame()
    {
        setTitle(TITLE);
        pack();
        mazePanel = new MazePanel(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT);
        add(mazePanel, BorderLayout.CENTER);
        buttonPanel = new ButtonPanel(SIDE_PANEL_WIDTH, MAZE_PANEL_HEIGHT, mazePanel);
        add(buttonPanel, BorderLayout.EAST);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setMinimumSize(new Dimension(MAZE_PANEL_WIDTH + SIDE_PANEL_WIDTH, MAZE_PANEL_HEIGHT));
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
