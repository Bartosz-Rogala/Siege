package game;

import game.objects.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static game.Properties.BOARD_HEIGHT;
import static game.Properties.BOARD_WIDTH;
import static game.Properties.MOVES_IN_A_ROUND;

public class Game {
    Hexagon[][] hexagons;
    private int armyCount = 0;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private Player nullPlayer;
    private int movesLeftInRound;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.nullPlayer = new Player();
        nullPlayer.setSocket(new Socket());
        this.currentPlayer = player1;
        this.movesLeftInRound = MOVES_IN_A_ROUND;
        this.hexagons = new Hexagon[BOARD_HEIGHT][BOARD_WIDTH];
        initializeHexagons();
    }

    public void initializeHexagons() {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                hexagons[i][j] = new Hexagon(i, j);
            }
        }
    }

    public void generateArmy(Player owner, String army) {
        armyCount++;
        int x = BOARD_HEIGHT;
        int y = armyCount > 1 ? BOARD_WIDTH - 2 : 0;
        List<Integer> pawnPlaces = drawWithoutRepetition();
        int currentIteration = 0;


        for (int i = 0; i < x; i++) {
            for (int j = y; j < y + 2; j++) {
                if (pawnPlaces.contains(currentIteration)) {
                    int random = (int) (Math.random() * 100);
                    if (random < 25) {
                        hexagons[i][j].setUnit(new MeleeTank(owner, army));
                    } else if (random < 50) {
                        hexagons[i][j].setUnit(new RangedDps(owner, army));
                    } else if (random < 80) {
                        hexagons[i][j].setUnit(new MeleeDps(owner, army));
                    } else {
                        hexagons[i][j].setUnit(new BuilderDps(owner, army));
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


    public Hexagon[][] getHexagons() {
        return hexagons;
    }


    public boolean isOver() {
        return false;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }


    public void updateGame(String currentPositions) {

    }

    public void attack(String attacker, String victim) {
        System.out.println("attacking");
        String[] attackerTokens = attacker.split(",");
        String[] victimTokens = victim.split(",");

        Hexagon attackerHex = hexagons[Integer.parseInt(attackerTokens[1])][Integer.parseInt(attackerTokens[2])];
        Hexagon victimHex = hexagons[Integer.parseInt(victimTokens[1])][Integer.parseInt(victimTokens[2])];

        victimHex.getUnit().attack(attackerHex.getUnit());

        if (victimHex.getUnit().isDead()) {
            victimHex.clear();
        }
    }

    public void move(String from, String to) {
        System.out.println("moving");
        String[] fromTokens = from.split(",");
        String[] toTokens = to.split(",");

        hexagons[Integer.parseInt(toTokens[1])][Integer.parseInt(toTokens[2])].move(hexagons[Integer.parseInt(fromTokens[1])][Integer.parseInt(fromTokens[2])]);
        hexagons[Integer.parseInt(fromTokens[1])][Integer.parseInt(fromTokens[2])].clear();
    }

    public void build(String builder, String buildingSite) {
        System.out.println("building");
        String[] buildingSiteTokens = buildingSite.split(",");

        hexagons[Integer.parseInt(buildingSiteTokens[1])][Integer.parseInt(buildingSiteTokens[2])].setUnit(new Obstacle(nullPlayer, "other"));
    }

    public void countTurn(int playerId) {
        if (movesLeftInRound > 0 && playerId == currentPlayer.getPlayerId()) {
            movesLeftInRound--;
            if (movesLeftInRound == 0) {
                if (currentPlayer.getPlayerId() == player1.getPlayerId()) {
                    currentPlayer = player2;
                } else {
                    currentPlayer = player1;
                }
                movesLeftInRound = MOVES_IN_A_ROUND;
            }
        }
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
