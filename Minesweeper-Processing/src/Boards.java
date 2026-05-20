/**
 * Boards.java:
 * An enumerator for different board settings
 * @author Matheus Boscariol
 * @version 17/5/2026
 */
public enum Boards {
    // Different board sizes
    SMALL(new BoardSetting(9,9,10)),
    MEDIUM(new BoardSetting(16,16,40)),
    LARGE(new BoardSetting(24,16,70));

    /// Field to store the description text
    private BoardSetting boardSetting;

    /**
     * Sets <code>boardSetting</code> based on the setting selected
     * @param boardSetting Setting to assign
     */
    Boards(BoardSetting boardSetting) {
        this.boardSetting = boardSetting;
    }

    /**
     * Getter method for board settings
     * @return <code>BoardSetting</code> attached to the enum
     */
    public BoardSetting getDescription() {
        return boardSetting;
    }
}
