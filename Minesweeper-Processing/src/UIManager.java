import Utilities.Grid;
import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PFont;
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

    PImage background;
    PImage restartIcon;
    PImage largeIcon;
    PImage smallIcon;

    PImage tileUnclicked, tileFlagged, tileBomb;
    PImage tileClick0, tileClick1, tileClick2, tileClick3, tileClick4, tileClick5, tileClick6, tileClick7, tileClick8;

    PImage buttonLeft, buttonRight, buttonInBetween;
    PImage flagIcon;

    PFont font;

    // Methods
    public UIManager(Minesweeper _main) {
        main = _main;
    }

    public void onStart() {
        screenSize = new Vector(main.width, main.height);

        // Load Images
        background = main.loadImage("sprites/BG.png");

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

        flagIcon = main.loadImage("sprites/flagIcon.png");

        font = main.createFont("fonts/Ithaca-LVB75.ttf", 128);

        int tileSize = (int) ((screenSize.x - (2 * gridOffset.x)) / main.tileManager.gridSize.y);
        grid = new Grid((int) gridOffset.x, (int) (gridOffset.x + tileSize * main.tileManager.gridSize.y), (int) gridOffset.y, (int) (gridOffset.y + tileSize * main.tileManager.gridSize.x));
        grid.tileSize = tileSize;
        grid.gridSize = main.tileManager.gridSize;

        buttons = new ArrayList<>();

        uiStartY = (int) ((2 * gridOffset.y) + (main.tileManager.gridSize.y * tileSize));

        var spaceInBetween = (screenSize.x - (2 * gridOffset.x)); // the space in between the 2 offsets on the x axis

        // Restart Button
        Button restartBtn = new Button("Restart", i -> i.restartGame(), buttonLeft, buttonInBetween, buttonRight);
        restartBtn.position = new Vector(gridOffset.x, uiStartY + 70);
        restartBtn.size = new Vector(screenSize.x - (2 * gridOffset.x), 60);
        buttons.add(restartBtn);

        // Small Button
        Button smallButton = new Button("Small", i -> {
            i.setBoardInfo(Boards.SMALL);
            i.restartGame();
        }, buttonLeft, buttonInBetween, buttonRight);
        smallButton.position = new Vector(gridOffset.x, uiStartY);
        smallButton.size = new Vector((spaceInBetween / 2) - 40, 60);
        buttons.add(smallButton);

        // Large Button
        Button largeButton = new Button("Large", i -> {
            i.setBoardInfo(Boards.MEDIUM);
            i.restartGame();
        }, buttonLeft, buttonInBetween, buttonRight);
        largeButton.position = new Vector((spaceInBetween / 2) + 90, uiStartY);
        largeButton.size = new Vector((spaceInBetween / 2) - 40, 60);
        buttons.add(largeButton);

        // Flags Counter
        Button flagCounter = new Button("10", i -> {
        }, buttonLeft, buttonInBetween, buttonRight);
        flagCounter.position = new Vector((screenSize.x / 2) - 40, uiStartY);
        flagCounter.size = new Vector(80, 60);
        flagCounter.name = "flagCounter";
        buttons.add(flagCounter);
    }

    public void onUpdate() {
        main.textFont(font);

        // Draw BG
        for (int currentX = 0; currentX < screenSize.x; currentX += 256) {
            for (int currentY = 0; currentY < screenSize.y; currentY += 256) {
                main.image(background, currentX, currentY, 256, 256);
            }
        }

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

        // Draw Buttons
        for (Button button : buttons) {
            if (button.name.equals("flagCounter")) {
                button.text = (main.tileManager.bombAmount - main.tileManager.flagAmount) + "";
            }

            button.draw(main);
        }

        // Draw Game Over / Win Screen

        // Game Over
        if (isGameOver) {
            main.rectMode(PApplet.CORNER);
            main.fill(0, 0, 0, 170);
            main.rect(gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);

            main.textSize(100);
            main.textAlign(PApplet.CENTER, PApplet.CENTER);
            main.fill(255);
            main.text("Game Over", gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);
        }

        // Has Won
        if (main.tileManager.correctAmount == main.tileManager.bombAmount) {
            main.rectMode(PApplet.CORNER);
            main.fill(0, 0, 0, 170);
            main.rect(gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);
            main.image(flagIcon, gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);

            main.textSize(100);
            main.textAlign(PApplet.CENTER, PApplet.CENTER);
            main.fill(255);
            main.text("Game Won!", gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);
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

            if (main.tileManager.correctAmount == main.tileManager.bombAmount) return;

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
                main.tileManager.clickSfx.play();
                button.onClick.accept(main);
                break;
            }
        }
    }
}
