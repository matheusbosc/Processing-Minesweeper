/*import java.util.ArrayList;

public class Tile
{
  public boolean mined = false;
  public boolean bomb = false;
  public boolean flagged = false;

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
   
   *//*
  public Grid(int _rows, int _columns, int _gridBoxSize, int _firstCoordsX, int _firstCoordsY)
  {
    rows = _rows;
    columns = _columns;
    gridBoxSize = _gridBoxSize;
    firstCoordsX = _firstCoordsX;
    firstCoordsY = _firstCoordsY;

    bombAmount = (int)((rows * columns) / 10);

    tiles = new ArrayList<>(columns);
    for (int i = 0; i < columns; i++)
    {
      tiles.add(new ArrayList<Tile>(rows));

      for (int j = 0; j < rows; j++)
      {
        tiles.get(i).add(new Tile());
      }
    }

    randomSeed(second()*minute()*hour());

    ArrayList<PVector> blacklist = new ArrayList<PVector>();

    for (int i = 0; i < bombAmount; i++)
    {
      PVector bomb = getRandomUniqueVector(blacklist, new PVector(0,0), new PVector(columns - 1,rows - 1));
      tiles.get((int)bomb.x).get((int)bomb.y).bomb = true;
      blacklist.add(bomb);
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

  private PVector getRandomUniqueVector(ArrayList<PVector> blacklist, PVector rangeMin, PVector rangeMax)
  {
    PVector newVec = new PVector(-1,-1);
    boolean badNumber = false;

    while (true)
    {
      newVec.x = random(rangeMin.x, rangeMax.x);
      newVec.y = random(rangeMin.y, rangeMax.y);

      for (int i = 0; i < blacklist.size(); i++)
      {
        if (newVec.x == blacklist.get(i).x && newVec.y == blacklist.get(i).y)
        {
          badNumber = true;
          break;
        }
      }

      if (!badNumber) break;
    }

    print(newVec.x + ", " + newVec.y + "\n");

    return newVec;
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

      Tile tile = gameGrid.tiles.get(i).get(j);
      
      if (i % 2 == 0) // column is even
      {
        if (j % 2 == 0) // row is even
        {
          col = tile.mined ? (tile.bomb ? #4b2020 : #caa540) : #62AA30;
        } else // row is odd
        {
          col = tile.mined ? (tile.bomb ? #4b2020 : #d4ae43) : #68BC2E;
        }
      } else // column is odd
      {
        if (j % 2 == 0) // row is even
        {
          col = tile.mined ? (tile.bomb ? #4b2020 : #d4ae43) : #68BC2E;
        } else // row is odd
        {
          col = tile.mined ? (tile.bomb ? #4b2020 : #caa540) : #62AA30;
        }
      }
      
      fill(col);
      
      rect(pos.x, pos.y, cellSizeX, cellSizeX);
      tile.ConfigureTile((int) pos.x, (int) pos.y, cellSizeX, cellSizeX);

      if (tile.flagged)
      {
        fill( #d32d2d );
        ellipseMode(CORNER);
        ellipse(pos.x + 5, pos.y + 5, cellSizeX - 10, cellSizeX - 10);
      }
    }
  }
  
}

void mouseClicked()
{
  int _w;
  int _h;
  PVector _tL;

  boolean breakLoop = false;
  
  // Detect tile
  for (int i = 0; i < columns; i++)
  {
    for (int j = 0; j < rows; j++)
    {
      if (gameGrid.tiles.get(i).get(j).mined == false)
      {
      _w = gameGrid.tiles.get(i).get(j).width;
      _h = gameGrid.tiles.get(i).get(j).height;
      _tL = gameGrid.tiles.get(i).get(j).topLeft;

      if (mouseX > _tL.x && mouseX < _tL.x + _w &&
          mouseY > _tL.y && mouseY < _tL.y + _h)
      {
        
        if (mouseButton == LEFT)
        {
          gameGrid.tiles.get(i).get(j).mined = true;
          gameGrid.tiles.get(i).get(j).flagged = false;
        }
        else 
        {
          gameGrid.tiles.get(i).get(j).flagged = true;
        }

        breakLoop = true;
        break;
      }
    }
    }

    if (breakLoop) break;
  }
}*/
