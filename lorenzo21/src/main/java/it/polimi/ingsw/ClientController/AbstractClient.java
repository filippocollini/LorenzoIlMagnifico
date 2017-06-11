package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.GameModelServer.Player;
import it.polimi.ingsw.Observer;

/**
 * 
 */
public abstract class AbstractClient implements Observer {

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
    private Player player;


    /**
     * @return
     */
    public void update() {
        // TODO implement here
    }

    public abstract void login(String username);
}