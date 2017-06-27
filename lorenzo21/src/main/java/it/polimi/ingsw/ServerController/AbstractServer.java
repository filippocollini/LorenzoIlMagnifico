package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.IClient;

import java.util.ArrayList;

/**
 * 
 */
public abstract class AbstractServer {

    public ConnectionInterface connectionHandler;
    public ArrayList<AbstractClient> clients;

    public AbstractServer(ConnectionInterface connectionHandler){
        this.connectionHandler=connectionHandler;
        clients = new ArrayList<AbstractClient>();
    }

    /**
     * 
     */
    public ConnectionInterface getConnectionHandler() {
        return connectionHandler;
    }
}