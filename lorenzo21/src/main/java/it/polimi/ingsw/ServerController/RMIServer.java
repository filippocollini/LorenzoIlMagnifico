package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class RMIServer extends AbstractServer {

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
        super(controller);
    }


    @Override
    public void startServer() throws IOException {

    }

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