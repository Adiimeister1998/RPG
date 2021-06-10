package classes;

import entities.Entity;

import java.io.IOException;

public abstract class GenericClass {
    protected Entity parent;
    protected int lvl;
    protected int xp;

    public GenericClass(Entity parent) {
        this.parent = parent;
        lvl = 1;
        xp = 0;
    }

    public abstract void gainXP(int xp);
}

