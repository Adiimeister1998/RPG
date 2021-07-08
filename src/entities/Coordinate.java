package entities;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate  coord){
        this.x=coord.x;
        this.y=coord.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void addDelta(Coordinate direction) {
        this.x += direction.x;
        this.y += direction.y;
    }

    @Override
    public String toString() {
        return "(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }

    public int getDistance(Coordinate coord) {
        return Math.abs(x - coord.x) + Math.abs(y - coord.y);
    }
}
