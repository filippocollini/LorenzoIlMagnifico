package it.polimi.ingsw.ServerController;

import java.rmi.Remote;

/**
 * send request to the server
 */
public interface ConnectionInterface<M,T> extends Remote {
    public void subscribe(PlayerInterface<M> s, T room);
    public void unsubscribe(PlayerInterface<M> s, T room);
    public void publish(M msg, T room);
    public boolean joinPlayer(AbstractPlayer player, String username);
    public String handleRequest(String request);
}


