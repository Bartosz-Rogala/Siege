package game;

import game.objects.GameObject;

public class Hexagon {

    private boolean isFilled;
    private GameObject gameObject;


    public Hexagon() {
        isFilled = false;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        this.isFilled = filled;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
        this.isFilled = true;
    }

    public void removeGameObject() {
        this.gameObject = null;
        this.isFilled = false;
    }

    @Override
    public String toString() {
        return isFilled + "," + gameObject;
    }
}
