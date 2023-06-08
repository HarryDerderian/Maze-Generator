package WeightDiGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Graph
{
    private HashMap<Vertex, HashSet<Edge>> vertexAndEdges;
    private int totalEdges;

    public Graph()
    {
        vertexAndEdges = new HashMap<>();
        totalEdges = 0;
    }

    /**
     * Iterates through every Vertex inside the graph updating the 
     * current vertex whenever a vertex with a larger ID number is found.
     * @return Vertex with the largest ID number
     */
    public Vertex getLast()
    {
        var iter = vertexAndEdges.keySet().iterator();
        int id = 0;
        Vertex max = null;
        while(iter.hasNext())
        {
            Vertex current = iter.next();
            if(current.getId() > id)
            {
                max = current;
                id = max.getId();
            }
        }
        return max;
    }

    /**
     * Iterates through every Vertex inside the graph until a Vertex with the 
     * same ID as the parameter is found, or null if no such Vertex is found.
     * @param id the ID that will be searched for within each Vertice
     * @return The Vertex with the specified ID or null
     */
    public Vertex getVertex(int id)
    {
        var iter = vertexAndEdges.keySet().iterator();
        while(iter.hasNext())
        {
            Vertex current = iter.next();
            if(current.getId() == id)
                return current;
            
        }
        return null;
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

    public void addVertex(Vertex newVertex)
    {
        if(!vertexAndEdges.containsKey(newVertex))
        vertexAndEdges.put(newVertex, new HashSet<Edge>());
    }

    public Collection<Vertex> getAllVertices()
    {
        return vertexAndEdges.keySet();
    }
    
    public Collection<HashSet<Edge>> getAllEdges()
    {
        return vertexAndEdges.values();
    }

    public Collection<Edge> vertexOutgoingEdges(Vertex v)
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
            remove(origin, destination);
        }
    }

    /**
     * Removes a specified vertex from the graph and all edges with it as a dest.
     * Uses helper method remove.
     * @param vertexToRemove the specified vertex to be removed
     */
    public void removeVertex(Vertex vertexToRemove)
    {
        if(!vertexAndEdges.containsKey(vertexToRemove)) return;
        
        vertexAndEdges.remove(vertexToRemove);
        vertexAndEdges.keySet().forEach(vertex->remove(vertexToRemove,vertex));
    }

    /**
     * Checks a given vertex for any edges/connections to a vertex marked for removal, 
     * if a connection or edge is found the edge is removed.
     * @param vertexToRemove the vertex to be removed
     * @param currentVertex the current vertex to check for connections to vertexToRemove
     */
    private void remove(Vertex currentVertex, Vertex vertexToRemove)
    {
        if(vertexToRemove.equals(currentVertex.getNorthVertex()))
        {
            currentVertex.setNorthVertex(null);
        }
        else if(vertexToRemove.equals(currentVertex.getSouthVertex()))
        {
            currentVertex.setSouthVertex(null);
        }
        else if(vertexToRemove.equals(currentVertex.getEastVertex()))
        {
            currentVertex.setEastVertex(null);
        }
        else if(vertexToRemove.equals(currentVertex.getWestVertex()))
        {
            currentVertex.setWestVertex(null);
        }
        else
        {
            return; // each vertex will have at most 4 edges: west, east, south, north.
        }
        var iter = vertexAndEdges.get(currentVertex).iterator();
        while(iter.hasNext())
        {
            Edge edge = iter.next();
            if (edge.getDest().equals(vertexToRemove))
            {
                iter.remove();
                totalEdges--;
                break;
            }
        }
    }

    public Vertex getRandomVertex()
    {
        Random random = new Random();
        return getVertex(random.nextInt(vertexAndEdges.size()));
    }

    public void clear()
    {
        vertexAndEdges.clear();
        totalEdges = 0;
    }
}