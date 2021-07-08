package entities;

public class Monster extends Entity {

    public Monster(String name, int hp, int atk, int def, int healQt, String classType, Coordinate coordinate) {
        super(name, hp, atk, def, healQt, classType, coordinate);
    }


    public int giveXP() {
        return (int) (50*type.getLvl()+100*type.getCoeff()+(hp+atk+def)/10);

    }

    @Override
    public String toString() {
        return super.toString() + " of type: Monster";
    }
}
