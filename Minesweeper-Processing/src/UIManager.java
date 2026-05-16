import Utilities.Grid;
import Utilities.Vector;
import processing.core.PApplet;

import java.awt.*;

//TODO: Add the UI buttons and settings
//TODO: Add lose conditions
//TODO: Add better textures
//TODO: Add sounds

/**
 * UIManager:
 * Draw UI, handle
 *
 * @author Matheus Boscariol
 * @version 15/05/2026
 */
public class UIManager implements GameModule {
    // Variables
    Minesweeper main; // Reference to the main class (all calls to processing methods must be done through this)
    public Vector screenSize; // Size of the screen (in pixels)

    public Grid grid;
    boolean drawGrid = false;

    // Colors
    Color bgColor = new Color(0x727F83);
    Color gridColor = new Color(0x705E4E);
    Color tileColor = new Color(0x67AF4C);
    Color bombColor = new Color(0x292525);
    Color flagColor = new Color(0xD14F4F);
    Color clickedColor = new Color(0xC6A689);

    // Methods
    public UIManager(Minesweeper _main) {
        main = _main;
    }

    public void onStart() {
        screenSize = new Vector(main.width, main.height);
        grid = new Grid(100, 700, 50, 800);
        grid.tileSize = 75;
        grid.gridSize = main.tileManager.gridSize;
    }

    public void onUpdate() {
        main.background(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());

        // Display Board BG
        main.fill(gridColor.getRed(), gridColor.getGreen(), gridColor.getBlue());
        main.rectMode(PApplet.CORNERS);
        main.rect(grid.topLeftCoords.x, grid.topLeftCoords.y, grid.bottomRightCoords.x, grid.bottomRightCoords.y);

        // Display Tiles
        main.rectMode(PApplet.CORNER);
        for (int i = 0; i < grid.gridSize.y; i++) {
            for (int j = 0; j < grid.gridSize.x; j++) {
                var tile = main.tileManager.getTileInfo(new Vector(j, i));

                if (tile.isBomb && tile.isShown) {
                    main.fill(bombColor.getRed(), bombColor.getGreen(), bombColor.getBlue());
                    main.rect(grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isFlagged)
                {
                    main.fill(flagColor.getRed(), flagColor.getGreen(), flagColor.getBlue());
                    main.rect(grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isShown)
                {
                    main.fill(clickedColor.getRed(), clickedColor.getGreen(), clickedColor.getBlue());
                    main.rect(grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);

                    main.fill(0);
                    main.textSize(20);
                    main.text(""+tile.bombsSurrounding, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.topLeftCoords.x + (i * grid.tileSize) + grid.tileSize, grid.topLeftCoords.y + (j * grid.tileSize) + grid.tileSize);
                } else {
                    main.fill(tileColor.getRed(), tileColor.getGreen(), tileColor.getBlue());
                    main.rect(grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);

                   /* main.fill(0);
                    main.textSize(20);
                    main.text(""+tile.bombsSurrounding, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.topLeftCoords.x + (i * grid.tileSize) + grid.tileSize, grid.topLeftCoords.y + (j * grid.tileSize) + grid.tileSize);
                */}
            }
        }

        // Draw Grid
        if(drawGrid) {
            main.strokeWeight(2);
            main.stroke(gridColor.getRed(), gridColor.getGreen(), gridColor.getBlue());

            for (int i = 0; i < grid.gridSize.y + 1; i++) // Vertical Lines
            {
                main.line((grid.tileSize * i) + grid.topLeftCoords.x, grid.topLeftCoords.y, (grid.tileSize * i) + grid.topLeftCoords.x, grid.bottomRightCoords.y);
            }

            for (int i = 0; i < grid.gridSize.x + 1; i++) // Horizontal Lines
            {
                main.line(grid.topLeftCoords.x, (grid.tileSize * i) + grid.topLeftCoords.y, grid.bottomRightCoords.x, (grid.tileSize * i) + grid.topLeftCoords.y);
            }
        }
    }

    /**
     * Actions to performed when the mouse is clicked
     *
     * @param mX The X mouse position when clicked
     * @param mY The Y mouse position when clicked
     */
    public void onClick(int mX, int mY) {
        // Is within grid...
        if (mX >= grid.topLeftCoords.x && mX <= grid.bottomRightCoords.x &&
                mY >= grid.topLeftCoords.y && mY <= grid.bottomRightCoords.y) {
            var tile = main.tileManager.getTileInfo(new Vector((float) Math.floor((mY - grid.topLeftCoords.y) / grid.tileSize), (float) Math.floor((mX - grid.topLeftCoords.x) / grid.tileSize)));

            if (tile != null) {
                if (main.mouseButton == PApplet.RIGHT) {
                    main.tileManager.flagTile(tile.coordinate);
                } else {
                    main.tileManager.clickTile(tile.coordinate);
                }
            }

            return;
        }

        // Not in grid... (UI)
    }
}
