package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Stanza implements Serializable {

    /**
     * Default constructor
     */
    public Stanza() {
        players= new HashMap<>();
    }

    /**
     *
     */
    private HashMap<String, AbstractPlayer> players = null;

    /**
     *
     */
    private Game game;


    public void joinPlayer(AbstractPlayer player, String username) {

        players.put(username, player);

    }

    public int nPlayers(){
        return players.size();
    }

}