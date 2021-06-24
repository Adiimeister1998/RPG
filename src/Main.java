import IO.CommandReader;
import entities.*;
import logger.Logger;

import java.util.*;
import java.util.stream.Collectors;

// TODO: check monster hp vs hp_max
public class Main {
    public static void main(String[] args) {
        Logger.createSingleton("test1.txt");

        Player player = new Player("Hero", 5000, 50, 30, 500, "DPS", new Coordinate(2, 2));

        Monster monster1 = MonsterFactory.SINGLETON.getStrongMonster("Tank");
        Monster monster2 = MonsterFactory.SINGLETON.getWeakMonster("DPS");
        monster1.gainXp(230);
        monster2.gainXp(301);

        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);

        CommandReader reader = new CommandReader(player, monsters);

        while (player.isAlive() && !monsters.isEmpty()) {
            System.out.println(player);
            // player input
            reader.getCommand();


            // print killed monsters & erase dead monsters
            ArrayList<Monster> deadMonsters = new ArrayList<>();
            for (Monster monster : monsters) {
                if (!monster.isAlive()) {
                    System.out.printf("Monster %s is dead!\n", monster.getName());
                    deadMonsters.add(monster);
                }
            }
            for (Monster monster : deadMonsters) {
                monsters.remove(monster);
            }
            deadMonsters.clear();

            // monsters attack player
            for (Monster monster : monsters) {
                monster.attack(player);
            }
        }

        // endgame result
        if (player.isAlive()) {
            System.out.printf("Player won with %d hp%n", player.getCurrHp());
        } else {
            System.out.printf("Player lost with %d monsters remaining", monsters.size());
        }

        Logger.getSingleton().closeLogger();
    }

    //TreeSet<Integer> x;
    //LinkedHashMap
    //LinkedHashSet
}
