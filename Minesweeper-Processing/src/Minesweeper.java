/**
 * Minesweeper:
 * A fun game where you have to discover where all the bombs are in a field.
 *
 * @author Matheus Boscariol
 * @version 13/05/2026
 */

import Utilities.Vector;
import processing.core.PApplet;

import java.util.ArrayList;

public class Minesweeper extends PApplet {
    public TileManager tileManager; // Reference to the tile manager
    public UIManager uiManager; // Reference to the UI manager

    private Boards boardInfo = Boards.SMALL;
    private Vector gridSize = new Vector(16, 16);
    private int bombs = 40;

    public void setup() {
        restartGame();
    }

    public void draw() {
        tileManager.onUpdate(); // Call the update method on the tile manager
        uiManager.onUpdate();
    }

    public void mousePressed() {
        uiManager.onClick(mouseX, mouseY);
    }

    public void restartGame() {
        BoardSetting settings = boardInfo.getDescription();

        tileManager = new TileManager(this, settings.gridX, settings.gridY, settings.bombAmount); // Initialize tile manager (and set size)
        tileManager.onStart(); // Call the start method on the tile manager

        uiManager = new UIManager(this); // Initialize UI manager
        uiManager.onStart(); // Call the start method on the ui manager
    }

    public void setBoardInfo(Boards boardInfo) {
        this.boardInfo = boardInfo;
    }

    public void settings() {
        size(700, 850);
        noSmooth();
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Minesweeper"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}


