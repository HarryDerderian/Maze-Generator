package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;
import GridGraph.GridGraph;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;

public class Panel extends JPanel
{
    private final Color TRANSPARENT = Color.BLACK;//new Color(0, 0, 0, 0);
    private final int WALL_WIDTH = 4; // width of maze walls
    private final Color WALL_COLOR = Color.RED;//new Color(0, 169, 181);

    private GridGraph grid;
    private Graph graph;
    private int cellWidth;
    private int cellHeight;
    private int width;
    private int height;
    private boolean resized;
    
    
    public Panel(int width, int height){
        // Set panel width and height, make transparent.
        setBackground(TRANSPARENT);
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

    public void buildMaze(){
        System.out.println("Width: "+getWidth() +" Height: "+getHeight());
        grid = new GridGraph(getWidth(), getHeight());
        graph = grid.getGraph();
        cellWidth = grid.getCellWidth();
        cellHeight = grid.getCellHeight();
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if(graph != null){
        drawStart(g);
        drawEnd(g);        
        drawMaze((Graphics2D)g);
        }
    }

}