package pw.client;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class Hexagon {

    private Polygon hex;
    private boolean isActive;
    private boolean isFilled;
    private String race;
    private String type;
    private int moveRadius;
    private int shootRadius;
    private ArrayList<Hexagon> moveNeighbours;
    private ArrayList<Hexagon> shootNeighbours;
    StringBuilder url;

    public Hexagon(Polygon hex) {
        this.hex = hex;
        this.isActive = false;
        this.isFilled = false;
        moveNeighbours = new ArrayList<>();
    }

    public void populate(String type, String race, int moveRadius, int shootRadius) {
        url = new StringBuilder("C:\\Users\\01168103\\Intellij Projects\\Siege\\Client\\src\\main\\resources\\client\\pawns\\");
        switch (race) {
            case "goblin":
                url.append("goblin\\");
                break;
            case "human":
                url.append("human\\");
                break;
            case "monster":
                url.append("monster\\");
                break;
        }

        switch (type) {
            case "soldier":
                url.append("sword.png");
                break;
            case "archer":
                url.append("bow.png");
                break;
            case "tank":
                url.append("shield.png");
                break;
        }

        this.hex.setFill(new ImagePattern(new Image(url.toString())));
        this.isFilled = true;
        this.type = type;
        this.race = race;
        this.moveRadius = moveRadius;
        this.shootRadius = shootRadius;
    }

    public void addNeighbour(Hexagon neighbour) {
        moveNeighbours.add(neighbour);
    }

    public ArrayList<Hexagon> getNeighbours() {
        return moveNeighbours;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setIsActive(boolean active) {

        if (active) {
            this.isActive = true;
            url = url.replace(url.lastIndexOf(".png"), url.lastIndexOf(".png"), "_selected");

            this.hex.setFill(new ImagePattern(new Image(url.toString())));
            for (Hexagon hex: moveNeighbours) {
                if (!hex.isFilled()) {
                    hex.getHex().setStroke(Color.BLACK);
                }
            }
        } else {
            if (isFilled && isActive) {
                url = url.replace(url.lastIndexOf("_selected"), url.lastIndexOf("_selected") + 9, "");
                this.hex.setFill(new ImagePattern(new Image(url.toString())));
                this.hex.setStroke(Color.rgb(77,77,77,0.33));
                for (Hexagon hex: moveNeighbours) {
                    if (!hex.isFilled()) {
                        hex.getHex().setStroke(Color.rgb(77,77,77,0.33));
                    }
                }
            } else {
                this.hex.setFill(Color.TRANSPARENT);
                this.hex.setStroke(Color.rgb(77,77,77,0.33));
            }
            this.isActive = false;
        }
    }

    public Polygon getHex() {
        return this.hex;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public int getMoveRadius() {
        return moveRadius;
    }

    public int getShootRadius() {
        return shootRadius;
    }

    public String getRace() {
        return race;
    }

    public String getType() {
        return type;
    }

    public void clear() {
        System.out.println("Hex is cleared");
        this.isFilled = false;
        this.url.setLength(0);
        this.hex.setFill(Color.TRANSPARENT);
    }

    @Override
    public String toString() {
        if (isFilled) {
            return isFilled + "," + type + "," + race + "," + moveRadius + "," + shootRadius;
        } else {
            return isFilled + "";
        }

    }
}
