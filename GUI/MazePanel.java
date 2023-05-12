package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Maze.Maze;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;

public class MazePanel extends JPanel
{
    private String BACKGROUND_IMG_PATH = "GUI/Backgrounds/background.jpg";
    private final int MAZE_WALL_WIDTH = 4; 
    private final float PATH_LINE_WIDTH = (float) 0.25;
    private final Color WALL_COLOR = Color.CYAN;
    private final Color PATH_COLOR = Color.GREEN;

    private Image background;
    private Maze maze;
    private Graph graph;
    private int cellWidth;
    private int cellHeight;
    private HashSet<Vertex> path;

    public MazePanel(int width, int height)
    {
        setPreferredSize(new Dimension(width, height));
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

    private void drawStart(Graphics g)
    {
        Vertex start = graph.getVertex(0);
        g.setColor(Color.GREEN);
        g.fillRect(start.getX(), start.getY(), cellWidth, cellHeight);
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

    public void updateMaze(int rows, int columns)
    {
        maze = new Maze(getWidth(), getHeight(), rows, columns);
        graph = maze.getGraph();
        cellWidth = maze.getCellWidth();
        cellHeight = maze.getCellHeight();
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
       path = maze.findPath();
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
            drawStart(g); // Start Vertex 
            drawEnd(g);   // End Vertex
            drawMaze((Graphics2D)g); // Maze
        }

        // Drawing path from start to end:
        if(path!= null)
            drawPath((Graphics2D)g);
        
    }
}