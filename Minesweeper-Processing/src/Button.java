import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Button:
 * Implements a button in the UI
 * @author Matheus Boscariol
 * @version 17/5/2026
 */
public class Button implements UIElement {
    /// Position of the button in pixels
    public Vector position = null;
    /// Size of the button in pixels
    public Vector size = null;
    /// Name of the button (used for references)
    public String name = "btn";
    /// List of buttons that the ui manager can use
    public ArrayList<Button> buttons = null;

    /// Function to call on click (passes in the main class, <code>{@link Minesweeper}</code>)
    public Consumer<Minesweeper> onClick = null;

    /// Button text
    public String text;
    /// Text color
    public Color textColor = new Color(0x000000);

    /// Sliced button sprites
    public PImage left, inBetween, right = null;

    /**
     * Initializes the button to split the sprite into 3 (prevents stretching)
     * @param text Button text
     * @param onClick Action to do on click
     * @param left Left icon
     * @param inBetween In between icon (stretches)
     * @param right Right icon
     */
    public Button(String text, Consumer<Minesweeper> onClick, PImage left, PImage inBetween, PImage right) {
        this.text = text;
        this.onClick = onClick;
        this.left = left;
        this.inBetween = inBetween;
        this.right = right;
    }

    /**
     * Initializes the button to use a single sprite
     * @param text Button text
     * @param onClick Action to do on click
     * @param icon Background icon
     */
    public Button(String text, Consumer<Minesweeper> onClick, PImage icon) {
        this.text = text;
        this.onClick = onClick;
        this.left = icon;
        this.inBetween = null;
        this.right = null;
    }

    /**
     * Draws the ui element and is called every frame
     * @param ms The main processing file
     */
    public void draw(Minesweeper ms) {
        ms.rectMode(PApplet.CORNER);

        if (inBetween == null && right == null) // Uses 1 icon
        {
            ms.image(left, position.x, position.y, size.x, size.y); // Draw left image
        } else { // Uses 3 icons
            ms.image(left, position.x, position.y, size.y, size.y); // Draw left image
            ms.image(inBetween, position.x + size.y, position.y, size.x - (2 * size.y), size.y); // Draw middle image
            ms.image(right, position.x + (size.x - size.y), position.y, size.y, size.y); // Draw right image
        }

        ms.textAlign(PApplet.CENTER, PApplet.CENTER); // Center the text
        ms.fill(textColor.getRed(), textColor.getGreen(), textColor.getBlue()); // Set text color
        ms.textSize(40); // Set text size
        ms.text(text, position.x + (size.x / 2), position.y + (size.y / 2)); // Draw text
    }
}
