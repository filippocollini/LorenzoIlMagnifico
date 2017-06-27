package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.GameModelServer.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Stanza implements Serializable {

    ArrayList<AbstractPlayer> list;

    /**
     * Default constructor
     */
    public Stanza() {
        players= new HashMap<>();
        list = new ArrayList<AbstractPlayer>((Collection<? extends AbstractPlayer>) players.values());
    }

    /**
     *
     */
    public HashMap<String, AbstractPlayer> players = null;

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