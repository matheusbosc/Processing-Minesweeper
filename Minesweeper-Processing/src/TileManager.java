import Utilities.Vector;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * TileManager:
 * Manage the tiles, generates board, provides tile utilities
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */
public class TileManager implements GameModule {

    // Variables
    private Tile[][] tiles; // A matrix (2D array) of tiles | tiles[x][y]
    private Tile[] bombs; // List of all the bombs in the board
    Minesweeper main;

    // Board config
    public final Vector gridSize; // The size of the board (amount of tiles X and Y)
    int bombAmount = 10; // Number of bombs to use
    int flagAmount = 0;
    int correctAmount = 0;


    // Methods

    public TileManager(Minesweeper _main, int _sizeX, int _sizeY, int _bombs) // Constructor
    {
        main = _main;
        gridSize = new Vector((float) _sizeX, (float) _sizeY);
        bombAmount = _bombs;
    }

    public void onStart()
    {
        // Generate Grid
        tiles = new Tile[(int) gridSize.x][(int) gridSize.y]; // Initialize tiles array

        bombs = new Tile[bombAmount]; // Initialize list of bombs

        // Initialize tiles
        for (int i = 0; i < tiles.length; i++)
        {   for (int j = 0; j < tiles[i].length; j++)
            {
                var t = new Tile(); // Create tile
                t.coordinate = new Vector(i,j); // Set coords
                tiles[i][j] = t; // Assign tile to the tiles matrix
            }
        }

        // Assign bombs
        for (int i = 0; i < bombAmount; i++)
        {
            boolean bombSet = false; // Has the current bomb been set

            do {
                Vector tileIndices = Vector.RandomVector(0, gridSize.x, 0, gridSize.y); // Get random position in grid

                var t = tiles[(int) tileIndices.x][(int) tileIndices.y]; // The tile in the random position

                if (!t.isBomb) // If it's not already a bomb
                {
                    t.isBomb = true; // Mark it as bomb
                    bombSet = true;
                    bombs[i] = t; // Add this tile as a bomb
                }
            } while (!bombSet);
        }

        // calculate surrounding bombs
        for (int i = 0; i < tiles.length; i++)
        {   for (int j = 0; j < tiles[i].length; j++)
            {
                if (!tiles[i][j].isBomb)
                    tiles[i][j].bombsSurrounding = getSurroundingBombAmount(tiles[i][j]); // Get surrounding bombs if its not a bomb
            }
        }
    }


    public void onUpdate() {}

    /**
     * Gets the number of bombs surrounding this tile
     * @param tile A reference to the tile to check
     * @return The number of bombs surrounding this tile
     */
    private int getSurroundingBombAmount(Tile tile)
    {
        /*
            Checks the squares surrounding the one selected (X) for bombs and increases a counter.
            Uses X & Y increments to get the surrounding squares.

            [ ][ ][A]  A: xIncrement = 1, yIncrement = 1
            [ ][X][ ]
            [ ][B][ ]  B: xIncrement = 0, yIncrement = -1
         */

        int bombCount = 0; // Number of bombs surrounding the tile

        // Go through each neighbour
        for (int x = -1; x <= 1; x++)
        {
            for (int y = -1; y <= 1; y++)
            {
                if (x == 0 && y == 0) continue; // Continue if it's the current tile

                int xPos = (int) tile.coordinate.x + x; // Neighbour's tile X & Y position
                int yPos = (int) tile.coordinate.y + y; //

                if (xPos < 0 || xPos >= gridSize.x || // Is the neighbour out of bounds
                    yPos < 0 || yPos >= gridSize.y)
                    continue;

                if (tiles[xPos][yPos].isBomb) // Increase bomb counter if it's a bomb
                    bombCount++;
            }
        }

        return bombCount;
    }

    /**
     * Gives a reference to the tile to the caller
     * @param position The position on a matrix of the clicked tile
     * @return The type of the tile: Safe, Bomb, or Clicked
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public Tile getTileInfo(Vector position)
    {
        // Throw error if tile is out of bounds
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");;

        return tiles[(int) position.x][(int) position.y]; // Return tile info
    }

    /**
     * Provides the type of tile and does any action related to the tile
     * @param position The position on a matrix of the clicked tile
     * @return The type of the tile: Safe, Bomb, or Clicked
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public void clickTile(Vector position)
    {
        // Throw error if tile is out of bounds
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");

        tiles[(int) position.x][(int) position.y].isFlagged = false;

        if (tiles[(int) position.x][(int) position.y].isBomb) { // If is a bomb...
            // Do bomb things (lose game)
            tiles[(int) position.x][(int) position.y].isShown = true;
        }
        else {  // if is regular tile...

            // Start flood fill is it's not flagged
            if (!tiles[(int) position.x][(int) position.y].isFlagged)
                floodFill(position);
        }
    }

    /**
     * Toggles the flagged state of the tile
     * @param position The position on a matrix of the clicked tile
     * @throws IndexOutOfBoundsException If the tile is not inside the board
     */
    public void flagTile(Vector position)
    {
        // Throw error if tile is out of bounds
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");

        var tile = tiles[(int) position.x][(int) position.y];

        if (tile.isShown) return; // Return if shown already

        // Toggle flagged state
        if (tile.isFlagged)
        {
            tile.isFlagged = false;
            flagAmount--;

            if (tile.isBomb)
                correctAmount--;
        } else {
            if (flagAmount >= bombAmount) return; // Dont let more flags than bombs be placed

            tile.isFlagged = true;
            flagAmount++;

            if (tile.isBomb)
                correctAmount++;
        }
    }

    /**
     * Recursively fills all tiles marked as having 0 bomb neighbours
     * @param position The position on a matrix of the clicked tile
     * @exception IndexOutOfBoundsException If the tile is not inside the board
     */
    public void floodFill(Vector position)
    {
        // Throw error if tile is out of bounds
        if (position.x >= tiles.length && position.y >= tiles[0].length) throw new IndexOutOfBoundsException("The tile provided is out of bounds of the tile list");

        /*
        Start
            Mark tile as shown

            If surrounding bombs = 0:
                Get all direct neighbours in an array
                Recursively call this method for each of the neighbours
         End
         */

        Tile thisTile = tiles[(int) position.x][(int) position.y]; // The tile in the current position

        // Mark tile as shown if not flagged or shown, else return
        if (thisTile.isShown || thisTile.isFlagged) return;
        else thisTile.isShown = true;

        if (thisTile.bombsSurrounding == 0) // If the tile doesn't have bomb neighbours...
        {
            ArrayList<Tile> tileList = new ArrayList<Tile>(); // Array of neighbour tiles

            // Add neighbours to array
            for (int x = -1; x <= 1; x++)
            {
                for (int y = -1; y <= 1; y++)
                {
                    if (x == 0 && y == 0) continue; // If it's the current tile

                    int xPos = (int) position.x + x; // New tile X & Y Positions
                    int yPos = (int) position.y + y; //

                    if (xPos < 0 || xPos >= gridSize.x || // If it's out of bounds
                        yPos < 0 || yPos >= gridSize.y)
                        continue;

                    tileList.add(tiles[xPos][yPos]); // Add tile to the list
                }
            }

            for (Tile neighbourTile : tileList) // Go through neighbour tiles
            {
                floodFill(neighbourTile.coordinate); // Recursively call this method
            }
        }
    }
}

