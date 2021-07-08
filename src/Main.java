import IO.CommandReader;
import entities.*;
import logger.Logger;

import java.util.*;
import java.util.stream.Collectors;

// TODO: don't waste turn on invalid commands
// TODO: make player not collide with obstacles
// TODO: make player not go outside room
// TODO: make defense work
// TODO: bordarea matricei
public class Main {
    public static void main(String[] args) {
        Logger.createSingleton("test1.txt");

        Player player = new Player("Player", 5000, 50, 30, 500, "DPS", new Coordinate(2, 2));

        Room room = new Room(5, 5, player);
        CommandReader reader = new CommandReader(room);

        System.out.println(room);

        while (player.isAlive() && !room.isCleared()) {
            System.out.println(player);
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
