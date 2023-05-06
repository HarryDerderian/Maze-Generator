package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.swing.JPanel;
import Grid.Grid;
import GridGraph.GridGraph;
import WeightDiGraph.Edge;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;

public class Panel extends JPanel
{
    
    private GridGraph grid;
    private Graph graph;
    private int width;
    private int height;
    
    private Panel(){}
    public Panel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        grid = new GridGraph(width, height);
        graph = grid.getGraph();
        //setBackground(Color.BLACK);
        setBackground(new Color(0, 0, 0, 0));
      //  DFS(graph, graph.getVertices().iterator().next());
    }


    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Vertex start = graph.getVertex(0);
        g.setColor(Color.RED);
        g.fillRect(start.getX(), start.getY(), grid.getCellWidth(), grid.getCellHeight());
        Vertex last = graph.getLast();
        g.fillRect(last.getX(), last.getY(), grid.getCellWidth(), grid.getCellHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(3));
        for(Vertex v : graph.getVertices()){
            
            if(v.hasDownWall()){
                g2d.drawLine(v.getX(), v.getY() +grid.getCellHeight(), v.getX() + grid.getCellWidth(), v.getY()+grid.getCellHeight());
            }
            if(v.hasUpWall()){
                g2d.drawLine(v.getX(), v.getY(), v.getX() + grid.getCellWidth(), v.getY());
            }
            if(v.hasRightWall()){
                g2d.drawLine(v.getX()+grid.getCellWidth(),v.getY(),v.getX()+grid.getCellWidth(), v.getY() + grid.getCellHeight());
            }
            if(v.hasLeftWall()){
                g2d.drawLine(v.getX(),v.getY(),v.getX(), v.getY() + grid.getCellHeight());
            }

          
           // g.drawRect(v.getX(), v.getY(), grid.getCellWidth(), grid.getCellHeight());
            
         //   g.drawString(Integer.toString(v.getId()), v.getX()+grid.getCellWidth()/2, v.getY()+grid.getCellHeight()/2);
        }
        

        
    }

    public static void DFS(Graph graph, Vertex start)
    {
        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> visited = new HashSet<>();
        stack.push(start);
        visited.add(start);
        Vertex current;
         
        while(!stack.isEmpty())
        {
            current = stack.peek();
            while(!graph.getEdges(current).isEmpty() &&
                  !visited.containsAll(graph.getAdjacent(current)) ){
            int weight = Integer.MAX_VALUE;
            HashSet<Edge> edges = graph.getEdges(current);
            for(Edge v : edges)
            {
                if(Integer.valueOf(v.getWeight()) < weight && !visited.contains(v.getDest()) ){
                    weight = v.getWeight();
                    current = v.getDest();
                }
            }
            visited.add(current);
            stack.push(current);
           }
           System.out.println(stack.pop().getId());
        }
    }
}
