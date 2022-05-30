package game.objects;

import game.Player;

import java.net.Socket;

public class Soldier extends Unit {

    public Soldier(Player owner, String race) {
        super(owner, race,4, 12,2,1);
    }

    @Override
    public String toString() {
        return "soldier" + "," + super.toString();
    }
}
