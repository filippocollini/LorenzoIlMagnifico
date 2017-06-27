package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.ConnectionInterface;

/**
 * Created by filippocollini on 15/06/17.
 */
public interface SocketPublisherInterface<M,T> {

    public ConnectionInterface<M,T> getBroker();
    default public void publish(M msg, T topic) {
        getBroker().publish(msg, topic);
    }
}