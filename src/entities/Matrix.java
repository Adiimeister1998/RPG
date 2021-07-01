package entities;

import java.util.ArrayList;

public class Matrix<T> {
    private final ArrayList<ArrayList<T>> cells;

    public Matrix(int rows, int columns) {
        cells = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            cells.add(new ArrayList<>());
            for(int j = 0; j < columns; j++) {
                cells.get(i).add(null);
            }
        }
    }

    public boolean isEmptyCell(int x, int y) {
        return this.get(x, y) == null;
    }

    public T get(int x, int y) {
        return cells.get(x).get(y);
    }

    public void add(int x, int y, T val) {
        cells.get(x).set(y, val);
    }

    public void remove(int x, int y) {
        add(x, y, null);
    }
}