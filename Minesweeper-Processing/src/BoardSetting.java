/**
 * BoardSetting:
 * Information about the board
 * @author Matheus Boscariol
 * @version 17/5/2026
 */
public class BoardSetting {
    /// Horizontal amount of tiles in the grid
    public int gridX;
    /// Vertical amount of tiles in the grid
    public int gridY;
    /// Total bomb amount in the board
    public int bombAmount;

    BoardSetting(int gridX, int gridY, int bombAmount) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.bombAmount = bombAmount;
    }
}
