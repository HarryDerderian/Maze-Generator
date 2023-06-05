package WeightDiGraph;

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
    private Vertex[] adjacentVertices;

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

        adjacentVertices = new Vertex[4];
        adjacentVertices[0] = eastVertex;
        adjacentVertices[1] = southVertex;
        adjacentVertices[2] = northVertex;
        adjacentVertices[3] = westVertex;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setSouthVertex(Vertex south) 
    {
        southVertex = south;
        adjacentVertices[1] = southVertex;
    }

    public void setWestVertex(Vertex west) 
    {
        westVertex = west;   
    }

    public void setEastVertex(Vertex east)
    {
        eastVertex = east;
    }

    public void setNorthVertex(Vertex north) 
    {
        northVertex = north;
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
    
    public boolean hasUpWall()
    {
        return walls[0];
    }

    public boolean hasDownWall()
    {
        return walls[1];
    }

    public boolean hasLeftWall()
    {
        return walls[2];
    }

    public boolean hasRightWall()
    {
        return walls[3];
    }

    public void removeRightWall()
    {
        walls[3] = false;
    }

    public void removeLeftWall()
    {
        walls[2] = false;
    }

    public void remvoeDownWall()
    {
        walls[1] = false;
    }

    public void removeUpWall()
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
    
    public Vertex[] getAdjacent()
    {
        return adjacentVertices;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj.getClass() != this.getClass()) return false;
        Vertex v = (Vertex) obj;
        return id == v.id;
    }
}
