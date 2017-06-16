package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class RMIServer implements AbstractServer {

    /**
     * 
     */
    private int port;

    /**
     * Default constructor
     *
     * @param controller
     */
    public RMIServer(IServer controller) {
        super();
    }


    /*@Override
    public void startServer(int port) throws IOException {

    }

    @Override
    public Game createRoom(AbstractPlayer player) {
        return null;
    }

    @Override
    public void joinRoom(Stanza room, AbstractPlayer player) {

    }

    @Override
    public void loginPlayer(String username) {

    }
    */

    @Override
    public void startServer() throws IOException {

    }
}