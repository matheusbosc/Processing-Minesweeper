import java.util.ArrayList;

public class Grid
{

  private int rows, columns;
  private int gridBoxSize;
  private int firstCoordsX, firstCoordsY;
  
  private ArrayList<PVector> bombs = new ArrayList<PVector>();
  private ArrayList<PVector> minedTiles = new ArrayList<PVector>();

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

int rows = 10;
int columns = 10;
int cellSizeX = 700 / columns;

boolean drawGrid = false;

Grid gameGrid;

void setup()
{
  size(900,900);
  
  setup_game(16,16);
}

void setup_game(int _rows, int _columns)
{
  rows = _rows;
  columns = _columns;
  cellSizeX = 800 / columns;

  gameGrid = new Grid(rows, columns, cellSizeX, 50, 50);
}

void draw()
{
  background(#5CA02D);
  
  
  // Inside Square
  fill(#62AA30);
  noStroke();
  rect(50, 50, 800, 800);
  
  if (drawGrid)
  {
    for (int i = 0; i <= columns; i++)
    {
      stroke(#000000);
      strokeWeight(3);
      line(50 + (i * cellSizeX), 50, 50 + (i * cellSizeX), 850);
    }
  
    for (int i = 0; i <= rows; i++)
    {
      stroke(#000000);
      strokeWeight(3);
      line(50, 50 + (i * cellSizeX), 850, 50 + (i * cellSizeX));
    }
  }
  
  noStroke();
  
  for (int i = 0; i < columns; i++)
  {
    for (int j = 0; j < rows; j++)
    {
      PVector pos = gameGrid.getRealSquarePosition(j + 1,i + 1);
      
      color col = 0x000000;
      
      if (i % 2 == 0) // column is even
      {
        if (j % 2 == 0) // row is even
        {
          col = #62AA30;
        } else // row is odd
        {
          col = #68BC2E;
        }
      } else // column is odd
      {
        if (j % 2 == 0) // row is even
        {
          col = #68BC2E;
        } else // row is odd
        {
          col = #62AA30;
        }
      }
      
      fill(col);
      
      rect(pos.x, pos.y, cellSizeX, cellSizeX);
    }
  }
  
}
