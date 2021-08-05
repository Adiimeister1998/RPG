package entities;

import java.util.ArrayList;

public class RoomFactory {
    public static Room getStartingRoom(Player player) {
        int sizeX = 5;
        int sizeY = 7;


        Room newRoom = new Room(sizeX, sizeY, player, new Matrix<>(sizeX, sizeY),
                new ArrayList<>(), new ArrayList<>(), true);
        newRoom.resetRoom();
        newRoom.boardRoom();

        return newRoom;
    }
    public static Room getNormalRoom(Player player, ArrayList<Doorway> exits) {
        int sizeX = 9;
        int sizeY = 12;


        Room newRoom = new Room(sizeX, sizeY, player, new Matrix<>(sizeX, sizeY),
                new ArrayList<>(), exits, false);
        newRoom.resetRoom();
        int obstacleCount = (sizeX - 2) * (sizeY - 2) / 5;
        int monsterCount = (sizeX - 2) * (sizeY - 2) / 5;
        newRoom.addObstacles(obstacleCount);
        newRoom.addMonsters(monsterCount);

        return newRoom;
    }
}
