package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.GameModelServer.Player;
import it.polimi.ingsw.Observer;
import it.polimi.ingsw.ServerController.AbstractPlayer;

/**
 * 
 */
public abstract class AbstractClient  {

    /**
     * Default constructor
     */
    public AbstractClient() {
    }

    /**
     *
     */
    private RMIClient rmiClient;

    /**
     *
     */
    private SocketClient socketClient;

    /**
     *
     */
    private AbstractPlayer player;


}