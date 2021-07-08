package classes;

import entities.Entity;

import java.io.IOException;
import java.util.Random;

public abstract class GenericClass {
    protected Entity parent;
    protected int lvl;
    protected int xp;
    protected double coeff;
    protected static final String[] types = {"Tank", "Healer", "DPS"};

    public static String getRandomClassType() {
        Random rand = new Random();
        int idx = Math.abs(rand.nextInt()) % types.length;
        return types[idx];
    }

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

