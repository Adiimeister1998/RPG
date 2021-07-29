package entities;

public class Doorway {
    private Coordinate coord;
    private Room parent;
    private Doorway neighbour;

    public Doorway(Coordinate coord, Room parent) {
        this.coord = coord;
        this.parent = parent;
    }

    public void setNeighbour(Doorway neighbour) {
        this.neighbour = neighbour;
    }

    public Room getNextRoom() {
        return this.neighbour.parent;
    }

    public Coordinate getNextCoord() {
        return this.neighbour.coord;
    }

    public Coordinate getCoord() {
        return coord;
    }
}
