package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.Properties.boardHeight;
import static game.Properties.boardWidth;

public class Game {
    Hexagon[][] hexagons;
    private int armyCount = 0;
    public Game() {
        hexagons = new Hexagon[boardHeight][boardWidth];
        initializeHexagons();
    }

    public void initializeHexagons() {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                hexagons[i][j] = new Hexagon();
            }
        }
    }

    public void generateArmy(String army) {
        armyCount++;
        int x = boardHeight;
        int y = armyCount > 1 ? boardWidth - 2 : 0;
        List<Integer> pawnPlaces = drawWithoutRepetition();
        int currentIteration = 0;
        Unit pawn;

        switch (army) {
            case "human":
                pawn = new Unit("YELLOW");
                break;
            case "goblin":
                pawn = new Unit("GREEN");
                break;
            case "monster":
                pawn = new Unit("RED");
                break;
            default:
                pawn = new Unit("BLUE");
        }

        for (int i = 0; i < x; i++) {
            for (int j = y; j < y + 2; j++) {
                if (pawnPlaces.contains(currentIteration)) {
                    hexagons[i][j].setGameObject(pawn);
                }
                currentIteration++;
            }
        }
    }

    public List<Integer> drawWithoutRepetition() {
        ArrayList<Integer> list = new ArrayList<>(18);
        for (int i = 0; i < 18; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        return list.subList(0,8);
    }

    public void assignPlayerIDs() {

    }

    public Hexagon[][] getHexagons() {
        return hexagons;
    }

    public boolean isOver() {
        return false;
    }

    @Override
    public String toString() {
        String msg = "";
        for (Hexagon[] hexes: hexagons) {
            for (Hexagon hex: hexes) {
                msg += hex.toString() + ";";
            }
        }

        return msg;
    }
}
