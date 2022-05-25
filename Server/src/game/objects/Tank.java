package game.objects;

public class Tank extends Unit {

    public Tank(String race) {
        super(race,2,17,1,1);
    }

    @Override
    public String toString() {
        return "tank" + "," + super.toString();
    }
}
