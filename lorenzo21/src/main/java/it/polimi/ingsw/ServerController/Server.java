package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.util.*;

/**
 * proxy to SocketServer and RMIServer
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


    @Override
    public Game createRoom(AbstractPlayer player) {
        return null;
    }

    @Override
    public void joinRoom(Stanza room, AbstractPlayer player) {

    }

    @Override
    public void loginPlayer(AbstractPlayer player) {

    }
}