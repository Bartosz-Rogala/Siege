package game.objects;

import game.Player;

/**
 *
 * Chest is a class that represents a non-player object in a game. Chest can contain diamond or coal (nothing)
 * Once player gets the diamond from opponents chest, he wins the game.
 * Class extends Unit class
 *
 * @Author Łukasz Łaszek
 */
public class Chest extends Unit {
    private String name;
    private boolean isOpen;

    public Chest(Player owner, String race) {
        super(owner, race,0,0, 120,0,0);
        this.name = "chest";
        this.isOpen = false;
    }

    public void open() {
        this.name = "openChest";
        this.isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return this.name + "," + super.toString();
    }
}
