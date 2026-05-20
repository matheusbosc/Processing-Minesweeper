package Utilities;

/**
 * Grid
 * A class for storing grid information
 * @author Matheus Boscariol
 * @version 17/5/2026
 */
public class Grid {
    /// Coordinates of the 2 extreme points on the grid (used to calculate size)
    public Vector topLeftCoords, bottomRightCoords;
    /// Size of a tile (in pixels)
    public int tileSize;
    /// Amount of tiles in the grid (X and Y)
    public Vector gridSize;

    public Grid(int _minX, int _maxX, int _minY, int _maxY)
    {
        topLeftCoords = new Vector(_minX, _minY);
        bottomRightCoords = new Vector(_maxX, _maxY);
    }
}
