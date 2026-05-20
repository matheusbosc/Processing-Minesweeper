import Utilities.Vector;

/**
 * Tile:
 * Contains information for tiles in the game
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */
public class Tile
{
    /// Tile position in the grid
    public Vector coordinate;
    /// Is the tile a bomb
    public boolean isBomb = false;
    /// Has the tile been flagged
    public boolean isFlagged = false;
    /// Has the tile been clicked
    public boolean isShown = false;
    /// How many bombs surround the tile
    public int bombsSurrounding;
}
