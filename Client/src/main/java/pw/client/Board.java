package pw.client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Board {

    Hexagon[][] hexagons;

    public Board(Hexagon[][] hexagons) {
        this.hexagons = hexagons;
    }

    public boolean isAnyHexActive() {

        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                if (hexagons[i][j].getIsActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void deactivateAll() {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                hexagons[i][j].setIsActive(false);
            }
        }
    }

    public void activate (Polygon hex) {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                if (hexagons[i][j].getHex().getId().equals(hex.getId())) {
                    hexagons[i][j].setIsActive(true);
                }
            }
        }
    }

    public boolean isActiveNeighbour (Polygon hex) {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                if (hexagons[i][j].getIsActive()) {
                    for (Hexagon neighbour: hexagons[i][j].getNeighbours()) {
                        if (neighbour.getHex().getId().equals(hex.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getContent() {
        String content = "";
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                content += hexagons[i][j].getIsActive() + ";";
            }
        }
        return content;
    }

    public void parseMessage(String[] hexes) {
        deactivateAll();
        int k = 0;
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                String[] tokens = hexes[k].split(",");
                System.out.println("tralalaalal");
                if (tokens[0].equals("true")) {
                    System.out.println("asdf");
                    hexagons[i][j].getHex().setFill(Color.web(tokens[2]));
                }
                k++;
//                if (Boolean.valueOf(activeHexes[k]) == true) {
//                    activate(hexagons[i][j].getHex());
//                }
//                k++;

            }
        }
    }

    // to do: add radius (if radius > 1) { addNeighour(radius -1)
    public void addNeighbours(int radius) {
        int maxI = hexagons.length;
        int maxJ = hexagons[0].length;
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {

                if (i % 2 == 0) {
                    if (i - 1 >= 0) {
                        hexagons[i][j].addNeighbour(hexagons[i-1][j]);
                        if (j - 1 >= 0) {
                            hexagons[i][j].addNeighbour(hexagons[i-1][j-1]);}
                    }
                    if (i + 1 < maxI) {
                        hexagons[i][j].addNeighbour(hexagons[i+1][j]);
                        if (j - 1 >= 0) {
                            hexagons[i][j].addNeighbour(hexagons[i+1][j-1]);}
                    }
                    if (j - 1 >= 0) {
                        hexagons[i][j].addNeighbour(hexagons[i][j-1]);}
                    if (j + 1 < maxJ) {
                        hexagons[i][j].addNeighbour(hexagons[i][j+1]);}
                } else {
                    if (i - 1 >= 0) {
                        hexagons[i][j].addNeighbour(hexagons[i-1][j]);
                        if (j + 1 < maxJ) {
                            hexagons[i][j].addNeighbour(hexagons[i-1][j+1]);}
                    }
                    if (i + 1 < maxI) {
                        hexagons[i][j].addNeighbour(hexagons[i+1][j]);
                        if (j + 1 < maxJ) {
                            hexagons[i][j].addNeighbour(hexagons[i+1][j+1]);}
                    }
                    if (j - 1 >= 0) {
                        hexagons[i][j].addNeighbour(hexagons[i][j-1]);}
                    if (j + 1 < maxJ) {
                        hexagons[i][j].addNeighbour(hexagons[i][j+1]);}
                }
            }
        }
    }
}
