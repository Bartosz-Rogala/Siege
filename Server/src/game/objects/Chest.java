package game.objects;

import game.Player;

public class Chest extends Unit {
    String name;

    public Chest(Player owner, String race) {
        super(owner, race,0,0, 120,0,0);
        this.name = "chest";
    }

    public void open() {
        this.name = "openChest";
    }

    @Override
    public String toString() {
        return this.name + "," + super.toString();
    }
}
