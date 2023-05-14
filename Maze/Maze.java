package Maze;

import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;
import WeightDiGraph.Edge;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class Maze 
{
    private Graph graph;
    private int numRows;
    private int numColumns;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private HashSet<Vertex> startToEnd;

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
        createMaze();
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
    
    private void createMaze()
    {
        Vertex startVertex = graph.getRandomVertex();
        Stack<Vertex> previous = new Stack<>();
        boolean[] visited = new boolean[graph.totalVerticies()];
        visited[startVertex.getId()] = true;
        int visitedVertices = 1;
        int totalVertices = graph.totalVerticies();
        Vertex current = startVertex;

        while(visitedVertices < totalVertices)
            {
                Vertex next = randomUnvisitedAdjacent(current, visited);
                if(next != null)
                {
                    connectCells(current, next);
                    previous.push(current);
                    current = next;
                    visited[current.getId()] = true;
                    visitedVertices++;
                }
                else
                {
                    current = previous.pop();
                }
            }

        graph.getVertices().forEach(vertex->rectify(vertex));
    }
    
    private void connectCells(Vertex v1, Vertex v2)
    {
        if(v1.getColumn() == v2.getColumn())
        {
            if(v1.getId() > v2.getId())
            {
                v1.removeUpWall();
                v2.remvoeDownWall();
            }
            else
            {
                v1.remvoeDownWall();
                v2.removeUpWall();
            }
        }
        else 
        {
            if(v1.getId() > v2.getId())
            {
                v1.removeLeftWall();
                v2.removeRightWall();
            }
            else
            {
                v1.removeRightWall();
                v2.removeLeftWall();
            }
        }
    }

    private Vertex randomUnvisitedAdjacent(Vertex v, boolean[] visisted)
    {
        int weight = -1;
        Vertex returnVertex = null;
        for(Edge edge : graph.getEdges(v))
        {
            Vertex currentVertex = edge.getDest();
            int currentWeight = edge.getWeight();
            if(!visisted[currentVertex.getId()] && weight < currentWeight)
            {
                returnVertex = currentVertex;
                weight = currentWeight;
            }
        }
        return returnVertex;
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
        boolean[] visited = new boolean[graph.totalVerticies()];
        visited[start.getId()] = true;
        startToEnd = new HashSet<>();
        startToEnd.add(start);
        Vertex target = graph.getLast();
        pathFinderDFS(start,target, visited);
        return startToEnd;
    }
    
    Vertex pathFinderDFS(Vertex current, Vertex target, boolean[] visited)
    {
        if(target.getId() == current.getId())
        return current;

        for(Vertex v : graph.getAdjacent(current)){
            if(visited[target.getId()])
            break;
            if(!visited[v.getId()])
            {
                visited[v.getId()] = true;
                Vertex temp = pathFinderDFS(v, target, visited);
                if(temp != null)
                startToEnd.add(temp);
            }
        }
        if(visited[target.getId()])
        return current;
        else
        return null;
    }
}