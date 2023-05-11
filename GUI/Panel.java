package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import GridGraph.GridGraph;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;

public class Panel extends JPanel
{
    private String BACKGROUND_IMG_PATH = "GUI/background.jpg";
    private final int WALL_WIDTH = 4; // width of maze walls
    private final Color WALL_COLOR = new Color(0, 255, 234);

    private Image background;
    private GridGraph grid;
    private Graph graph;
    private int cellWidth;
    private int cellHeight;
    private LinkedList<Vertex> path;

    public Panel(int width, int height){
        try
        {
            background = ImageIO.read(new File(BACKGROUND_IMG_PATH));
        }
        catch(Exception e)
        {
            setBackground(Color.BLACK);
        }
        setPreferredSize(new Dimension(width, height));
    }

    private void drawStart(Graphics g){
        Vertex start = graph.getVertex(0);
        g.setColor(Color.GREEN);
        g.fillRect(start.getX() + start.getX()/2, start.getY()+start.getY()/2 , cellWidth/2, cellHeight/2);
    }

    private void drawEnd(Graphics g){
        Vertex last = graph.getLast();
        g.setColor(Color.GREEN);
        g.fillRect(last.getX() +cellWidth/2, last.getY() + cellHeight/4, 
        cellWidth/2, cellHeight/2);
    }

    private void drawMaze(Graphics2D g2d)
    {
        g2d.setColor(WALL_COLOR);
        g2d.setStroke(new BasicStroke(WALL_WIDTH));
        graph.getVertices().forEach(vertex->drawWalls(g2d, vertex));
    }

    private void drawWalls(Graphics2D g2d, Vertex v)
    {
        int x = v.getX();
        int y = v.getY();
        if(v.hasDownWall()){
            g2d.drawLine(x, y + cellHeight, x + cellWidth, y + cellHeight);
        } 
        if(v.hasUpWall()){
            g2d.drawLine(x, y, x + cellWidth, y);
        }
        if(v.hasRightWall()){
            g2d.drawLine(x + cellWidth, y, x + cellWidth, y + cellHeight);
        }
        if(v.hasLeftWall()){
            g2d.drawLine(x, y, x, y + cellHeight);
        }
    }

    public void buildMaze(int rows, int columns)
    {
        grid = new GridGraph(getWidth(), getHeight(), rows, columns);
        graph = grid.getGraph();
        cellWidth = grid.getCellWidth();
        cellHeight = grid.getCellHeight();
        repaint();
    }

    private void drawPath(Graphics2D g){
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(10));
        for(Vertex v : path){
            for(Vertex j : graph.getAdjacent(v)){
                if(path.contains(j)){
                    drawConnection(v,j,g);
                }
            }
        }
        
    }

    private void drawConnection(Vertex start, Vertex end, Graphics2D g){
        if(start.getUp()!= null&&start.getUp().getId() == end.getId()){
            g.drawLine(start.getX() + cellWidth/2, start.getY() + cellHeight/2, start.getX() + cellWidth/2, end.getY() +cellHeight/2);
        }
        else if(start.getDown()!=null&&start.getDown().getId() == end.getId()){
            g.drawLine(start.getX() + cellWidth/2, start.getY() + cellHeight/2, start.getX() + cellWidth/2, end.getY() +cellHeight/2);
        }
        else if(start.getRight()!= null && start.getRight().getId() == end.getId()){
            g.drawLine(start.getX() + cellWidth/2, start.getY() + cellHeight/2, end.getX() + cellWidth/2, start.getY() +cellHeight/2);
        }
        else if(start.getLeft()!= null &&start.getLeft().getId() == end.getId()){
            g.drawLine(start.getX() + cellWidth/2, start.getY() + cellHeight/2, end.getX() + cellWidth/2, start.getY() +cellHeight/2);
        }
    }

    public void DFS(){
       path = grid.findPath();
       repaint();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(background != null)
            g.drawImage(background, 0, 0, null);
        if(graph != null)
        {
            drawStart(g);
            drawEnd(g);        
            drawMaze((Graphics2D)g);
        }
        if(path!= null){
            drawPath((Graphics2D)g);
        }
    }
}