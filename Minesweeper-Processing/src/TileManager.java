/**
 * TileManager:
 * Manage the tiles, generates board, provides tile utilities
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */

import Utilities.Vector;
import processing.core.PApplet;

public class TileManager extends PApplet implements GameModule {

    // Variables
    private Tile[][] tiles; // A matrix (2D array) of tiles | tiles[x][y]
    private Tile[] bombs;

    // Board config
    private final Vector gridSize;

    // Methods

    public TileManager(int _sizeX, int _sizeY)
    {
        gridSize = new Vector((float) _sizeX, (float) _sizeY);
    }

    public void onStart() {
        // Generate Grid
        tiles = new Tile[(int) gridSize.x][(int) gridSize.y]; // Initialize tiles array

        // Assign Bombs
        int bombAmount = 10;
        bombs = new Tile[bombAmount];

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[i].length; j++)
            {
                if (tiles[i][j] == null) tiles[i][j] = new Tile();
                tiles[i][j].coordinate = new Vector(i+1,j+1);
            }
        }

        for (int i = 0; i < bombAmount; i++)
        {
            boolean bombSet = false;

            do {
                Vector tileIndices = Vector.RandomVector(0, gridSize.x, 0, gridSize.y);

                var t = tiles[(int) tileIndices.x][(int) tileIndices.y];

                if (!t.isBomb)
                {
                    t.isBomb = true;
                    bombSet = true;
                    bombs[i] = t;
                }
            } while (!bombSet);
        }
    }

    public void onStop() {}

    public void onUpdate() {}

    /**
     * Gives a reference to the tile
     * @param position The position on a matrix of the clicked tile
     * @return The type of the tile: Safe, Bomb, or Clicked
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public Tile getTileInfo(Vector position)
    {
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");;

        return tiles[(int) position.x][(int) position.y];
    }

    /**
     * Provides the type of tile
     * @param position The position on a matrix of the clicked tile
     * @return The type of the tile: Safe, Bomb, or Clicked
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public TileType checkTileType(Vector position)
    {
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");

        if (tiles[(int) position.x][(int) position.y].isBomb) return TileType.Bomb;
        else return TileType.Safe;
    }

    /**
     * Marks the tile as flagged
     * @param position The position on a matrix of the clicked tile
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public void flagTile(Vector position)
    {
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");

        tiles[(int) position.x][(int) position.y].isFlagged = true;
    }

    /**
     * Fills around the tile that was clicked if it isn't a bomb
     * @param position The position on a matrix of the clicked tile
     */
    public void floodFill(Vector position)
    {

    }
}

