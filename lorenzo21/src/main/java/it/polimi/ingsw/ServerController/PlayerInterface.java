package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 14/06/17.
 */
public interface PlayerInterface<M> {
    public void dispatchMessage(M msg);
}
