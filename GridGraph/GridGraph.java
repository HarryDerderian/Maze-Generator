package GridGraph;

import Grid.Grid;
import WeightDiGraph.Graph;
import WeightDiGraph.Vertex;
import WeightDiGraph.Edge;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;


public class GridGraph 
{
    private Graph graph;
    private Grid grid;
    private Vertex[][] vertices;
    public GridGraph(int gridWidth, int gridHeight){
        grid = new Grid(gridWidth, gridHeight);
        graph = new Graph();
        updateGraph();
        makeMaze();
    }
    
public Graph getGraph() {
    return graph;
}
public int getCellHeight(){
    return grid.getCellHeight();
}
public int getCellWidth(){
    return grid.getCellWidth();
}
    private void updateGraph(){
        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();
        int cellHeight = grid.getCellHeight();
        int cellWidth = grid.getCellWidth();
        vertices = new Vertex[grid.getROWS()][grid.getCOLUMNS()];
        for(int x = 0; x <grid.getCOLUMNS(); x++){
            for(int y = 0; y < grid.getROWS(); y++){
                int id = (y * grid.getCOLUMNS()) + x;
                Vertex v = new Vertex(id, x*cellWidth,y*cellHeight);
                graph.addVertex(v);
                vertices[y][x] = v;
            }
        }
        /*for(int x = 0; x < gridWidth; x += cellWidth){
            for(int y = 0; y < gridHeight; y += cellHeight){
                int row = y / cellHeight;
                int col = x / cellWidth;
                int id = ((y/cellHeight) *grid.getCOLUMNS()) + x/cellWidth;
                Vertex v = new Vertex(id,x,y);
                graph.addVertex(v);
                vertices[row][col] = v;
                
            }
        }*/
        Random random = new Random();
        int min = 1;
        int max = 20;
         for(int x = 0; x < grid.getROWS(); x++){
            for(int y = 0; y < grid.getCOLUMNS(); y++){
                
                if(y > 0){
                graph.createEdge(vertices[x][y], vertices[x][y -1], random.nextInt(max - min + 1) + min);
                vertices[x][y].setLeft(vertices[x][y -1]);
                }
                if(x > 0){
                graph.createEdge(vertices[x][y], vertices[x-1][y], random.nextInt(max - min + 1) + min);
                vertices[x][y].setUp(vertices[x-1][y]);
                }
                if(y < grid.getCOLUMNS() -1){
                graph.createEdge(vertices[x][y], vertices[x][y+1], random.nextInt(max - min + 1) + min);
                vertices[x][y].setRight(vertices[x][y+1]);
                }
                if(x < grid.getROWS() -1){
                graph.createEdge(vertices[x][y], vertices[x+1][y], random.nextInt(max - min + 1) + min);
                vertices[x][y].setDown(vertices[x+1][y]);
                }
            }
        }
    }

    private void connectCells(Vertex a, Vertex b, LinkedList<Vertex> list){
        if(a.getDown() != null && a.getDown().getId() == b.getId()){
            a.remvoeDownWall();
            b.removeUpWall();
        }
        else if(a.getUp()!= null &&a.getUp().getId() == b.getId()){
            a.removeUpWall();
            b.remvoeDownWall();
        }
        else if(a.getRight()!= null&&a.getRight().getId() == b.getId()){
            a.removeRightWall();
            b.removeLeftWall();
        }
        else if(a.getLeft()!= null&&a.getLeft().getId() == b.getId()){
            a.removeLeftWall();
            b.removeRightWall();
        }
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
        // DFS must be PRE-ORDER
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
    }
}
