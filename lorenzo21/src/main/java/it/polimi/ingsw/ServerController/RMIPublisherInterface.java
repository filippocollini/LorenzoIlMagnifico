package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 18/06/17.
 */
public interface RMIPublisherInterface<M,T> {

    public ConnectionInterface<M,T> getBroker();
    default public void publish(M msg, T topic){
        getBroker().publish(msg, topic);
    }
}
