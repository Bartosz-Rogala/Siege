package game.objects;

public class Tank extends Unit {

    public Tank(String race) {
        super(race,1,1);
    }

    @Override
    public String toString() {
        return "tank" + "," + super.toString();
    }
}
