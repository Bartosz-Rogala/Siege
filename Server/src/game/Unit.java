package game;

public class Unit extends GameObject {
    private int moveRadius;
    private int shootRadius;
    private String color;

    public Unit (String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return moveRadius + "," + color;
    }
}
