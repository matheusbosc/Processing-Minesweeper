/**
 * Minesweeper:
 * A fun game where you have to discover where all the bombs are in a field.
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */

import processing.core.PApplet;
import java.util.ArrayList;

public class Minesweeper extends PApplet
{
    TileManager tileManager; // Reference to the tile manager


    public void setup() {
        tileManager = new TileManager(10, 8); // Initialize tile manager (and set size)
        tileManager.onStart(); // Call the start method on the tile manager
    }

    public void draw()
    {
        tileManager.onUpdate(); // Call the update method on the tile manager
    }

    public void settings() { size(800, 900); }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "Minesweeper" };
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}


