package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.ConnectionInterface;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.socket.SocketPublisherInterface;

import java.io.Serializable;

/**
 * Created by filippocollini on 15/06/17.
 */
public class SocketSocketPublisher<M extends Serializable, T extends Serializable> implements SocketPublisherInterface<M,T> {


    private Server<M,T> broker;

    public SocketSocketPublisher(Server<M, T> b) {
        broker = b;
    }

    public ConnectionInterface<M,T> getBroker() {
        return broker;
    }

}
