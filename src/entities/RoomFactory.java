package entities;

import java.util.ArrayList;

public class RoomFactory {
    public static Room getStartingRoom(Player player) {
        int sizeX = 5;
        int sizeY = 7;


        Room newRoom = new Room(sizeX, sizeY, player, new Matrix<CellType>(sizeX, sizeY),
                new ArrayList<Monster>(), new ArrayList<Doorway>(), true);
        newRoom.resetRoom();
        newRoom.boardRoom();

        return newRoom;
    }
    // INCOMPLETE!!!
    public static Room getNormalRoom(Player player) {
        int sizeX = 11;
        int sizeY = 13;


        Room newRoom = new Room(sizeX, sizeY, player, new Matrix<CellType>(sizeX, sizeY),
                new ArrayList<Monster>(), new ArrayList<Doorway>(), true);
        newRoom.resetRoom();
        newRoom.boardRoom();
        newRoom.addObstacles(25);

        return newRoom;
    }
}
