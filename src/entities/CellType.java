package entities;

public enum CellType {
    MONSTER(),
    OBSTACLE(),
    DOORWAY(),
    PLAYER();

    private int index;

    CellType() {
        index = -1;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
