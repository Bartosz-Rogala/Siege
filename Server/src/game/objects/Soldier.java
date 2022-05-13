package game.objects;

import java.net.Socket;

public class Soldier extends Unit {

    public Soldier(String race) {
        super(race,2,1);
    }

    @Override
    public String toString() {
        return "soldier" + "," + super.toString();
    }
}
