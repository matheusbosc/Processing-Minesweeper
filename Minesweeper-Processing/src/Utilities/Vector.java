/**
 * Utilities.Vector:
 * Used to store a point on a 2D grid. Replaces processing's PVector class, which uses int instead of float
 * @author  Matheus Boscariol
 * @version 14/05/2026
 */

package Utilities;
import processing.core.PApplet;

import java.util.Random;

public class Vector {
    public float x, y;

    public Vector(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    public static Vector RandomVector(float minX, float maxX, float minY, float maxY)
    {
        Random random = new Random();

        float _x = random.nextFloat(maxX - minX) + minX;
        float _y = random.nextFloat(maxY - minY) + minY;

        return new Vector(_x, _y);
    }
}
