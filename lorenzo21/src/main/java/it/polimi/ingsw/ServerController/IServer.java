package it.polimi.ingsw.ServerController;

import java.io.Serializable;

/**
 * send request to the server
 */
public interface IServer<M,T>{
        public void subscribe(PlayerInterface<M> s, T room);
        public void unsubscribe(PlayerInterface<M> s, T room);
        public void publish(M msg, T room);
        public Stanza joinRoom(SocketPlayer<Serializable> player, String username);
}


