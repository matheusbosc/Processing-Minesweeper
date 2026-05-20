package Utilities;

import java.util.Random;

/**
 * Utilities.Vector:
 * Used to store a point on a 2D grid. Replaces processing's PVector class, which uses int instead of float
 * @author  Matheus Boscariol
 * @version 14/05/2026
 */
public class Vector {
    /// X position of the vector
    public float x;
    /// Y position of the vector
    public float y;

    public Vector(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    /**
     * Generate a random vector inside a range
     * @param minX Minimum X position
     * @param maxX Maximum X position
     * @param minY Minimum Y position
     * @param maxY Maximum Y position
     * @return Randomly generated vector inside the range
     */
    public static Vector RandomVector(float minX, float maxX, float minY, float maxY)
    {
        Random random = new Random(); // Initialize randomizer

        float _x = random.nextFloat(maxX - minX) + minX; // Generate x
        float _y = random.nextFloat(maxY - minY) + minY; // Generate y

        return new Vector(_x, _y);
    }

    /**
     * Override for the toString() method
     * @return A string version of the method
     */
    @Override
    public String toString()
    {
        return x + ", " + y;
    }
}
