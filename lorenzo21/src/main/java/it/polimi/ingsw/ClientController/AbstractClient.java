package it.polimi.ingsw.ClientController;

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

    public abstract String handleClientRequest(String request);

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


    public abstract void connect();

    public abstract String move(String request);
}