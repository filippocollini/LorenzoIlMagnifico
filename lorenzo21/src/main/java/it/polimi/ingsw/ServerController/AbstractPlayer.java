package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * 
 */
public abstract class AbstractPlayer<M> {

    private Stanza room;

    Player player;

    /**
     * Default constructor
     */
    public AbstractPlayer() {
        player= new Player();
    }

    public abstract void dispatchGameSettings(Game game) throws RemoteException;

    /**
     * 
     */
    public void setRoom(Stanza room) {
        this.room=room;
    }

    public Stanza getRoom() {
        return room;
    }

    public abstract M receive() throws SocketException;
    public abstract void send(M message);
}