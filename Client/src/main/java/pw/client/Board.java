package pw.client;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class Board {

    Hexagon[][] hexagons;

    public Board(Hexagon[][] hexagons) {
        this.hexagons = hexagons;
    }

    public void parseMessage(String[] hexes) {

        int k = 0;
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                String[] tokens = hexes[k].split(",");
                if (tokens[0].equals("true")) {
                    hexagons[i][j].populate(tokens[1], tokens[2], Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4]));
                    addMoveNeighbours(hexagons[i][j], hexagons[i][j].getMoveRadius(), i, j);
                } else {
                    hexagons[i][j].setIsActive(false);
                }
                k++;
            }
        }
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

    public boolean isFilledIn (Polygon hex) {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                if (hexagons[i][j].isFilled()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getContent() {
        String content = "";
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                content += hexagons[i][j].toString() + ";";
            }
        }
        return content;
    }

    public void addMoveNeighbours(Hexagon hex, int radius, int i, int j) {

        int maxI = hexagons.length;
        int maxJ = hexagons[0].length;

        if (radius >= 1) {
            if (i % 2 == 0) {
                if (i - 1 >= 0) {
                    hex.addNeighbour(hexagons[i - 1][j]);
                    addMoveNeighbours(hex, radius-1, i-1, j);
                    if (j - 1 >= 0) {
                        hex.addNeighbour(hexagons[i - 1][j - 1]);
                        addMoveNeighbours(hex, radius-1, i-1, j-1);
                    }
                }
                if (i + 1 < maxI) {
                    hex.addNeighbour(hexagons[i + 1][j]);
                    addMoveNeighbours(hex, radius-1, i+1, j);
                    if (j - 1 >= 0) {
                        hex.addNeighbour(hexagons[i + 1][j - 1]);
                        addMoveNeighbours(hex, radius-1, i+1, j-1);
                    }
                }
                if (j - 1 >= 0) {
                    hex.addNeighbour(hexagons[i][j - 1]);
                    addMoveNeighbours(hex, radius-1, i, j-1);
                }
                if (j + 1 < maxJ) {
                    hex.addNeighbour(hexagons[i][j + 1]);
                    addMoveNeighbours(hex, radius-1, i, j+1);
                }
            } else {
                if (i - 1 >= 0) {
                    hex.addNeighbour(hexagons[i - 1][j]);
                    addMoveNeighbours(hex, radius-1, i-1, j);
                    if (j + 1 < maxJ) {
                        hex.addNeighbour(hexagons[i - 1][j + 1]);
                        addMoveNeighbours(hex, radius-1, i-1, j+1);
                    }
                }
                if (i + 1 < maxI) {
                    hex.addNeighbour(hexagons[i + 1][j]);
                    addMoveNeighbours(hex, radius-1, i+1, j);
                    if (j + 1 < maxJ) {
                        hex.addNeighbour(hexagons[i + 1][j + 1]);
                        addMoveNeighbours(hex, radius-1, i+1, j+1);
                    }
                }
                if (j - 1 >= 0) {
                    hex.addNeighbour(hexagons[i][j - 1]);
                    addMoveNeighbours(hex, radius-1, i, j-1);
                }
                if (j + 1 < maxJ) {
                    hex.addNeighbour(hexagons[i][j + 1]);
                    addMoveNeighbours(hex, radius-1, i, j+1);
                }
            }
        }
    }
}
