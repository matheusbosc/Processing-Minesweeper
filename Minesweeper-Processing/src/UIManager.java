import Utilities.Grid;
import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;

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
    private Vector gridOffset = new Vector(50, 50);

    public int uiStartY = 0;

    public Grid grid;
    boolean drawGrid = false;
    boolean isGameOver = false;

    ArrayList<Button> buttons;

    // Colors
    Color bgColor = new Color(0xA0804F);
    Color gridColor = new Color(0x705E4E);
    Color tileColor = new Color(0x67AF4C);
    Color bombColor = new Color(0x292525);
    Color flagColor = new Color(0xD14F4F);
    Color clickedColor = new Color(0xC6A689);

    PImage restartIcon;
    PImage largeIcon;
    PImage smallIcon;

    PImage tileUnclicked, tileFlagged, tileBomb;
    PImage tileClick0, tileClick1, tileClick2, tileClick3, tileClick4, tileClick5, tileClick6, tileClick7, tileClick8;

    PImage buttonLeft,  buttonRight, buttonInBetween;

    // Methods
    public UIManager(Minesweeper _main) {
        main = _main;
    }

    public void onStart() {
        screenSize = new Vector(main.width, main.height);

        // Load Images
        tileUnclicked = main.loadImage("sprites/tileUnclicked.png");
        tileClick0 = main.loadImage("sprites/tileClick0.png");
        tileClick1 = main.loadImage("sprites/tileClick1.png");
        tileClick2 = main.loadImage("sprites/tileClick2.png");
        tileClick3 = main.loadImage("sprites/tileClick3.png");
        tileClick4 = main.loadImage("sprites/tileClick4.png");
        tileClick5 = main.loadImage("sprites/tileClick5.png");
        tileClick6 = main.loadImage("sprites/tileClick6.png");
        tileClick7 = main.loadImage("sprites/tileClick7.png");
        tileClick8 = main.loadImage("sprites/tileClick8.png");
        tileBomb = main.loadImage("sprites/tileBomb.png");
        tileFlagged = main.loadImage("sprites/tileFlag.png");

        buttonLeft = main.loadImage("sprites/buttonLeft.png");
        buttonRight = main.loadImage("sprites/buttonRight.png");
        buttonInBetween = main.loadImage("sprites/buttonInBetween.png");

        int tileSize = (int) ((screenSize.x - (2 * gridOffset.x)) / main.tileManager.gridSize.y);
        grid = new Grid((int) gridOffset.x, (int) (gridOffset.x + tileSize * main.tileManager.gridSize.y), (int) gridOffset.y, (int) (gridOffset.y + tileSize * main.tileManager.gridSize.x));
        grid.tileSize = tileSize;
        grid.gridSize = main.tileManager.gridSize;

        buttons = new ArrayList<>();

        uiStartY = (int) ((2 * gridOffset.y) + (main.tileManager.gridSize.y * tileSize));

        // Restart Button
        Button restartBtn = new Button("Restart", i -> i.restartGame(), buttonLeft, buttonInBetween, buttonRight);
        restartBtn.position = new Vector(gridOffset.x, uiStartY);
        restartBtn.size = new Vector(screenSize.x - (2 * gridOffset.x), 60);
        buttons.add(restartBtn);

        // Small Button
        Button smallButton = new Button("Small", i -> {
            i.setBoardInfo(Boards.SMALL);
            i.restartGame();
        }, buttonLeft, buttonInBetween, buttonRight);
        smallButton.position = new Vector(gridOffset.x, uiStartY + 70);
        smallButton.size = new Vector((screenSize.x - (2 * gridOffset.x)) / 2 - 10, 60);
        buttons.add(smallButton);

        // Large Button
        Button largeButton = new Button("Large", i -> {
            i.setBoardInfo(Boards.MEDIUM);
            i.restartGame();
        }, buttonLeft, buttonInBetween, buttonRight);
        largeButton.position = new Vector(gridOffset.x + ((screenSize.x - (gridOffset.x * 2)) / 2) + 10, uiStartY + 70);
        largeButton.size = new Vector((screenSize.x - (2 * gridOffset.x)) / 2 - 10, 60);
        buttons.add(largeButton);
    }

    public void onUpdate() {

        main.background(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());

        // Display Tiles
        main.rectMode(PApplet.CORNER);
        for (int i = 0; i < grid.gridSize.y; i++) {
            for (int j = 0; j < grid.gridSize.x; j++) {
                var tile = main.tileManager.getTileInfo(new Vector(j, i));

                if (tile.isBomb && tile.isShown) {
                    main.image(tileBomb, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isFlagged) {
                    main.image(tileFlagged, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isShown) {
                    PImage img;

                    switch (tile.bombsSurrounding) {
                        case 1:
                            img = tileClick1;
                            break;
                        case 2:
                            img = tileClick2;
                            break;
                        case 3:
                            img = tileClick3;
                            break;
                        case 4:
                            img = tileClick4;
                            break;
                        case 5:
                            img = tileClick5;
                            break;
                        case 6:
                            img = tileClick6;
                            break;
                        case 7:
                            img = tileClick7;
                            break;
                        case 8:
                            img = tileClick8;
                            break;
                        default:
                            img = tileClick0;
                    }

                    main.image(img, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else {
                    main.image(tileUnclicked, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                }
            }
        }

        // Draw Grid
        if (drawGrid) {
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

        // Draw Buttons
        for (Button button : buttons) {
            button.draw(main);
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
        // TODO: Fix click bounds
        if (mX >= grid.topLeftCoords.x && mX <= grid.bottomRightCoords.x &&
                mY >= grid.topLeftCoords.y && mY <= grid.bottomRightCoords.y && !isGameOver) {
            var tile = main.tileManager.getTileInfo(new Vector((float) Math.floor((mY - grid.topLeftCoords.y) / grid.tileSize), (float) Math.floor((mX - grid.topLeftCoords.x) / grid.tileSize)));

            if (tile != null) {
                if (main.mouseButton == PApplet.RIGHT) {
                    main.tileManager.flagTile(tile.coordinate);
                } else {
                    main.tileManager.clickTile(tile.coordinate);

                    if (tile.isBomb) {
                        isGameOver = true;
                    }
                }
            }

            return;
        }

        // Not in grid... (UI)

        for (var button : buttons) {
            if (mX >= button.position.x && mX <= (button.position.x + button.size.x) &&
                    mY >= button.position.y && mY <= (button.position.y + button.size.y)) {
                button.onClick.accept(main);
                break;
            }
        }
    }
}
