package entities;

import classes.GenericClass;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private boolean isCleared;
    private int sizeX;
    private int sizeY;
    private Matrix<Integer> layout;
    private ArrayList<Monster> monsters;
    private Player player;

    public Room(int sizeX, int sizeY, Player player) {
        this.sizeX = sizeX + 2;
        this.sizeY = sizeY + 2;
        this.player = player;
        this.layout = new Matrix<>(sizeX+2, sizeY+2);
        this.monsters = new ArrayList<>();
        this.isCleared = false;

        Random rand = new Random();
        // temporary
        int volume = (this.sizeX - 2) * (this.sizeY - 2);
        int obstacleCnt = volume * 20 / 100;
        int mobCnt = volume * 20 / 100;

        for (int y=0;y<this.sizeY;y++){
            layout.add(0,y,-1);
            layout.add(this.sizeX-1,y,-1);

        }
        for (int x=0;x<this.sizeX;x++){
            layout.add(x,0,-1);
            layout.add(x,this.sizeY-1,-1);
        }

        layout.add(player.getCoord().getX(), player.getCoord().getY(), -2);

        while(obstacleCnt > 0) {
            int x = Math.abs(rand.nextInt()) % this.sizeX;
            int y = Math.abs(rand.nextInt()) % this.sizeY;
            if(layout.isEmptyCell(x, y)) {
                layout.add(x, y, -1);
                obstacleCnt--;
            }
        }
        int idx = 0;
        while (mobCnt > 0) {
            Monster newMonster = MonsterFactory.SINGLETON.getMonster(GenericClass.getRandomClassType(), 1, "Monster#" + idx, sizeX, sizeY);
            if(layout.isEmptyCell(newMonster.getCoord().getX(), newMonster.getCoord().getY())) {
                layout.add(newMonster.getCoord().getX(), newMonster.getCoord().getY(), idx);
                monsters.add(newMonster);
                mobCnt--;
                idx++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-".repeat(Math.max(0, 2 * sizeY + 1)));
        builder.append("\n");
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(layout.isEmptyCell(i, j)) {
                    builder.append("| ");
                } else if(layout.get(i, j).equals(-1)) {
                    builder.append("|#");
                }  else if(layout.get(i, j).equals(-2)) {
                    builder.append("|P");
                } else {
                    builder.append("|");
                    builder.append(layout.get(i, j));
                }
            }
            builder.append("|\n");
            builder.append("-".repeat(Math.max(0, 2 * sizeY + 1)));
            builder.append("\n");
        }
        return builder.toString();
    }

    public boolean isCleared() {
        return isCleared;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public Player getPlayer() {
        return player;
    }

    public Matrix<Integer> getLayout() {
        return layout;
    }

    public void setCleared(boolean cleared) {
        isCleared = cleared;
    }

    public void executeRound() {
        // print killed monsters & erase dead monsters
        ArrayList<Monster> deadMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if (!monster.isAlive()) {
                System.out.printf("%s has died!\n", monster.getName());
                deadMonsters.add(monster);
                player.gainXp(monster.giveXP());
            }
        }
        for (Monster monster : deadMonsters) {
            monsters.remove(monster);
            layout.remove(monster.getCoord().getX(), monster.getCoord().getY());
        }
        deadMonsters.clear();


        // monsters attack player
        for (Monster monster : monsters) {
            if (monster.getCoord().getDistance(player.getCoord()) <= 2)
                monster.attack(player);
        }

        if(monsters.isEmpty()) {
            isCleared = true;
        }

    }
}
