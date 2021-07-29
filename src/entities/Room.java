package entities;

import classes.GenericClass;

import java.util.*;

// TODO LIST
// TODO 0: implement RoomFactory (Start X, Normal, Boss, Treasure, Trap)
// TODO 4: Add Floor Class
// TODO 6: Change win condition to boss defeat
public class Room {
    private boolean isCleared;
    private int sizeX;
    private int sizeY;
    private Matrix<CellType> layout;
    private ArrayList<Monster> monsters;
    private final Matrix<CellType> backupLayout;
    private final ArrayList<Monster> backupMonsters;
    private Player player;
    private ArrayList<Doorway> exits;

    public Room(int sizeX, int sizeY, Player player, Matrix<CellType> layout,
                ArrayList<Monster> monsters, ArrayList<Doorway> exits, boolean isCleared) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.player = player;
        this.layout = layout;
        this.exits = exits;
        this.monsters = monsters;
        this.backupLayout = new Matrix<>(layout);
        this.backupMonsters = new ArrayList<>(monsters);
        this.isCleared = isCleared;
    }

    public void resetRoom() {
        layout = new Matrix<>(backupLayout);
        monsters = new ArrayList<>(backupMonsters);
        layout.add(player.getCoord().getX(), player.getCoord().getY(), CellType.PLAYER);
    }

    public void addExit(Doorway exit) {
        this.exits.add(exit);
        this.layout.add(exit.getCoord().getX(), exit.getCoord().getY(), CellType.DOORWAY);
        this.backupLayout.add(exit.getCoord().getX(), exit.getCoord().getY(), CellType.DOORWAY);
    }

    public void boardRoom() {
        for (int y=0;y<this.sizeY;y++){
            layout.add(0, y, CellType.OBSTACLE);
            layout.add(this.sizeX - 1, y, CellType.OBSTACLE);

        }
        for (int x=0;x<this.sizeX;x++){
            layout.add(x, 0, CellType.OBSTACLE);
            layout.add(x, this.sizeY - 1, CellType.OBSTACLE);
        }
    }

    public boolean checkFullyAccessible() {
        Coordinate currentPosition = null;
        ArrayDeque<Coordinate> toVisit = new ArrayDeque<>();
        TreeSet<Coordinate> visited = new TreeSet<>();
        int totalCells = 0;
        for(int x = 1; x < this.sizeX; x++) {
            for(int y = 1; y < this.sizeY; y++) {
                if(this.layout.isEmptyCell(x, y)) {
                    if(currentPosition == null)
                        currentPosition = new Coordinate(x, y);
                    totalCells++;
                }
            }
        }

        assert currentPosition != null;
        toVisit.addLast(currentPosition);
        Coordinate[] deltas =
                {new Coordinate(0, 1),
                 new Coordinate(0, -1),
                 new Coordinate(-1, 0),
                 new Coordinate(1, 0)};

        while (!toVisit.isEmpty()) {
            currentPosition = toVisit.getFirst();
            toVisit.removeFirst();
            visited.add(currentPosition);

            for(int i = 0; i < 4; i++) {
                Coordinate nextPos = new Coordinate(currentPosition);
                nextPos.addDelta(deltas[i]);
                if(!visited.contains(nextPos) && layout.isEmptyCell(nextPos.getX(), nextPos.getY())) {
                    toVisit.addLast(nextPos);
                }
            }
        }
        return totalCells == visited.size();
    }

    //INCOMPLETE!!!
    // TODO: add condition for exits
    public void addObstacles(int count) {
        Random random = new Random();
        while(count > 0) {
            int x = 1 + Math.abs(random.nextInt()) % (this.sizeX - 1);
            int y = 1 + Math.abs(random.nextInt()) % (this.sizeY - 1);
            if(!this.layout.isEmptyCell(x, y))
                continue;
            this.layout.add(x, y, CellType.OBSTACLE);
            if(this.checkFullyAccessible()) {
                count--;
                this.backupLayout.add(x, y, CellType.OBSTACLE);
            } else {
                this.layout.remove(x, y);
            }
        }
    }
    /*
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
    }*/

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-".repeat(Math.max(0, 2 * sizeY + 1)));
        builder.append("\n");
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(layout.isEmptyCell(i, j)) {
                    builder.append("| ");
                } else if(layout.get(i, j).equals(CellType.OBSTACLE)) {
                    builder.append("|#");
                }  else if(layout.get(i, j).equals(CellType.PLAYER)) {
                    builder.append("|P");
                } else if(layout.get(i, j).equals(CellType.DOORWAY)){
                    builder.append("|E");
                } else {
                    builder.append("|");
                    builder.append(layout.get(i, j).getIndex());
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

    public Matrix<CellType> getLayout() {
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
