package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ServerController.AbstractPlayer;

import java.io.Serializable;
import java.net.SocketException;
import java.rmi.Remote;
import java.util.*;

/**
 * 
 */
public class RMIPlayer <M extends Serializable> extends AbstractPlayer<M> implements Remote {

    /**
     * Default constructor
     */
    public RMIPlayer() {
    }


    @Override
    public M receive() {
        return null;
    }

    @Override
    public void send(M message) {

    }
}