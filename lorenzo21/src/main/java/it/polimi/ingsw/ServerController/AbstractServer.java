package it.polimi.ingsw.ServerController;

import java.io.IOException;
import java.util.*;

/**
 * 
 */
public abstract class AbstractServer implements IServer {

    /**
     * Default constructor
     */
    public AbstractServer(IServer controller) {
        this.controller=controller;
    }

    /**
     * 
     */
    private IServer controller;



    /**
     * 
     */
    public abstract void startServer() throws IOException;

}