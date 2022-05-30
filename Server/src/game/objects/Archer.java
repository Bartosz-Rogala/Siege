package game.objects;

import game.Player;

public class Archer extends Unit {

    public Archer(Player owner, String race) {
        super(owner, race,3,10,1,3);
    }

    @Override
    public String toString() {
        return "archer" + "," + super.toString();
    }
}