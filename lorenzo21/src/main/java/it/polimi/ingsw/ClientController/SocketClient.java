package it.polimi.ingsw.ClientController;

import java.util.*;

/**
 * 
 */
public class SocketClient extends AbstractClient {

    ClientRules clientRules;

    /**
     * Default constructor
     */
    public SocketClient() {
    }

    @Override
    public void login(String username){
        clientRules.login(username);
    }

}