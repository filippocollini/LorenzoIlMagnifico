package it.polimi.ingsw.ServerController;

import java.io.Serializable;

/**
 * Created by filippocollini on 15/06/17.
 */
public class Publisher<M extends Serializable, T extends Serializable> extends Thread implements PublisherInterface<M,T> {


    private Server<M,T> broker;

    public Publisher(Server<M, T> b) {
        broker = b;
    }

    public IServer<M,T> getBroker() {
        return broker;
    }

}
