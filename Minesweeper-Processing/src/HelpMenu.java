import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * HelpMenu:
 * Implements a help menu which can then be shown
 * @author Matheus Boscariol
 * @version 21/5/2026
 */
public class HelpMenu implements UIElement {
    /// Position of the button in pixels
    public Vector position = null;
    /// Size of the button in pixels
    public Vector size = null;
    /// Name of the button (used for references)
    public String name = "helpMenu";
    /// List of buttons that the ui manager can use
    public ArrayList<Button> buttons = null;

    /// Screen BG
    public PImage bg;

    /// Tutorial demonstration
    public PImage tutorialImg;

    /// Icon for close button
    PImage closeButtonIcon;

    /// Closes the help menu
    Button closeBtn;

    /// Is the help menu active
    public boolean isActive = false;

    public HelpMenu(Minesweeper tempMs, Vector pos, Vector size)
    {
        position = pos;
        this.size = size;

        buttons = new ArrayList<>();

        bg = tempMs.loadImage("sprites/helpMenuBG.png");
        closeButtonIcon = tempMs.loadImage("sprites/closeIcon.png");
        tutorialImg = tempMs.loadImage("sprites/tutorialImage.png");

        closeBtn = new Button("", i -> isActive = false, closeButtonIcon);
        closeBtn.position = new Vector(position.x + size.x - (position.y + ((size.y / 10) / 2)), position.y + ((size.y / 10) / 2));
        closeBtn.size = new Vector(size.y / 8, size.y / 8);
        buttons.add(closeBtn);
    }

    /**
     * Draws the ui element and is called every frame
     * @param ms The main processing file
     */
    public void draw(Minesweeper ms) {
        if (!isActive) return; // Return if not active

        ms.fill(255);
        ms.imageMode(PApplet.CORNER);
        ms.image(bg, position.x, position.y, size.x, size.y);

        ms.textAlign(PApplet.CENTER, PApplet.CENTER); // Center the text
        ms.fill(79, 76, 68); // Set text color
        ms.textSize(65); // Set text size
        ms.text("Help", position.x + (size.x / 2), position.y + (size.y / 10)); // Draw text

        closeBtn.draw(ms);

        ms.imageMode(PApplet.CENTER);
        ms.image(tutorialImg, position.x + (size.x / 2), (int)(position.y + (size.y / 2.5)), (int)(size.x / 2.4), (int)(size.x / 2.4));

        String helpMenuText = """
                The number inside the tile is the number of bombs around it
                
                Right click a tile to flag it as a bomb
                Left click a tile to clear it if it isn’t a bomb
                
                Try to not click the bombs to win
                """;

        ms.textAlign(PApplet.CENTER, PApplet.BOTTOM); // Center the text
        ms.fill(79, 76, 68); // Set text color
        ms.textSize(25); // Set text size
        ms.text(helpMenuText, position.x + (size.x / 2), position.y + size.y - 30); // Draw text

        ms.imageMode(PApplet.CORNER);

    }
}
