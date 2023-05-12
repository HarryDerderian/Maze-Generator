package WeightDiGraph;

public class Edge 
{
    private int weight;
    private Vertex dest;
    private Vertex origin;
    
  
    
    public Edge(Vertex origin, Vertex dest){
        this.origin = origin;
        this.dest = dest;
        weight = 1;
    }

    public Edge(Vertex origin, Vertex dest, int weight){
        this.origin = origin;
        this.dest = dest;
        this.weight = weight;
    }

    public Vertex getDest() {
        return dest;
    }
    public int getWeight() {
        return weight;
    }
    public Vertex getOrigin() {
        return origin;
    }
    public void setDest(Vertex dest) {
        this.dest = dest;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setOrigin(Vertex origin) {
        this.origin = origin;
    }
}
