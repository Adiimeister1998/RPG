import entities.Monster;
import entities.MonsterFactory;
import entities.Player;
import logger.Logger;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Logger.createSingleton("test1.txt");

        Player player = new Player(100, 20, 30, 100, "DPS");

        Monster monster1 = MonsterFactory.SINGLETON.getWeakMonster("Tank");
        Monster monster2 = MonsterFactory.SINGLETON.getWeakMonster("DPS");
        Monster monster3 = MonsterFactory.SINGLETON.getWeakMonster("Healer");
        monster1.gainXp(230);
        monster2.gainXp(301);
        monster3.gainXp(10);

        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);
        monsters.add(monster3);

        while (player.isAlive() && !monsters.isEmpty()) {
            Random random = new Random();
            int monsterIdx = Math.abs(random.nextInt()) % monsters.size();
            int totalAtk = 0;
            for(Monster monster : monsters) {
                totalAtk += monster.getAtk();
            }

            if(player.getCurrHp() > totalAtk) {
                player.attack(monsters.get(monsterIdx));
            } else {
                player.heal(player);
            }

            if(!monsters.get(monsterIdx).isAlive()) {
                monsters.remove(monsters.get(monsterIdx));
            }

            for(Monster monster : monsters) {
                monster.attack(player);
            }
        }

        if(player.isAlive()) {
            System.out.printf("Player won with %d hp%n", player.getCurrHp());
        } else {
            System.out.printf("Player lost with %d monsters remaining", monsters.size());
        }

        Logger.getSingleton().closeLogger();
    }
}
