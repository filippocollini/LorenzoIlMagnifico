package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 26/05/17.
 */
public interface Event {

    public boolean isLegal();
    public void eventHappened();
}
