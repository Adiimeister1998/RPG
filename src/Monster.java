public class Monster {

    private int hp;
    private int damage;
    private int atk;
    private int def;

    public Monster(int hp, int damage, int atk, int def) {
        this.hp = hp;
        this.damage = damage;
        this.atk = atk;
        this.def = def;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }
}
