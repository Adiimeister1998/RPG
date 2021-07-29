import IO.CommandReader;
import entities.*;
import logger.Logger;

import java.util.*;
import java.util.stream.Collectors;



public class Main {
    public static void main(String[] args) {
        Logger.createSingleton("test1.txt");

        Player player = new Player("Player", 100, 25, 10, 20, "DPS", new Coordinate(2, 3));

        Room room = RoomFactory.getNormalRoom(player);
        Doorway exit = new Doorway(new Coordinate(1, 0), room);
        room.addExit(exit);
        CommandReader reader = new CommandReader(room);

        System.out.println(room);

        while (player.isAlive() && !room.isCleared()) {
            // player input
            reader.getCommand();
            room.executeRound();
        }

        // endgame result
        if (player.isAlive()) {
            System.out.printf("Player won with %d hp%n", player.getCurrHp());
        } else {
            System.out.printf("Player lost with %d monsters remaining", room.getMonsters().size());
        }

        Logger.getSingleton().closeLogger();
    }

    //TreeSet<Integer> x;
    //LinkedHashMap
    //LinkedHashSet
}
