package game.objects;

import game.Player;

/**
 *
 * Obstacle is a class that represents non-player object in a game - an Obstacle. Regardless, every object.
 * Class extends Unit class
 * has an owner.
 *
 * @Author Łukasz Łaszek
 */
public class Obstacle extends Unit {

    public Obstacle(Player owner, String race) {
        super(owner, race,0,0, 30,0,0);
    }

    @Override
    public String toString() {
        return "obstacle" + "," + super.toString();
    }
}
