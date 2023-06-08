package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
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
    
    private final float PATH_LINE_WIDTH = (float) 4;
    private final Color WALL_COLOR = new Color(79, 39, 28);
    private final Color PATH_COLOR = new Color(16, 249, 144);
    private final Color DEADEND_COLOR = new Color(227, 227, 227);
    // ACTION COMMANDS
    private final MoveUP UP_COMMAND = new MoveUP();
    private final MoveDown DOWN_COMMAND = new MoveDown();
    private final MoveLeft LEFT_COMMAND = new MoveLeft();
    private final MoveRight RIGHT_COMMAND = new MoveRight();
    
    private int mazeWallWidth;
    private Image background;
    private Maze maze;
    private Graph graph;
    private int cellWidth;
    private int cellHeight;
    private Collection<Vertex> path;
    private Collection<Vertex> pathBFS;
    private Collection<Vertex> pathDFS;
    private Vertex currentPos;

    public MazePanel(int width, int height)
    {
        mazeWallWidth = 10;
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
        g.setColor(Color.BLUE);
        g.fillRect(currentPos.getX(), currentPos.getY(), cellWidth, cellHeight);
    }

    private void drawEnd(Graphics g)
    {
        Vertex last = maze.getTarget();
        g.setColor(Color.GREEN);
        g.fillRect(last.getX(), last.getY(), cellWidth, cellHeight);
    }

    private void drawMaze(Graphics2D g2d)
    {
        g2d.setColor(WALL_COLOR);
        g2d.setStroke(new BasicStroke(mazeWallWidth));
        graph.getAllVertices().forEach(vertex->drawWalls(g2d, vertex));
    }

    private void drawWalls(Graphics2D g2d, Vertex v)
    {
        int x = v.getX();
        int y = v.getY();
        if(v.hasSouthWall())
            g2d.drawLine(x, y + cellHeight, x + cellWidth, y + cellHeight);
        if(v.hasNorthWall())
            g2d.drawLine(x, y, x + cellWidth, y);
        if(v.hasEastWall())
            g2d.drawLine(x + cellWidth, y, x + cellWidth, y + cellHeight);
        if(v.hasWestWall())
            g2d.drawLine(x, y, x, y + cellHeight);
    }

    public void renderNewMaze(int rows, int columns)
    {
        if(rows > 25) 
            mazeWallWidth = 6;
        else if (rows > 10)
            mazeWallWidth = 10;
        else 
            mazeWallWidth = 15;
        
        maze = new Maze(getWidth(), getHeight(), rows, columns);
        graph = maze.getGraph();
        cellWidth = maze.getCellWidth();
        cellHeight = maze.getCellHeight();
        currentPos = graph.getVertex(0);
        path = null;
        pathBFS = null;
        pathDFS = null;
        repaint();
    }

    private void drawBFS(Graphics2D g)
    {
        g.setColor(DEADEND_COLOR);
        g.setStroke(new BasicStroke(PATH_LINE_WIDTH));
        pathBFS.forEach(vertex->drawValidConnections(vertex, g, pathBFS));
    }

    private void drawDFS(Graphics2D g)
    {
        g.setColor(DEADEND_COLOR);
        g.setStroke(new BasicStroke(PATH_LINE_WIDTH));
        pathDFS.forEach(vertex->drawValidConnections(vertex, g, pathDFS));
    }

    private void drawPath(Graphics2D g)
    {
        g.setColor(PATH_COLOR);
        g.setStroke(new BasicStroke(PATH_LINE_WIDTH));
        // Draw lines connection all adjacent vertices leading to end.
        path.forEach(vertex->drawValidConnections(vertex, g, path));
    }

    private void drawValidConnections(Vertex vertex, Graphics2D g, Collection<Vertex> list)
    {
        // If an adjacent is also in the path draw lines connection them.
        vertex.getAdjacent().forEach(adj->{
            if(list.contains(adj))
            drawConnection(vertex, adj, g);
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

    public void updateBFS()
    {
        if(graph == null)
            return; // (No graph) -> (No Maze to navigate).
        else if(pathBFS == null)
        {
            pathBFS = maze.bfsTraversal(currentPos);
            pathDFS = null;
            updatePath();
        }
        else if(pathBFS != null)
        {
            pathBFS = null; // clear path, (user clicked with already displayed path)
            path = null;
        }
        repaint();
    }

    public void updateDFS()
    {
        if(graph == null)
        return; // (No graph) -> (No Maze to navigate).
        
        else if(pathDFS == null)
        {
            pathDFS = maze.dfsTraversal(currentPos);
            pathBFS = null;
            updatePath();
        }
        else if(pathDFS != null)
        {
            pathDFS = null; // clear path, (user clicked with already displayed path)
            path = null;
        }
        repaint();
    }

    public void updatePath()
    {
        path = maze.getPath(currentPos);
    }

    public void clear()
    {
        maze = null;
        graph = null;
        path = null;
        pathDFS = null;
        pathBFS = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        // Drawing background image:
        if(background != null)
            g.drawImage(background, 0, 0, null);
        // Drawing DFS traversal
        if(pathDFS != null)
        {
            drawDFS((Graphics2D)g);
        }
        // Drawing BFS traversal
        if(pathBFS != null)
        {
            drawBFS((Graphics2D)g);
        }
        // Drawing path from start to end:
        if(path != null)
            drawPath((Graphics2D)g);
        // Drawing of the maze:
        if(graph != null)
        {
            drawMaze((Graphics2D)g); // Maze
            drawCurrentPos(g); // Start Vertex 
            drawEnd(g);   // End Vertex
        }
    }

    private void updatePaths()
    {
        if(path != null)
            path = maze.getPath(currentPos);
        if(pathBFS != null)
            pathBFS = maze.bfsTraversal(currentPos);
        if(pathDFS != null)
            pathDFS = maze.dfsTraversal(currentPos);
    }

    private boolean checkWin()
    {
        if(currentPos.equals(maze.getTarget()))
        {
            renderNewMaze(maze.getNumRows(), maze.getNumColumns());
            return true;
        }
        return false;
    }
    // Keyboard control logic: 
    private class MoveUP extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasNorthWall())
                return;
            currentPos = currentPos.getNorthVertex();
            checkWin();
            updatePaths();
            repaint();
        }
    }

    private class MoveDown extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasSouthWall())
               return;
            currentPos = currentPos.getSouthVertex();
            checkWin();
            updatePaths();
            repaint();
        }
    }

    private class MoveRight extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasEastWall())
               return;
            currentPos = currentPos.getEastVertex();
            checkWin();
            updatePaths();
            repaint();
        }
    }
    private class MoveLeft extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(graph == null || currentPos.hasWestWall())
               return;
            currentPos = currentPos.getWestVertex();
            checkWin();
            updatePaths();
            repaint();
        }
    }
}