package entities;

public class Monster extends Entity {
    public Monster(int hp, int atk, int def, int healQt, String classType) {
        super(hp, atk, def, healQt, classType);
    }

    @Override
    public String toString() {
        return super.toString() + " of type: Monster";
    }
}
