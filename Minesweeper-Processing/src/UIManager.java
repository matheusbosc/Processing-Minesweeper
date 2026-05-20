import Utilities.Grid;
import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * UIManager:
 * Draw UI, handle
 *
 * @author Matheus Boscariol
 * @version 15/05/2026
 */
public class UIManager implements GameModule {

    /// Reference to the main class (all calls to processing methods must be done through this)
    Minesweeper main;
    /// Size of the screen (in pixels)
    public Vector screenSize;
    /// Pixel offset from the top left corner
    private Vector gridOffset = new Vector(50, 50);

    /// How far down does the UI (Buttons, text, not the board itself) starts
    public int uiStartY = 0;

    /// The grid used by the game
    public Grid grid;
    /// Has the game been lost
    boolean isGameOver = false;

    /// List of buttons
    ArrayList<Button> buttons;

    /// Background image
    PImage background;

    /// Special tile images
    PImage tileUnclicked, tileFlagged, tileBomb;
    /// Clicked tile images
    PImage tileClick0, tileClick1, tileClick2, tileClick3, tileClick4, tileClick5, tileClick6, tileClick7, tileClick8;

    /// Button images
    PImage buttonLeft, buttonRight, buttonInBetween;
    /// Flag icon
    PImage flagIcon;

    /// Game font
    PFont font;


    public UIManager(Minesweeper _main) {
        main = _main;
    }

    public void onStart() {
        screenSize = new Vector(main.width, main.height);

        // Load external resources
        background = main.loadImage("sprites/BG.png");

        // Tile images
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

        // Button images
        buttonLeft = main.loadImage("sprites/buttonLeft.png");
        buttonRight = main.loadImage("sprites/buttonRight.png");
        buttonInBetween = main.loadImage("sprites/buttonInBetween.png");

        // Flag icon
        flagIcon = main.loadImage("sprites/flagIcon.png");

        // Font
        font = main.createFont("fonts/Ithaca-LVB75.ttf", 128);


        int tileSize = (int) ((screenSize.x - (2 * gridOffset.x)) / main.tileManager.gridSize.y); // Calculate tile size

        // Create grid and configure it
        grid = new Grid((int) gridOffset.x, (int) (gridOffset.x + tileSize * main.tileManager.gridSize.y), (int) gridOffset.y, (int) (gridOffset.y + tileSize * main.tileManager.gridSize.x));
        grid.tileSize = tileSize;
        grid.gridSize = main.tileManager.gridSize;

        // Initialize button array
        buttons = new ArrayList<>();

        // Calculate UI start
        uiStartY = (int) ((2 * gridOffset.y) + (main.tileManager.gridSize.y * tileSize));

        // Calculate the space in between the left and right offsets
        var spaceInBetween = (screenSize.x - (2 * gridOffset.x));

        // Restart Button
        Button restartBtn = new Button("Restart", i -> i.startGame(), buttonLeft, buttonInBetween, buttonRight);
        restartBtn.position = new Vector(gridOffset.x, uiStartY + 70);
        restartBtn.size = new Vector(screenSize.x - (2 * gridOffset.x), 60);
        buttons.add(restartBtn);

        // Small Button
        Button smallButton = new Button("Small", i -> {
            i.setBoardInfo(Boards.SMALL); // Set board info
            i.startGame(); // restart game
        }, buttonLeft, buttonInBetween, buttonRight);
        smallButton.position = new Vector(gridOffset.x, uiStartY);
        smallButton.size = new Vector((spaceInBetween / 2) - 40, 60);
        buttons.add(smallButton);

        // Large Button
        Button largeButton = new Button("Large", i -> {
            i.setBoardInfo(Boards.MEDIUM); // Set board info
            i.startGame(); // Restart game
        }, buttonLeft, buttonInBetween, buttonRight);
        largeButton.position = new Vector((spaceInBetween / 2) + 90, uiStartY);
        largeButton.size = new Vector((spaceInBetween / 2) - 40, 60);
        buttons.add(largeButton);

        // Flags Counter
        Button flagCounter = new Button("10", i -> {} /*No action to do*/, buttonLeft, buttonInBetween, buttonRight);
        flagCounter.position = new Vector((screenSize.x / 2) - 40, uiStartY);
        flagCounter.size = new Vector(80, 60);
        flagCounter.name = "flagCounter";
        buttons.add(flagCounter);
    }

    public void onUpdate() {
        main.textFont(font); // Set font

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
                    // Set bomb tile if its shown and a bomb
                    main.image(tileBomb, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isFlagged) {
                    // Set flag tile if its flagged
                    main.image(tileFlagged, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else if (tile.isShown) {
                    // Get correct tile for the surrounding bomb amount
                    PImage img = switch (tile.bombsSurrounding) {
                        case 1 -> tileClick1;
                        case 2 -> tileClick2;
                        case 3 -> tileClick3;
                        case 4 -> tileClick4;
                        case 5 -> tileClick5;
                        case 6 -> tileClick6;
                        case 7 -> tileClick7;
                        case 8 -> tileClick8;
                        default -> tileClick0;
                    };

                    // Set tile image
                    main.image(img, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                } else {
                    // Set unclick tile image
                    main.image(tileUnclicked, grid.topLeftCoords.x + (i * grid.tileSize), grid.topLeftCoords.y + (j * grid.tileSize), grid.tileSize, grid.tileSize);
                }
            }
        }

        // Draw Buttons
        for (Button button : buttons) {
            // If it's the flag counter, update the text
            if (button.name.equals("flagCounter")) {
                button.text = (main.tileManager.bombAmount - main.tileManager.flagAmount) + "";
            }

            // Draw button
            button.draw(main);
        }

        // Draw Game Over / Win Screen

        // Game Over
        if (isGameOver) {
            // Draw black overlay
            main.rectMode(PApplet.CORNER);
            main.fill(0, 0, 0, 170);
            main.rect(gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);

            // Draw text
            main.textSize(100);
            main.textAlign(PApplet.CENTER, PApplet.CENTER);
            main.fill(255);
            main.text("Game Over", gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);
        }

        // Has Won
        if (main.tileManager.correctAmount == main.tileManager.bombAmount) {
            // Draw black overlay and flag icon
            main.rectMode(PApplet.CORNER);
            main.fill(0, 0, 0, 170);
            main.rect(gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);
            main.image(flagIcon, gridOffset.x, gridOffset.y, grid.gridSize.x * grid.tileSize, grid.gridSize.y * grid.tileSize);

            // Draw text
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
        if (mX >= grid.topLeftCoords.x && mX <= grid.bottomRightCoords.x &&
                mY >= grid.topLeftCoords.y && mY <= grid.bottomRightCoords.y && !isGameOver) {

            // Return if the player has won
            if (main.tileManager.correctAmount == main.tileManager.bombAmount) return;

            // Get the tile for the calculated position
            var tile = main.tileManager.getTileInfo(new Vector((float) Math.floor((mY - grid.topLeftCoords.y) / grid.tileSize),
                                                               (float) Math.floor((mX - grid.topLeftCoords.x) / grid.tileSize)));

            if (tile != null) {
                if (main.mouseButton == PApplet.RIGHT) {
                    main.tileManager.flagTile(tile.coordinate); // Flag tile on right click
                } else {
                    main.tileManager.clickTile(tile.coordinate); // Click tile on left click

                    if (tile.isBomb && !tile.isFlagged) {
                        isGameOver = true; // Lose game if it's not flagged and it's a bomb
                    }
                }
            }

            return;
        }

        // Not in grid... (UI)

        // Call onClick for the first button that is inside the bounds
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
