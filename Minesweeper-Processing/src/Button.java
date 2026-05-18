import Utilities.Vector;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.function.Consumer;

public class Button implements UIElement {
    public Vector position = null, size = null;
    public String name = "btn";

    public Consumer<Minesweeper> onClick = null;

    public String text;
    public Color fill = new Color(0xFFFFFF), stroke = new Color(0x000000), textColor = new Color(0x000000);

    public PImage left, inBetween, right = null;


    public Button(String text, Consumer<Minesweeper> onClick, PImage left, PImage inBetween, PImage right) {
        this.text = text;
        this.onClick = onClick;
        this.left = left;
        this.inBetween = inBetween;
        this.right = right;
    }

    public void draw(Minesweeper ms) {
        ms.rectMode(PApplet.CORNER);
        ms.fill(fill.getRed(), fill.getGreen(), fill.getBlue());
        ms.stroke(stroke.getRed(), stroke.getGreen(), stroke.getBlue());
        ms.image(left, position.x, position.y, size.y, size.y);
        ms.image(inBetween, position.x + size.y, position.y, size.x - (2 * size.y), size.y);
        ms.image(right, position.x + (size.x - size.y), position.y, size.y, size.y);

        ms.textAlign(PApplet.CENTER, PApplet.CENTER);
        ms.fill(textColor.getRed(), textColor.getGreen(), textColor.getBlue());
        ms.textSize(40);
        ms.text(text, position.x + (size.x / 2), position.y + (size.y / 2));
    }
}
