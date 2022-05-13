package game;

import game.objects.Archer;
import game.objects.Soldier;
import game.objects.Tank;
import game.objects.Unit;

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


        for (int i = 0; i < x; i++) {
            for (int j = y; j < y + 2; j++) {
                if (pawnPlaces.contains(currentIteration)) {
                    int random = (int) (Math.random() * 100);
                    if (random < 30) {
                        hexagons[i][j].setGameObject(new Tank(army));
                    } else if (random < 60) {
                        hexagons[i][j].setGameObject(new Archer(army));
                    } else {
                        hexagons[i][j].setGameObject(new Soldier(army));
                    }
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
        return list.subList(0,6);
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
