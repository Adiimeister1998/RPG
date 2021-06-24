package IO;

import entities.Coordinate;
import entities.Monster;
import entities.Player;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class CommandReader {
    private Scanner scanner;
    private Player player;
    private ArrayList<Monster> monsters;

    public CommandReader(Player player, ArrayList<Monster> monsters) {
        this.player = player;
        this.monsters = monsters;
        this.scanner= new Scanner (System.in);
    }

    public void getCommand (){
        while(true) {
            String[] comm = scanner.nextLine().toLowerCase(Locale.ROOT).split(" ");
            switch (comm[0]) {
                case "go" -> getDirection(comm[1]);
                case "heal" -> player.heal(player);
                case "attack" -> executeAttack(comm[1]);
                case "analyze" -> printMonsters();
                default -> {System.out.println("Invalid command!"); continue;}
            }
            break;
        }
    }

    private void printMonsters() {
        for(Monster monster : monsters) {
            System.out.println(monster);
        }
    }

    private void executeAttack(String monsterName) {
        for(Monster monster : monsters) {
            if(monster.getName().toLowerCase(Locale.ROOT).equals(monsterName)) {
                if(player.getCoord().getDistance(monster.getCoord()) <= 2) {
                    player.attack(monster);
                } else {
                    System.out.println("That monster is too far away!");
                }
                return;
            }
        }

        System.out.println("That monster does not exist!");
    }

    private void getDirection(String direction) {
        switch (direction){
            case "north" -> player.goDirection(new Coordinate(-1, 0));
            case "south" -> player.goDirection(new Coordinate(1, 0));
            case "west" -> player.goDirection(new Coordinate(0, -1));
            case "east" -> player.goDirection(new Coordinate(0, 1));
            default -> System.out.println("Invalid direction");
        }
    }
}
