import Utilities.Vector;
import java.util.function.Consumer;

public interface UIElement {
    Vector position = null, size = null;
    String name = null;

    Consumer<Minesweeper> onClick = null;

    void draw(Minesweeper ms);
}
