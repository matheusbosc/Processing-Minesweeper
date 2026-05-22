import Utilities.Vector;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * UIElement:
 * An element that is part of the UI (like a button or label)
 * @author Matheus Boscariol
 * @version 17/5/2026
 */
public interface UIElement {
    /// Position of the button in pixels
    public Vector position = null;
    /// Size of the button in pixels
    public Vector size = null;
    /// Name of the button (used for references)
    public String name = "";
    /// List of buttons that the ui manager can use
    public ArrayList<Button> buttons = null;

    /**
     * Draws the ui element and is called every frame
     * @param ms The main processing file
     */
    void draw(Minesweeper ms);
}
