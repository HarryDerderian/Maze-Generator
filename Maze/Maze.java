package Maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;
public class Maze 
{
    private Graph graph;
    private int numRows;
    private int numColumns;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private Vertex targetVertex;
    private Random random;

    public Maze(int mazeWidth, int mazeHeight, int mazeColumns, int mazeRows)
    {
        width = mazeWidth;
        height = mazeHeight;
        numColumns = mazeColumns;
        numRows = mazeRows;
        cellWidth = width / numColumns;
        cellHeight = height / numRows;
        random = new Random();
        graph = new Graph();
        initializeGrid();
        createMaze();
        // TODO: fix, make look cleaner
        do // ensures target vertex is not start vertex
        targetVertex = graph.getRandomVertex();
        while(targetVertex.equals(graph.getVertex(0))); 
    }

    public Vertex getTarget()
    {
        return targetVertex;
    }

    public Graph getGraph()
    {
        return graph;
    }

    public int getNumRows()
    {
        return numRows;
    }

    public int getNumColumns()
    {
        return numColumns;
    }

    public int getCellHeight()
    {
        return cellHeight;
    }

    public int getCellWidth()
    {
        return cellWidth;
    }

    /**
    * Creates a grid graph based on the specified number of rows and columns.
    * Each vertex in the graph has a unique ID that corresponds to its row and column position.
    * The ID is calculated as row * numColumns + column, where rows and columns start from 0.
    * The top-left vertex has an ID of 0, and the bottom-right vertex has an ID of (numRows * numColumns - 1).
    */
    private void initializeGrid()
    {
        for(int column = 0; column < numColumns; column++)
        {
            for(int row = 0; row < numRows; row++) 
            {                            
                int id = row * numColumns + column;
                int cordX = column * cellWidth;
                int cordY = row * cellHeight;
                Vertex v = new Vertex(id, cordX, cordY, row, column);
                graph.addVertex(v);
            }
        }
        graph.getVertices().forEach(vertex->connectAdjacentCells(vertex));
    }

    /**
    * Creates edges from a Vertex to any vertices that are a single row above/below,
    * or single column behind/ahead. Results in the vertex having edges connection it
    * to its north, south, east, west.
    * @param vertex will have edges created to its four adjacent neighbors
    */
    private void connectAdjacentCells(Vertex vertex)
    {
        int row = vertex.getRow();
        int column = vertex.getColumn();
        int vertexNum = vertex.getId();

        if(row > 0) 
        {   // Connect to top vertex
            Vertex topVertex = graph.getVertex(vertexNum - numColumns);
            graph.createEdge(vertex, topVertex, random.nextInt(4) + 1);
            vertex.setNorthVertex(topVertex);
        }
        if(row < numRows -1) 
        {   // connect to bottom vertex
            Vertex bottomVertex = graph.getVertex(vertexNum + numColumns);
            graph.createEdge(vertex, bottomVertex, random.nextInt(4) + 1);
            vertex.setSouthVertex(bottomVertex);
        }
        if(column > 0) 
        {   // connect to left vertex
            Vertex leftVertex = graph.getVertex(vertexNum -1);
            graph.createEdge(vertex, leftVertex, random.nextInt(4) + 1);
            vertex.setWestVertex(leftVertex);
        }
        if(column < numColumns -1) 
        {   // connect to right vertex 
            Vertex rightVertex = graph.getVertex(vertexNum +1);
            graph.createEdge(vertex, rightVertex, random.nextInt(4) + 1);
            vertex.setEastVertex(rightVertex);
        }
    }

    /**
     * Chooses a random starting vertex, proceeds to visit every vertex inside 
     * the graph using a random direction dfs. Whenever a vertex is visited the 
     * overlapping wall between it and the previous vertex is removed.
     * Assumes graph is a grid-graph.
     */
    private void createMaze()
    {
        Vertex current = graph.getRandomVertex();
        Stack<Vertex> previous = new Stack<>();
        boolean[] visited = new boolean[graph.totalVerticies()];
        visited[current.getId()] = true;
        int visitedVertices = 1;
        int totalVertices = graph.totalVerticies();

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
    
    /**
     * Connects two cells by removing their overlapping walls.
     * Checks if the cells are in the same row or column based on 
     * the positioning removes appropriate walls.
     * @param v1 first vertex
     * @param v2 second vertex
     */
    private void connectCells(Vertex v1, Vertex v2)
    {
        if(v1.getColumn() == v2.getColumn()) // same column
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
        else if(v1.getRow() == v2.getRow()) // same row
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

    /**
    * Finds and returns a vertex that is adjacent to origin and not true in visited index. 
    * @param origin The vertex that will be searched for an unvisited neighbor.
    * @param visited A boolean array of visited vertices with index based on vertex ids.
    * @return An unvisited vertex in the order of: Right, Down, Left, Up, or null.
    */
    private Vertex unvisitedOrderedNeighbor(Vertex origin, boolean[] visited)
    {
         Vertex unvisitedNeighbor = null;
         // check right
         if(origin.getEastVertex() != null && !visited[origin.getEastVertex().getId()])
         {
            unvisitedNeighbor = origin.getEastVertex();
         }
         // check below
         else if(origin.getSouthVertex() != null && !visited[origin.getSouthVertex().getId()])
         {
            unvisitedNeighbor = origin.getSouthVertex();
         }
         // check left
         else if(origin.getWestVertex() != null && !visited[origin.getWestVertex().getId()])
         {
            unvisitedNeighbor = origin.getWestVertex();
         }
         // check above
         else if(origin.getNorthVertex() != null && !visited[origin.getNorthVertex().getId()])
         {
            unvisitedNeighbor = origin.getNorthVertex();
         }
         return unvisitedNeighbor;
    }

    /**
     * Finds and returns a vertex that is adjacent to origin and not true in visited index. 
     * @param origin The vertex that will be searched for an unvisited neighbor.
     * @param visited A boolean array of visited vertices with index based on vertex ids.
     * @return An unvisited vertex in the random order or null if none exist
     */
    private Vertex randomUnvisitedAdjacent(Vertex origin, boolean[] visisted)
    {
        ArrayList<Vertex> unvisitedNeighbors = new ArrayList<>();
        for(Vertex adjacent : graph.getAdjacent(origin))
            if(!visisted[adjacent.getId()])
                unvisitedNeighbors.add(adjacent);
        
        return unvisitedNeighbors.isEmpty() ? null : 
                        unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
    }

    /**
     * Removes any edges from v that have a wall blocking them.
     * @param v the vertex that will have edges removed if a wall is in the way.
     */
    private void rectify(Vertex v)
    {
        if(v.hasDownWall() && v.getSouthVertex() != null)
        {
            graph.removeEdge(v, v.getSouthVertex());
            v.setSouthVertex(null);
        }
        if(v.hasUpWall() && v.getNorthVertex() != null)
        {
            graph.removeEdge(v, v.getNorthVertex());
            v.setNorthVertex(null);
        }
        if(v.hasLeftWall() && v.getWestVertex() != null)
        {
            graph.removeEdge(v, v.getWestVertex());
            v.setWestVertex(null);
        }
        if(v.hasRightWall() && v.getEastVertex() != null)
        {
            graph.removeEdge(v,v.getEastVertex());
            v.setEastVertex(null);
        }
    }

    /**
     * Traverses the maze using bfs adding each vertex 
     * traversed to a collection until the end point is reached.
     * @param start the starting vertex
     * @return a collection of all vertices traversed in the bfs traversal
     */
    public Collection<Vertex> bfsTraversal(Vertex start)
    {
        LinkedList<Vertex> path = new LinkedList<>();
        Queue<Vertex> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.totalVerticies()];
        visited[start.getId()] = true;
        queue.add(start);
        int targetId = targetVertex.getId();
        while(!queue.isEmpty() && !visited[targetId])
        {
            Vertex current = queue.poll();
            path.add(current);
            for(Vertex adjacent : graph.getAdjacent(current))
                if(!visited[adjacent.getId()])
                {
                    queue.add(adjacent);
                    visited[adjacent.getId()] = true;
                }
            
        }
        return path;
    }
   
    /**
     * Traverses the maze using dfs adding each vertex 
     * traversed to a collection until the end point is reached.
     * @param start the starting vertex
     * @return a collection of all vertices traversed in the dfs traversal
     */
    public Collection<Vertex> dfsTraversal(Vertex start)
    {
        LinkedList<Vertex> path = new LinkedList<>();    
        boolean[] visited = new boolean[graph.totalVerticies()];
        Stack<Vertex> previous = new Stack<>();
        Vertex current = start;
        visited[current.getId()] = true;
        int targetNum = targetVertex.getId();
            
            while(!visited[targetNum]) 
            {
                Vertex next = unvisitedOrderedNeighbor(current, visited);
                if(next != null)
                {
                    visited[next.getId()] = true;
                    previous.push(current);
                    current = next;
                }
                else
                {
                    path.add(current);
                    current = previous.pop();
                }
            }
            while(!previous.empty())
            path.add(previous.pop());
            return path;
    }

    /**
     * Traverses the maze using dfs with a stack for back tracking.
     * @param start the starting vertex
     * @return a collection (stack) containing only vertices leading to the target vertex.
     */ 
    public Collection<Vertex> getPath(Vertex current)
    {
        boolean[] visited = new boolean[graph.totalVerticies()];
        Stack<Vertex> path = new Stack<>();
        visited[current.getId()] = true;
        int targetNum = targetVertex.getId();
        
        while(!visited[targetNum]) 
        {
            Vertex next = unvisitedOrderedNeighbor(current, visited);
            if(next != null)
            {
                visited[next.getId()] = true;
                path.push(current);
                current = next;
            }
            else
            {
                current = path.pop();
            }
        }
        path.push(targetVertex);
        return path;
    }
}