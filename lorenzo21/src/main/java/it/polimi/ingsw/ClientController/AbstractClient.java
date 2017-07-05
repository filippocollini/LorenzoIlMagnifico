package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.State;

import java.rmi.RemoteException;

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

    public abstract void handle(String request, State state) throws RemoteException;

    public abstract void marketMove(String uuid) throws RemoteException;

    public abstract void endMove(String uuid) throws RemoteException;
}