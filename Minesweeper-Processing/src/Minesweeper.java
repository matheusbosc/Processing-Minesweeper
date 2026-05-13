import processing.core.PApplet;
import java.util.ArrayList;

public class Minesweeper extends PApplet
{
    public void setup() {

    }

    public void draw()
    {

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


