package WeightDiGraph;

public class Vertex 
{

    private int id;
    private int x;
    private int y;
    private Vertex up;
    private Vertex down;
    private Vertex left;
    private Vertex right;
    private boolean[] walls; // 0:up 1:down 2:left 3:right

    public Vertex(int id, int x, int y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        walls = new boolean[4];
        up = null;
        down = null;
        right = null;
        left = null;
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
    public void setY(int y) {
        this.y = y;
    }
    public void setDown(Vertex down) {
        this.down = down;
        walls[1] = true;
    }
    public void setLeft(Vertex left) {
        this.left = left;
        walls[2] = true;
    }
    public void setRight(Vertex right) {
        this.right = right;
        walls[3] = true;   
    }
    public void setUp(Vertex up) {
        this.up = up;
        walls[0] = true;
    }
    public boolean hasUpWall(){
        return walls[0];
    }
    public boolean hasDownWall(){
        return walls[1];
    }
    public boolean hasLeftWall(){
        return walls[2];
    }
    public boolean hasRightWall(){
        return walls[3];
    }
    public void removeRightWall(){
        walls[3] = false;
    }
    public void removeLeftWall(){
        walls[2] = false;
    }
    public void remvoeDownWall(){
        walls[1] = false;
    }
    public void removeUpWall(){
        walls[0] = false;
    }

    public Vertex getDown() {
        return down;
    }
    public Vertex getLeft() {
        return left;
    }
    public Vertex getRight() {
        return right;
    }
    public Vertex getUp() {
        return up;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) return false;
        Vertex v = (Vertex) obj;
        return id == v.id;
    }
}
