package Grid;

public class Grid 
{
    private int rows;
    private int columns;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    private Grid(){}
    public Grid(int width, int height, int columns, int rows){
        this.width = width;
        this.height = height;
        this.columns = columns;
        this.rows = rows;
        cellWidth = width / columns;
        cellHeight = height / rows;
    }
    public int getCOLUMNS() {
        return columns;
    }
    public int getCellHeight() {
        return cellHeight;
    }
    public int getCellWidth() {
        return cellWidth;
    }
    public int getHeight() {
        return height;
    }
    public int getROWS() {
        return rows;
    }
    public int getWidth() {
        return width;
    }
   
    
}
