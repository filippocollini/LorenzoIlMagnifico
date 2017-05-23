package it.polimi.ingsw.ServerController;

import java.util.*;

/**
 * 
 */
public class Server implements IServer {

    /**
     * Default constructor
     */
    public Server() {
    }

    /**
     * 
     */
    private RMIServer rmiServer;

    /**
     * 
     */
    private SocketServer socketServer;

    /**
     * 
     */
    private HashMap<String, AbstractPlayer> players;

    /**
     * 
     */
    private ArrayList<Stanza> stanze;




}