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
