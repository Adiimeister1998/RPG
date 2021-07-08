package classes;

import entities.Entity;

import java.io.IOException;

public class DPSClass extends GenericClass {
    private static final int deltaAtk = 5;
    private static final int deltaHP = 3;
    private static final int deltaDef = 2;

    public DPSClass(Entity parent) {
        super(parent,0.5);
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
        return "DPS";
    }
}
