package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import Maze.Maze;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;

public class MazePanel extends JPanel
{
    private String BACKGROUND_IMG_PATH = "GUI/Backgrounds/background.jpg";
    private final int MAZE_WALL_WIDTH = 7; 
    private final float PATH_LINE_WIDTH = (float) 0.25;
    private final Color WALL_COLOR = Color.LIGHT_GRAY;
    private final Color PATH_COLOR = Color.GREEN;

    // ACTION COMMANDS
    private final MoveUP UP_COMMAND = new MoveUP();
    private final MoveDown DOWN_COMMAND = new MoveDown();
    private final MoveLeft LEFT_COMMAND = new MoveLeft();
    private final MoveRight RIGHT_COMMAND = new MoveRight();

    private Image background;
    private Maze maze;
    private Graph graph;
    private int cellWidth;
    private int cellHeight;
    private Stack<Vertex> path;
    private Vertex currentPos;


    public MazePanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
        buildBackground();
        buildKeyBinding();
    }

    private void buildKeyBinding()
    {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveUp");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),"moveDown");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"moveDown");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"moveLeft");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"moveLeft");
        
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),"moveRight");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"moveRight");
        
        getActionMap().put("moveUp", UP_COMMAND);
        getActionMap().put("moveDown", DOWN_COMMAND);
        getActionMap().put("moveLeft", LEFT_COMMAND);
        getActionMap().put("moveRight", RIGHT_COMMAND);
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

    private void drawCurrentPos(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRect(currentPos.getX(), currentPos.getY(), cellWidth, cellHeight);
    }

    private void drawEnd(Graphics g)
    {
        Vertex last = graph.getLast();
        g.setColor(Color.GREEN);
        g.fillRect(last.getX(), last.getY(), cellWidth, cellHeight);
    }

    private void drawMaze(Graphics2D g2d)
    {
        g2d.setColor(WALL_COLOR);
        g2d.setStroke(new BasicStroke(MAZE_WALL_WIDTH));
        graph.getVertices().forEach(vertex->drawWalls(g2d, vertex));
    }

    private void drawWalls(Graphics2D g2d, Vertex v)
    {
        int x = v.getX();
        int y = v.getY();
        if(v.hasDownWall())
            g2d.drawLine(x, y + cellHeight, x + cellWidth, y + cellHeight);
         
        if(v.hasUpWall())
            g2d.drawLine(x, y, x + cellWidth, y);
        
        if(v.hasRightWall())
            g2d.drawLine(x + cellWidth, y, x + cellWidth, y + cellHeight);
        
        if(v.hasLeftWall())
            g2d.drawLine(x, y, x, y + cellHeight);
        
    }

    public void renderNewMaze(int rows, int columns)
    {
        maze = new Maze(getWidth(), getHeight(), rows, columns);
        graph = maze.getGraph();
        cellWidth = maze.getCellWidth();
        cellHeight = maze.getCellHeight();
        currentPos = graph.getVertex(0);
        path = null;
        repaint();
    }

    private void drawPath(Graphics2D g)
    {
        g.setColor(PATH_COLOR);
        g.setStroke(new BasicStroke(PATH_LINE_WIDTH));
        // Draw lines connection all adjacent vertices leading to end.
        path.forEach(vertex->drawValidConnections(vertex, g));
    }

    private void drawValidConnections(Vertex vertex, Graphics2D g)
    {
        // If an adjacent is also in the path draw lines connection them.
        graph.getAdjacent(vertex).forEach(adjacentVertex->{
            if(path.contains(adjacentVertex))
            drawConnection(vertex, adjacentVertex, g);
        });
    }

    private void drawConnection(Vertex start, Vertex end, Graphics2D g)
    {
        // Changes cords to be center of cells rather than top left.
        int yOffset = cellHeight / 2;
        int xOffset = cellWidth / 2;
        
        int startX = start.getX() + xOffset;
        int startY = start.getY() + yOffset;
        
        int endX = end.getX() + xOffset;
        int endY = end.getY() + yOffset;
        
        g.drawLine(startX, startY, endX, endY);
    }

    public void updatePath()
    {
      if(graph == null)
            return; // (No graph) -> (No Maze to navigate).
      else if(path == null)
            path = maze.pathFinderDFS(currentPos);
      else if(path != null)
            path = null; // clear path, (user clicked with already displayed path)
      
      repaint();
    }

    public void clear()
    {
        maze = null;
        graph = null;
        path = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // Drawing background image:
        if(background != null)
            g.drawImage(background, 0, 0, null);
        
        // Drawing of the maze:
        if(graph != null)
        {
            drawMaze((Graphics2D)g); // Maze
            drawCurrentPos(g); // Start Vertex 
            drawEnd(g);   // End Vertex
        }

        // Drawing path from start to end:
        if(path != null)
            drawPath((Graphics2D)g);
    }

    // Keyboard control logic:
    
    private class MoveUP extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasUpWall())
                return;
            currentPos = currentPos.getUp();
            if(path != null)
                path = maze.pathFinderDFS(currentPos);
            repaint();
        }
    }

    private class MoveDown extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasDownWall())
               return;
            currentPos = currentPos.getDown();
            if(path != null)
                path = maze.pathFinderDFS(currentPos);
            repaint();
        }
    }

    private class MoveRight extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasRightWall())
               return;
            currentPos = currentPos.getRight();
            if(path != null)
                path = maze.pathFinderDFS(currentPos);
            repaint();
        }
    }
    private class MoveLeft extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasLeftWall())
               return;
            currentPos = currentPos.getLeft();
            if(path != null)
                path = maze.pathFinderDFS(currentPos);
            repaint();
        }
    }
}