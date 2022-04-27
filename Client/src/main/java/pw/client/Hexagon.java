package pw.client;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Hexagon {

    private Polygon hex;
    private boolean isActive;
    private ArrayList<Hexagon> neighbours;

    public Hexagon(Polygon hex) {
        this.hex = hex;
        this.isActive = false;
        neighbours = new ArrayList<>();
    }

    public void addNeighbour(Hexagon neighbour) {
        neighbours.add(neighbour);
    }

    public ArrayList<Hexagon> getNeighbours() {
        return neighbours;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean active) {

        if (active) {
            this.isActive = true;
            hex.setFill(Color.BLACK);
            for (Hexagon hex: neighbours) {
                hex.getHex().setFill(Color.GRAY);
            }
        } else {
            this.isActive = false;

            hex.setFill(Color.TRANSPARENT);

        }
    }

    public Polygon getHex() {
        return this.hex;
    }
}
