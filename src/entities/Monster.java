package entities;

public class Monster extends Entity {
    public Monster(String name, int hp, int atk, int def, int healQt, String classType, Coordinate coordinate) {
        super(name, hp, atk, def, healQt, classType, coordinate);
    }

    @Override
    public String toString() {
        return super.toString() + " of type: Monster";
    }
}
