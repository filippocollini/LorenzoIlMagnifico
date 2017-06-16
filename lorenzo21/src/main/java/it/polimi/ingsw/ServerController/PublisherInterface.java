package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 15/06/17.
 */
public interface PublisherInterface<M,T> {

    public IServer<M,T> getBroker();
    default public void publish(M msg, T topic) {
        getBroker().publish(msg, topic);
    }
}