import Utilities.Vector;
import processing.core.PApplet;

/**
 * Minesweeper.java:
 * A fun game where you have to discover where all the bombs are in a field.
 *
 * @author Matheus Boscariol
 * @version 13/05/2026
 */
public class Minesweeper extends PApplet {

    /// Reference to the Tile Manager
    public TileManager tileManager;

    /// Reference to the UI Manager
    public UIManager uiManager;

    /// Information about the board (size, bomb amount)
    private Boards boardInfo = Boards.SMALL;

    /// Size of the grid (set by <code>boardInfo</code>)
    private Vector gridSize;

    /// Amount of bombs in the board (set by <code>boardInfo</code>)
    private int bombs;

    /**
     * Initialize processing and game
     */
    public void setup() {
        startGame();
    }

    /**
     * Call the update methods on the managers
     */
    public void draw() {
        tileManager.onUpdate(); // Call the update method on the tile manager
        uiManager.onUpdate();   //
    }

    /**
     * Call the uiManager's on click event
     */
    public void mousePressed() {
        uiManager.onClick(mouseX, mouseY);
    }

    /**
     * Assign and initialize the tile and ui managers
     */
    public void startGame() {
        BoardSetting settings = boardInfo.getDescription();

        tileManager = new TileManager(this, settings.gridX, settings.gridY, settings.bombAmount); // Initialize tile manager (and set size)
        tileManager.onStart(); // Call the start method on the tile manager

        uiManager = new UIManager(this); // Initialize UI manager
        uiManager.onStart(); // Call the start method on the ui manager
    }

    /**
     * Setter for the <code>boardInfo</code> variable
     * @param boardInfo Board information enumerator
     */
    public void setBoardInfo(Boards boardInfo) {
        this.boardInfo = boardInfo;
    }

    /**
     * Method required by processing for configuring applet
     */
    public void settings() {
        // Code in here is usually in the setup() method,
        // but since im not using the processing ide, and IntelliJ instead,
        // I need to put them in here

        size(700, 850);
        noSmooth();
    }

    /**
     * Create the applet and start it
     * @param passedArgs CLI arguments
     */
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"Minesweeper"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}


