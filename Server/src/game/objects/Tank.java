package game.objects;


import game.Player;

public class Tank extends Unit {

    public Tank(Player owner, String race) {
        super(owner, race,2,17,1,1);
    }

    @Override
    public String toString() {
        return "tank" + "," + super.toString();
    }
}
