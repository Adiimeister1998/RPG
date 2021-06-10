package entities;

import java.io.IOException;
import java.util.Random;

public class MonsterFactory {
    public static final MonsterFactory SINGLETON = new MonsterFactory();
    private static final int STRONG_PARAM = 100;
    private static final int STRONG_DEV = 25;
    private static final int WEAK_PARAM = 20;
    private static final int WEAK_DEV = 5;

    private MonsterFactory() {

    }

    public Monster getStrongMonster(String classType) {
        Random rand = new Random();

        int hp = rand.nextInt() % STRONG_DEV + STRONG_PARAM;
        int atk = rand.nextInt() % STRONG_DEV + STRONG_PARAM;
        int def = rand.nextInt() % STRONG_DEV + STRONG_PARAM;
        int healQt = rand.nextInt() % STRONG_DEV + STRONG_PARAM;

        return new Monster(hp, def, atk, healQt, classType);
    }

    public Monster getWeakMonster(String classType) {
        Random rand = new Random();

        int hp = rand.nextInt() % WEAK_DEV + WEAK_PARAM;
        int atk = rand.nextInt() % WEAK_DEV + WEAK_PARAM;
        int def = rand.nextInt() % WEAK_DEV + WEAK_PARAM;
        int healQt = rand.nextInt() % STRONG_DEV + STRONG_PARAM;

        return new Monster(hp, def, atk, healQt, classType);
    }
}
