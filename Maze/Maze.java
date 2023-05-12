package Maze;

import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;
import WeightDiGraph.Edge;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

public class Maze 
{
    private Graph graph;
    private int rows;
    private int columns;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private Vertex[][] vertices;

    //TODO: better encapsulate:
    private HashSet<Vertex> vis;
    private HashSet<Vertex> path;

    public Maze(int mazeWidth, int mazeHeight, int mazeColumns, int mazeRows)
    {
        width = mazeWidth;
        height = mazeHeight;
        columns = mazeColumns;
        rows = mazeRows;
        cellWidth = width / columns;
        cellHeight = height / rows;
        graph = new Graph();
        
        update();
        makeMaze();
    }
    
    public Graph getGraph() 
    {
        return graph;
    }

    public int getCellHeight()
    {
        return cellHeight;
    }

    public int getCellWidth()
    {
        return cellWidth;
    }

    private void initialize()
    {
        vertices = new Vertex[rows][columns];
        for(int x = 0; x < columns; x++)
        {
            for(int y = 0; y < rows; y++) 
            {                            
                int id = y * columns + x;
                Vertex v = new Vertex(id, x*cellWidth, y*cellHeight);
                graph.addVertex(v);
                vertices[y][x] = v;
            }
        }
    }

    private void makeGridConnection()
    {
        Random random = new Random();
        int min = 1;
        int max = 4;
         for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                
                if(y > 0)
                {
                graph.createEdge(vertices[x][y], vertices[x][y -1], random.nextInt(max - min + 1) + min);
                vertices[x][y].setLeft(vertices[x][y -1]);

                }
                if(x > 0){
                graph.createEdge(vertices[x][y], vertices[x-1][y], random.nextInt(max - min + 1) + min);
                vertices[x][y].setUp(vertices[x-1][y]);

                }
                if(y < columns -1){
                graph.createEdge(vertices[x][y], vertices[x][y+1], random.nextInt(max - min + 1) + min);
                vertices[x][y].setRight(vertices[x][y+1]);

                }
                if(x < rows -1){
                graph.createEdge(vertices[x][y], vertices[x+1][y], random.nextInt(max - min + 1) + min);
                vertices[x][y].setDown(vertices[x+1][y]);

                }
            }
        }
    }
    
    

    private void update()
    {
        initialize();
        makeGridConnection();
    }

    private void connectCells(Vertex a, Vertex b, LinkedList<Vertex> list)
    {
        if(a.getDown() != null && a.getDown().getId() == b.getId()){
            a.setSouthWall(false);
            b.setNorthWall(false);
        }
        else if(a.getUp()!= null &&a.getUp().getId() == b.getId()){
            b.setSouthWall(false);
            a.setNorthWall(false);
        }
        else if(a.getRight()!= null&&a.getRight().getId() == b.getId()){
            a.setEastWall(false);
            b.setWestWall(false);

        }
        else if(a.getLeft()!= null&&a.getLeft().getId() == b.getId()){
            b.setEastWall(false);
            a.setWestWall(false);
        }
        // MAJOR CLEAN UP REQUIRED:
         else{
            int rightIndex = list.indexOf(a.getRight());
            int leftIndex = list.indexOf(a.getLeft());
            int downIndex = list.indexOf(a.getDown());
            int upIndex = list.indexOf(a.getUp());
            int max = Math.max(Math.max(rightIndex, leftIndex), Math.max(downIndex, upIndex));
            if(max == -1) return;
            else if(max == rightIndex){
                a.removeRightWall();
                if(a.getRight() != null){
                    a.getRight().removeLeftWall();
                }
            }
            else if(max == leftIndex){
                a.removeLeftWall();
                if(a.getLeft() != null){
                    a.getLeft().removeRightWall();
                }
            }
            else if(max == downIndex){
                a.remvoeDownWall();
                if(a.getDown() != null){
                    a.getDown().removeUpWall();
                }
            }
            else{
                a.removeUpWall();
                if(a.getUp() != null){
                    a.getUp().remvoeDownWall();
                }
            }
        }
    }

    public void makeMaze(){ 
        Stack<Vertex> stack = new Stack<>();
        LinkedList<Vertex> visited = new LinkedList<>();

        Vertex startVertex = graph.getRandomVertex();
        stack.push(startVertex);
        Vertex current = startVertex;
        while(!stack.isEmpty()){
            Vertex temp = current;
            current = stack.pop();
            if(!visited.contains(current)){

                connectCells(current, temp, visited);
                visited.addLast(current);
                TreeMap<Integer, Vertex> tree = new TreeMap<Integer, Vertex>();
                for(Vertex v : graph.getAdjacent(current) ){
                    if(!visited.contains(v)){
                        graph.getEdges(current).forEach((Edge e)->{
                            if(e.getDest().getId() == v.getId()){
                                tree.put(e.getWeight(), v);
                            }
                        });
                    }
                }
                tree.forEach((Integer i, Vertex v)->stack.push(v));
            }
        }
        removeInvalidEdges();

    }

    private void removeInvalidEdges()
    {
        for(Vertex v : graph.getVertices()){
            if(v.hasDownWall() && v.getDown() != null){
                graph.removeEdge(v, v.getDown());
                v.setDown(null);
            }
            if(v.hasUpWall() && v.getUp() != null){
                graph.removeEdge(v, v.getUp());
                v.setUp(null);
            }
            if(v.hasLeftWall() && v.getLeft() != null){
                graph.removeEdge(v, v.getLeft());
                v.setLeft(null);
            }
            if(v.hasRightWall() && v.getRight() != null){
                graph.removeEdge(v,v.getRight());
                v.setRight(null);
            }
        }
    }

    public HashSet<Vertex> findPath(Vertex start)
    {
        if(path != null){
            path = null;
            return path;
        }
        vis = new HashSet<Vertex>();
        path = new HashSet<>();
        path.add(start);
        vis.add(start);
        DFS(start,graph.getLast());
        return path;
    }
    
    Vertex DFS(Vertex current, Vertex target)
    {
        if(target.getId() == current.getId())
        return current;

        for(Vertex v : graph.getAdjacent(current)){
            if(vis.contains(target))
            break;
            if(!vis.contains(v))
            {
                vis.add(v);
                Vertex temp = DFS(v, target);
                if(temp != null)
                path.add(temp);
            }
        }
        if(vis.contains(target))
        return current;
        else
        return null;
    }
}