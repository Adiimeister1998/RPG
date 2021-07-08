package IO;

import entities.Coordinate;
import entities.Monster;
import entities.Player;
import entities.Room;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CommandReader {
    private Scanner scanner;
    private Room room;

    public CommandReader(Room room) {
        this.room = room;
        this.scanner= new Scanner (System.in);
    }

    public void getCommand (){
        while(true) {
            String[] comm = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");
            switch (comm[0]) {
                case "go" -> {
                    if(comm.length < 2) {
                        System.out.println("go Command needs direction (north/south/east/west)!");
                        continue;
                    }
                    if(!getDirection(comm[1])) {
                        System.out.println("go Command needs direction (north/south/east/west)!");
                        continue;
                    }
                }
                case "heal" -> room.getPlayer().heal(room.getPlayer());
                case "attack" -> {
                    if(comm.length < 2) {
                        System.out.println("attack Command needs monster name!");
                        continue;
                    }
                    if(!executeAttack(comm[1]))
                        continue;
                }
                case "analyze" -> {
                    System.out.println(room.getPlayer());
                    printMonsters();
                    continue;
                }
                default -> {System.out.println("Invalid command!"); continue;}
            }
            break;
        }
    }

    private void printMonsters() {
        for(Monster monster : room.getMonsters()) {
            System.out.println(monster);
        }
        System.out.println(room);
    }

    private boolean executeAttack(String monsterName) {
        for(Monster monster : room.getMonsters()) {
            if(monster.getName().toLowerCase(Locale.ROOT).equals(monsterName)) {
                if(room.getPlayer().getCoord().getDistance(monster.getCoord()) <= 2) {
                    room.getPlayer().attack(monster);
                    return true;
                } else {
                    System.out.println("That monster is too far away!");
                    return false;
                }

            }
        }

        System.out.println("That monster does not exist!");
        return false;
    }

    private boolean getDirection(String direction) {
        Coordinate newCoord = switch (direction) {
            case "north" -> new Coordinate(-1, 0);
            case "south" -> new Coordinate(1, 0);
            case "west" -> new Coordinate(0, -1);
            case "east" -> new Coordinate(0, 1);
            default -> new Coordinate(0, 0);
        };
        Coordinate dest=new Coordinate(room.getPlayer().getCoord());
        dest.addDelta(newCoord);
        if (!room.getLayout().isEmptyCell(dest.getX(), dest.getY())){
            System.out.println("Invalid destintation");
            return false;
        }

        room.getLayout().remove(room.getPlayer().getCoord().getX(), room.getPlayer().getCoord().getY());
        room.getPlayer().goDirection(newCoord);
        room.getLayout().add(room.getPlayer().getCoord().getX(), room.getPlayer().getCoord().getY(), -2);
       return true;
    }

}
