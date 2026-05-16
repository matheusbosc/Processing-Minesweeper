/**
 * GameModule:
 * Provides properties and methods for other modules to access
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */

public interface GameModule {

    /**
     * Called once at the start of the game in the setup() method
     */
    public void onStart();

    /**
     * Called every frame in the draw() method (Game loop)
     */
    public void onUpdate();
}
