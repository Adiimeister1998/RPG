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

    public static Monster getStrongMonster() throws IOException {
        Random rand = new Random();

        int hp = rand.nextInt() % STRONG_DEV + STRONG_PARAM;
        int atk = rand.nextInt() % STRONG_DEV + STRONG_PARAM;
        int def = rand.nextInt() % STRONG_DEV + STRONG_PARAM;

        return new Monster(hp, 1, def, atk);
    }

    public static Monster getWeakMonster() throws IOException {
        Random rand = new Random();

        int hp = rand.nextInt() % WEAK_DEV + WEAK_PARAM;
        int atk = rand.nextInt() % WEAK_DEV + WEAK_PARAM;
        int def = rand.nextInt() % WEAK_DEV + WEAK_PARAM;

        return new Monster(hp, 1, def, atk);
    }
}
