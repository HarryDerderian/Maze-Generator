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
    private int numRows;
    private int numColumns;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;


    //TODO: better encapsulate:
    private HashSet<Vertex> vis;
    private HashSet<Vertex> path;

    public Maze(int mazeWidth, int mazeHeight, int mazeColumns, int mazeRows)
    {
        width = mazeWidth;
        height = mazeHeight;
        numColumns = mazeColumns;
        numRows = mazeRows;
        cellWidth = width / numColumns;
        cellHeight = height / numRows;
        graph = new Graph();
        
        initializeGrid();
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

    private void initializeGrid()
    {
        for(int column = 0; column < numColumns; column++)
        {
            for(int row = 0; row < numRows; row++) 
            {                            
                int id = row * numColumns + column;
                Vertex v = new Vertex(id, column * cellWidth, row * cellHeight, row, column);
                graph.addVertex(v);
            }
        }
        // Connect each vertex to it's right, left, upper, lower.
        graph.getVertices().forEach(vertex->connectAdjacentCells(vertex));
    }

    private void connectAdjacentCells(Vertex vertex)
    {
        int row = vertex.getRow();
        int column = vertex.getColumn();
        int vertexNum = vertex.getId();

        if(row > 0) 
        {   // Connect to top vertex
            Vertex topVertex = graph.getVertex(vertexNum - numColumns);
            graph.createEdge(vertex, topVertex, randomWeight());
            vertex.setUp(topVertex);
        }
        if(row < numRows -1) 
        {   // connect to bottom vertex
            Vertex bottomVertex = graph.getVertex(vertexNum + numColumns);
            graph.createEdge(vertex, bottomVertex, randomWeight());
            vertex.setDown(bottomVertex);
        }
        if(column > 0) 
        {   // connect to left vertex
            Vertex leftVertex = graph.getVertex(vertexNum -1);
            graph.createEdge(vertex, leftVertex, randomWeight());
            vertex.setLeft(leftVertex);
        }
        if(column < numColumns -1) 
        {   // connect to right vertex 
            Vertex rightVertex = graph.getVertex(vertexNum +1);
            graph.createEdge(vertex, rightVertex, randomWeight());
            vertex.setRight(rightVertex);
        }
    }

    private int randomWeight()
    {
        Random random = new Random();
        int max = 4;
        int min = 1;
        return random.nextInt(max - min + 1) + min;
    }
    
    private void breakWalls(Vertex a, Vertex b, LinkedList<Vertex> list)
    {
     
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

                breakWalls(current, temp, visited);
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
        graph.getVertices().forEach(vertex->rectify(vertex));
    }

    private void rectify(Vertex v)
    {
        if(v.hasDownWall() && v.getDown() != null)
        {
            graph.removeEdge(v, v.getDown());
            v.setDown(null);
        }
        if(v.hasUpWall() && v.getUp() != null)
        {
            graph.removeEdge(v, v.getUp());
            v.setUp(null);
        }
        if(v.hasLeftWall() && v.getLeft() != null)
        {
            graph.removeEdge(v, v.getLeft());
            v.setLeft(null);
        }
        if(v.hasRightWall() && v.getRight() != null)
        {
            graph.removeEdge(v,v.getRight());
            v.setRight(null);
        }
    }

    public HashSet<Vertex> findPath(Vertex start)
    {
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