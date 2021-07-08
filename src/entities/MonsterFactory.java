package entities;

import java.util.Random;

public class MonsterFactory {
    public static final MonsterFactory SINGLETON = new MonsterFactory();

    private MonsterFactory() {

    }

    public Monster getMonster(String classType,int lvl,String name,int roomSizeX,int roomSizeY) {
        Random rand = new Random();

        int hp = 50 + rand.nextInt() % 10;
        int atk = 20 + rand.nextInt() % 5;
        int def = 10 + rand.nextInt() % 3;
        int healQt = 5 + rand.nextInt() % 2;
        int x = Math.abs(rand.nextInt()) % roomSizeX;
        int y = Math.abs(rand.nextInt()) % roomSizeY;


        Monster newMonster= new Monster(name, hp, def, atk, healQt, classType, new Coordinate(x, y));
        newMonster.gainXp(lvl*(lvl-1)*50);
        return newMonster;
    }
}
