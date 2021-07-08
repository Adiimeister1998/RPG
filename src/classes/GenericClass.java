package classes;

import entities.Entity;

import java.io.IOException;

public abstract class GenericClass {
    protected Entity parent;
    protected int lvl;
    protected int xp;
    protected double coeff;

    public int getLvl() {
        return lvl;
    }

    public int getXp() {
        return xp;
    }

    public int neededXP(){
        return 100*lvl-xp;
    }

    public double getCoeff() {
        return coeff;
    }

    public GenericClass(Entity parent, double coeff) {
        this.parent = parent;
        this.coeff = coeff;
        lvl = 1;
        xp = 0;
    }

    public abstract void gainXP(int xp);
}

