package WeightDiGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;


public class Graph
{
    private HashMap<Vertex, HashSet<Edge>> vertexAndEdges;
    private int totalEdges;

    public Vertex getLast(){
        var iter = vertexAndEdges.keySet().iterator();
        int id = 0;
        Vertex max = null;
        while(iter.hasNext()){
            Vertex current = iter.next();
            
            if(current.getId() > id){
                max = current;
                id = max.getId();
            }
        }
        return max;
    }

    public Vertex getVertex(int id)
    {
        var iter = vertexAndEdges.keySet().iterator();
        while(iter.hasNext()){
            Vertex current = iter.next();
            
            if(current.getId() == id){
                return current;
            }
        }
        return null;
    }
    public Graph()
    {
        vertexAndEdges = new HashMap<>();
        totalEdges = 0;
    }

    public boolean containsVertex(Vertex v)
    {
        return vertexAndEdges.containsKey(v);
    }

    public int totalEdges()
    {
        return totalEdges;
    }

    public int totalVerticies()
    {
        return vertexAndEdges.size();
    }

    public void addVertex(Vertex v)
    {
        if(!vertexAndEdges.containsKey(v))
        vertexAndEdges.put(v, new HashSet<Edge>());
    }

    public HashSet<Vertex> getVertices(){
        HashSet<Vertex> vertices = new HashSet<>();
        vertexAndEdges.forEach( (Vertex v, HashSet<Edge> e)->{
            vertices.add(v);
        } );
        return vertices;
    }
    public HashSet<Edge> getEdges(Vertex v)
    {
        return vertexAndEdges.get(v);
    }

    public void createEdge(Vertex origin, Vertex destination, int weight)
    {
        if(vertexAndEdges.containsKey(origin) 
        && vertexAndEdges.containsKey(destination))
        {
            vertexAndEdges.get(origin).add(new Edge(origin, destination, weight));
            totalEdges++;    
        }
    }

    public void removeEdge(Vertex origin, Vertex destination)
    {
        if(vertexAndEdges.containsKey(origin) 
        && vertexAndEdges.containsKey(destination))
        {
            var iter = vertexAndEdges.get(origin).iterator();
            Edge current;
            while(iter.hasNext()){
                current = iter.next();
                if(current.getDest().getId() == destination.getId()){
                    iter.remove();
                    totalEdges--;
                    break;
                }
            }
        }
    }

    public void removeVertex(Vertex vertexToRemove)
    {
        if(vertexAndEdges.containsKey(vertexToRemove))
        {
            vertexAndEdges.remove(vertexToRemove);
            vertexAndEdges.forEach( (Vertex v, HashSet<Edge> edges)->{
                for(Edge edge : edges)
                {
                    if(edge.getDest().equals(vertexToRemove)){
                        vertexAndEdges.get(v).remove(edge);
                        totalEdges--;
                    }
                }
                });
        }
    }


    public Vertex getRandomVertex()
    {
        Random random = new Random();
        int index = random.nextInt(vertexAndEdges.size());
        return getVertex(index);
    }

    public void clear()
    {
        vertexAndEdges.clear();
        totalEdges = 0;
    }

    public LinkedList<Vertex> getAdjacent(Vertex v){
        LinkedList<Vertex> adjacentVertices = new LinkedList<>();
        if(vertexAndEdges.containsKey(v))
        {
            if(v.getRight() != null)
            {
                adjacentVertices.addLast(v.getRight());
            }
            if(v.getDown() != null)
            {
                adjacentVertices.addLast(v.getDown());
            }
            if(v.getLeft() != null)
            {
                adjacentVertices.addLast(v.getLeft());
            }
            if(v.getUp() != null)
            {
                adjacentVertices.addLast(v.getUp());
            }
        }
        return adjacentVertices;
    }
}