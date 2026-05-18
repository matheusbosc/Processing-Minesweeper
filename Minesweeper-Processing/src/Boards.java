public enum Boards {
    SMALL(new BoardSetting(9,9,10)),
    MEDIUM(new BoardSetting(16,16,40)),
    LARGE(new BoardSetting(24,16,70));

    // Field (variable) to store the description text
    private BoardSetting boardSetting;

    // Constructor (runs once for each constant above)
    Boards(BoardSetting boardSetting) {
        this.boardSetting = boardSetting;
    }

    // Getter method to read the description
    public BoardSetting getDescription() {
        return boardSetting;
    }
}
