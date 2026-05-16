package Utilities;

public class Grid {
    public Vector topLeftCoords, bottomRightCoords; // The coordinates of the 2 extreme points on the grid (used to calculate size)
    public int tileSize; // Size of a tile (in pixels)
    public Vector gridSize; // Amount of tiles in the grid (X and Y)

    public Grid(int _minX, int _maxX, int _minY, int _maxY)
    {
        topLeftCoords = new Vector(_minX, _minY);
        bottomRightCoords = new Vector(_maxX, _maxY);
    }
}
