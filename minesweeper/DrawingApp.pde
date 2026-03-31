import java.util.ArrayList;

class FullColor
{
  int r, g, b, a;
  
  public FullColor(int _r, int _g, int _b, int _a)
  {
    r = _r;
    g = _g;
    b = _b;
    a = _a;
  }
}

public class Tile
{
  public PVector topLeft;
  public int width, height;

  public void ConfigureTile(int _topLeftX, int _topLeftY, int _width, int _height)
  {
    topLeft = new PVector(_topLeftX, _topLeftY);
    width = _width;
    height = _height;
  }
}

public class Grid
{

  private int rows, columns;
  private int gridBoxSize;
  private int firstCoordsX, firstCoordsY;

  public int bombAmount;

  public ArrayList<ArrayList<Tile>> tiles;

  /**
   
   @param _rows Amount of rows in the grid
   @param _columns Amount of columns in the grid
   @param _gridBoxSize The size of each box in the grid (pixels)
   @param _firstCoordsX The X position of the top left corner of the grid
   @param _firstCoordsY The Y position of the top left corner of the grid
   
   */
  public Grid(int _rows, int _columns, int _gridBoxSize, int _firstCoordsX, int _firstCoordsY)
  {
    rows = _rows;
    columns = _columns;
    gridBoxSize = _gridBoxSize;
    firstCoordsX = _firstCoordsX;
    firstCoordsY = _firstCoordsY;

    tiles = new ArrayList<>(columns);
    for (int i = 0; i < columns; i++)
    {
      tiles.add(new ArrayList<Tile>(rows));

      for (int j = 0; j < rows; j++)
      {
        tiles.get(i).add(new Tile());
      }
    }
  }

  public PVector getRealSquarePosition(int _row, int _column)
  {
    _row -= 1;
    _column -= 1;

    if (_row > rows - 1 || _column > columns - 1 || _row < 0 || _column < 0) return null;

    int realX = firstCoordsX + (_row * gridBoxSize);
    int realY = firstCoordsY + (_column * gridBoxSize);

    return new PVector(realX, realY);
  }
}


ArrayList<ArrayList<FullColor>> image;

int imageSizeX = 20;
int imageSizeY = 20;

void setup()
{
  size(800,800);
  image = new ArrayList<>();
  
  for (int i = 0; i < imageSizeX; i++)
  {
    image.add(new ArrayList<>());
    
    for (int j = 0; j < imageSizeX; j++)
    {
      image.get(i).add(new FullColor(0,0,0,255));
    }
  }
}

void draw()
{
  background(#E0E0E0);
}
