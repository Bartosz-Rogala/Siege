package game.objects;

import game.Player;

/**
 *
 * BuilderDps is a class that represents non-standard fighter in a game. The only character that can build obstacles.
 * Class extends Unit class
 *
 * @Author Łukasz Łaszek
 */
public class BuilderDps extends Unit {

    public BuilderDps(Player owner, String race) {
        super(owner, race,5,15, 50,4,1);
    }

    @Override
    public String toString() {
        return "builder" + "," + super.toString();
    }
}
