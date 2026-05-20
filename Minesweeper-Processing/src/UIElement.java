import Utilities.Vector;
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

    /// Function to call on click (passes in the main class, <code>{@link Minesweeper}</code>)
    Consumer<Minesweeper> onClick = null;

    void draw(Minesweeper ms);
}
