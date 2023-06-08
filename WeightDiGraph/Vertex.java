package WeightDiGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Vertex 
{
    private int id;
    private int x;
    private int y;
    private int row;
    private int column;
    private Vertex northVertex;
    private Vertex southVertex;
    private Vertex westVertex;
    private Vertex eastVertex;
    private boolean[] walls; // [0]: top wall, [1]: bottom wall, [2]: left wall, [3]: right wall
    private Set<Vertex> adjacent;

    public Vertex(int id, int x, int y, int row, int column)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.column = column;
        this.row = row;
        walls = new boolean[4];
        walls[0] = true;
        walls[1] = true;
        walls[2] = true;
        walls[3] = true;
        northVertex = null;
        southVertex = null;
        westVertex = null;
        eastVertex = null;
        adjacent = new HashSet<Vertex>();
    }

    public int getColumn() 
    {
        return column;
    }

    public int getRow() 
    {
        return row;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setSouthVertex(Vertex south) 
    {
        adjacent.remove(southVertex);
        southVertex = south;
        if(south != null)
        adjacent.add(southVertex);
    }

    public void setWestVertex(Vertex west) 
    {
        adjacent.remove(westVertex);
        westVertex = west;
        if(west != null)
        adjacent.add(westVertex);
    }

    public void setEastVertex(Vertex east)
    {
        adjacent.remove(eastVertex);
        eastVertex = east;
        if(east != null)
        adjacent.add(eastVertex);
    }

    public void setNorthVertex(Vertex north) 
    {
        adjacent.remove(northVertex);
        northVertex = north;
        if(north != null)
        adjacent.add(northVertex);
    }

    public void setNorthWall(Boolean b)
    {
        walls[0] = b;
    }

    public void setSouthWall(Boolean b)
    {
        walls[1] = b;
    }

    public void setEastWall(Boolean b)
    {
        walls[3] = b;
    }

    public void setWestWall(Boolean b)
    {
        walls[2] = b;
    }
    
    public boolean hasNorthWall()
    {
        return walls[0];
    }

    public boolean hasSouthWall()
    {
        return walls[1];
    }

    public boolean hasWestWall()
    {
        return walls[2];
    }

    public boolean hasEastWall()
    {
        return walls[3];
    }

    public void removeEastWall()
    {
        walls[3] = false;
    }

    public void removeWestWall()
    {
        walls[2] = false;
    }

    public void removeSouthWall()
    {
        walls[1] = false;
    }

    public void removeNorthWall()
    {
        walls[0] = false;
    }

    public Vertex getSouthVertex()
    {
        return southVertex;
    }

    public Vertex getWestVertex() 
    {
        return westVertex;
    }

    public Vertex getEastVertex()
    {
        return eastVertex;
    }

    public Vertex getNorthVertex()
    {
        return northVertex;
    }

    public Collection<Vertex> getAdjacent()
    {
        return adjacent;
    }
    
    @Override
    public boolean equals(Object obj) 
    {
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Vertex v = (Vertex) obj;
        return id == v.id;
    }
}