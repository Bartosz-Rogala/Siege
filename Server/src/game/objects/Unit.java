package game.objects;

public class Unit extends GameObject {
    private String race;

    private int attack;
    private int healthPoints;
    private int moveRadius;
    private int shootRadius;

    public Unit() {
        moveRadius = 1;
        shootRadius = 1;
        race = "human";
    }

    public Unit(String race, int attack, int healthPoints, int moveRadius, int shootRadius) {
        this.race = race;
        this.attack = attack;
        this.healthPoints = healthPoints;
        this.moveRadius = moveRadius;
        this.shootRadius = shootRadius;

    }

    @Override
    public String toString() {
        return race + "," + attack + "," + healthPoints + "," + moveRadius + "," + shootRadius;
    }
}
