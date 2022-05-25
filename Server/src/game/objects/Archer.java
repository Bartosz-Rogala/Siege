package game.objects;

public class Archer extends Unit {

    public Archer(String race) {
        super(race,3,10,1,3);
    }

    @Override
    public String toString() {
        return "archer" + "," + super.toString();
    }
}