package entities;

import classes.DPSClass;
import classes.GenericClass;
import classes.HealerClass;
import classes.TankClass;
import logger.Logger;
import logger.LoggerType;

public class Entity {
    protected int hp;
    protected int atk;
    protected int def;
    protected int healQt;
    protected int currHp;
    protected GenericClass type;
    private boolean isAlive;

    public Entity(int hp, int atk, int def, int healQt, String classType) {
        this.isAlive = true;
        this.hp = hp;
        this.currHp = hp;
        this.atk = atk;
        this.def = def;
        this.healQt = healQt;
        switch (classType) {
            case "Tank" -> this.type = new TankClass(this);
            case "DPS" -> this.type = new DPSClass(this);
            case "Healer" -> this.type = new HealerClass(this);
            default -> this.type = new TankClass(this);
        }
    }

    public int getCurrHp() {
        return currHp;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public void setCurrHp(int currHp) {
        this.currHp = currHp;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void levelUp(int deltaAtk, int deltaHP, int deltaDef)  {
        hp += deltaHP;
        atk += deltaAtk;
        def += deltaDef;
        Logger.getSingleton().log(String.format("%s leveled up with stats: %d %d %d.\n",
                this.toString(), deltaAtk, deltaHP, deltaDef), LoggerType.INFO);
    }

    public void gainXp(int xp)  {
        Logger.getSingleton().log(String.format("%s got %d xp.\n", this.toString(), xp), LoggerType.INFO);
        this.type.gainXP(xp);
    }

    private void updateState() {
        if(this.currHp < 0) {
            this.isAlive = false;
        }
    }

    public void attack(Entity entity) {
        entity.setCurrHp(entity.getCurrHp() - this.atk);
        entity.updateState();
    }

    public void heal(Entity entity) {
        entity.setCurrHp(entity.getCurrHp() + this.healQt);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", currHp=" + currHp +
                ", type=" + type +
                '}';
    }
}
