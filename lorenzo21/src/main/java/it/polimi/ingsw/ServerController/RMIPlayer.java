package it.polimi.ingsw.ServerController;

import java.net.SocketException;
import java.util.*;

/**
 * 
 */
public class RMIPlayer extends AbstractPlayer {

    /**
     * Default constructor
     */
    private RMIPlayer() {
    }

    @Override
    public Object receive() throws SocketException {
        return null;
    }

    @Override
    public void send(Object message) {

    }

    /**
     * 
     */
    private AbstractPlayer remotePlayer;

    /**
     *
     */
    public void doSomething() {
        // TODO implement here
    }

}