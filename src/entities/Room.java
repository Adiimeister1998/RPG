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
    private Matrix<Cell> layout;
    private ArrayList<Monster> monsters;
    private final Matrix<Cell> backupLayout;
    private final ArrayList<Monster> backupMonsters;
    private Player player;
    private ArrayList<Doorway> exits;

    public Room(int sizeX, int sizeY, Player player, Matrix<Cell> layout,
                ArrayList<Monster> monsters, ArrayList<Doorway> exits, boolean isCleared) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.player = player;
        this.layout = layout;
        this.exits = new ArrayList<>();
        this.monsters = monsters;
        this.backupLayout = new Matrix<>(layout);
        this.backupMonsters = new ArrayList<>(monsters);
        this.isCleared = isCleared;

        this.boardRoom();
        for(Doorway exit : exits) {
            addExit(exit);
        }
    }

    public void resetRoom() {
        layout = new Matrix<>(backupLayout);
        monsters = new ArrayList<>(backupMonsters);
        layout.add(player.getCoord().getX(), player.getCoord().getY(), new Cell(CellType.PLAYER));
    }

    public void addExit(Doorway exit) {
        this.exits.add(exit);
        this.layout.add(exit.getCoord().getX(), exit.getCoord().getY(), new Cell(CellType.DOORWAY));
        this.backupLayout.add(exit.getCoord().getX(), exit.getCoord().getY(), new Cell(CellType.DOORWAY));
    }

    public void boardRoom() {
        for (int y=0;y<this.sizeY;y++){
            layout.add(0, y, new Cell(CellType.OBSTACLE));
            layout.add(this.sizeX - 1, y, new Cell(CellType.OBSTACLE));
            backupLayout.add(0, y, new Cell(CellType.OBSTACLE));
            backupLayout.add(this.sizeX - 1, y, new Cell(CellType.OBSTACLE));

        }
        for (int x=0;x<this.sizeX;x++){
            layout.add(x, 0, new Cell(CellType.OBSTACLE));
            layout.add(x, this.sizeY - 1, new Cell(CellType.OBSTACLE));
            backupLayout.add(x, 0, new Cell(CellType.OBSTACLE));
            backupLayout.add(x, this.sizeY - 1, new Cell(CellType.OBSTACLE));
        }
    }

    public boolean checkFullyAccessible() {
        Coordinate currentPosition = null;
        ArrayDeque<Coordinate> toVisit = new ArrayDeque<>();
        TreeSet<Coordinate> visited = new TreeSet<>();
        int totalCells = 0;
        for(int x = 0; x < this.sizeX; x++) {
            for(int y = 0; y < this.sizeY; y++) {
                if(this.layout.isEmptyCell(x, y) || !this.layout.get(x, y).getType().equals(CellType.OBSTACLE)) {
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
                if(isValidCoord(nextPos, layout.getRows(), layout.getColumns()) &&
                        !visited.contains(nextPos) &&
                        (layout.isEmptyCell(nextPos.getX(), nextPos.getY()) ||
                         !layout.get(nextPos.getX(), nextPos.getY()).getType().equals(CellType.OBSTACLE))) {
                    toVisit.addLast(nextPos);
                }
            }
        }
        return totalCells == visited.size();
    }

    private boolean isValidCoord(Coordinate nextPos, int rows, int columns) {
        return nextPos.getX() >= 0 && nextPos.getX() < rows && nextPos.getY() >= 0 && nextPos.getY() < columns;
    }

    public void addObstacles(int count) {
        Random random = new Random();
        while(count > 0) {
            int x = 1 + Math.abs(random.nextInt()) % (this.sizeX - 1);
            int y = 1 + Math.abs(random.nextInt()) % (this.sizeY - 1);
            if(!this.layout.isEmptyCell(x, y))
                continue;
            this.layout.add(x, y, new Cell(CellType.OBSTACLE));
            if(this.checkFullyAccessible()) {
                count--;
                this.backupLayout.add(x, y, new Cell(CellType.OBSTACLE));
            } else {
                this.layout.remove(x, y);
            }
        }
    }
    public void addMonsters(int count) {
        Random random = new Random();
        int idx = 0;
        for(int i = 0; i < count; i++) {
            int x = 1 + Math.abs(random.nextInt()) % (this.sizeX - 1);
            int y = 1 + Math.abs(random.nextInt()) % (this.sizeY - 1);
            Monster newMonster = MonsterFactory.SINGLETON.getMonster(GenericClass.getRandomClassType(), 1, "Monster#" + idx, x, y);
            if(!this.layout.isEmptyCell(x, y))
                continue;
            Cell monsterCell = new Cell(CellType.MONSTER, idx);
            this.layout.add(x, y, monsterCell);
            monsters.add(newMonster);
            idx++;
            count--;
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
                } else if(layout.get(i, j).getType().equals(CellType.OBSTACLE)) {
                    builder.append("|#");
                }  else if(layout.get(i, j).getType().equals(CellType.PLAYER)) {
                    builder.append("|P");
                } else if(layout.get(i, j).getType().equals(CellType.DOORWAY)){
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

    public Matrix<Cell> getLayout() {
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
