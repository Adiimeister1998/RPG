package entities;

public class Cell {
    private CellType type;
    private int index;

    public Cell(CellType type) {
        this.type = type;
        this.index = -1;
    }

    public Cell(CellType type, int index) {
        this.type = type;
        this.index = index;
    }

    public CellType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }
}
