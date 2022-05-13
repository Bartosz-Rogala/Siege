package game.objects;

public class Unit extends GameObject {
    private String race;
    private int moveRadius;
    private int shootRadius;

    public Unit() {
        moveRadius = 1;
        shootRadius = 1;
        race = "human";
    }

    public Unit(String race, int moveRadius, int shootRadius) {
        this.race = race;
        this.moveRadius = moveRadius;
        this.shootRadius = shootRadius;
    }

    @Override
    public String toString() {
        return race + "," + moveRadius + "," + shootRadius;
    }
}
