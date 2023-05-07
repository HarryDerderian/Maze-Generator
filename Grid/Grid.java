package Grid;

public class Grid 
{
    private final int ROWS = 30;
    private final int COLUMNS = 30;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;

    private Grid(){}
    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        cellWidth = width / COLUMNS;
        cellHeight = height / ROWS;
    }
    public int getCOLUMNS() {
        return COLUMNS;
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
        return ROWS;
    }
    public int getWidth() {
        return width;
    }
   
    
}
