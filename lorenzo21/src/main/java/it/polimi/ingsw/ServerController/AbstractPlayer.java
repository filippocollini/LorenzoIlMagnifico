package it.polimi.ingsw.ServerController;

import java.net.SocketException;
import java.util.*;

/**
 * 
 */
public abstract class AbstractPlayer<M> {

    private Stanza room;

    /**
     * Default constructor
     */
    public AbstractPlayer() {
    }

    //public abstract void dispatchMessage(M msg);

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