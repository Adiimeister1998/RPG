package classes;

import entities.Entity;

import java.io.IOException;

public class TankClass extends GenericClass {
    private static final int deltaAtk = 1;
    private static final int deltaHP = 5;
    private static final int deltaDef = 5;

    public TankClass(Entity parent) {
        super(parent);
    }

    @Override
    public void gainXP(int xp) {
        this.xp += xp;
        while(this.xp > 100 * this.lvl) {
            this.xp -= 100 * this.lvl;
            lvl += 1;
            parent.levelUp(deltaAtk, deltaHP, deltaDef);
        }
    }

    @Override
    public String toString() {
        return "Tank";
    }
}
