package it.polimi.ingsw.ServerController;

import java.util.*;

/**
 * 
 */
public class AbstractPlayer {

    private Stanza room;

    /**
     * Default constructor
     */
    public AbstractPlayer() {
    }



    /**
     * 
     */
    public void AssociaPlayerEPartita() {
        // TODO implement here
    }

    /**
     * 
     */
    public void setRoom(Stanza room) {
        this.room=room;
    }

    public Stanza getRoom() {
        return room;
    }
}