/**
 * GameModule:
 * Provides properties and methods for other modules to access
 * @author  Matheus Boscariol
 * @version 13/05/2026
 */

public interface GameModule {
    public void onStart(); // Actions to happen on the game start
    public void onStop(); // Actions to happen on the game stop
    public void onUpdate(); // Actions to happen on the game update (game loop)
}
