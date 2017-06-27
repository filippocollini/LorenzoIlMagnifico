package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 27/06/2017.
 */
public abstract class BoardObserver {
    protected Board board;
    public abstract void update();
}
