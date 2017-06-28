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

    /**
     * Default constructor
     */
    public Stanza() {
        players= new HashMap<>();
        list = new ArrayList<AbstractPlayer>((Collection<? extends AbstractPlayer>) players.values());
        timerStarted = false;
        matchStarted= false;
    }

    private transient Timer timer;
    public static final int MAXPLAYERS = 4;

    /**
     *
     */
    ArrayList<AbstractPlayer> list;

    /**
     *
     */
    public HashMap<String, AbstractPlayer> players = null;

    /**
     *
     */
    private Game game;

    /**
     *
     */
    private boolean timerStarted;

    public boolean matchStarted;


    public void joinPlayer(AbstractPlayer player, String username) {
        players.put(username, player);
        if(players.size()>1 && !timerStarted) {
            timerHandler();
            System.out.println("parte il countdown");
        }else
            if(players.size()==MAXPLAYERS){
                timer.cancel();
                matchStarted=true;
                System.out.println("numero massimo di giocatori raggiunto, INIZIA LA PARTITA!");
                //chiama gameConfiguration
            }else if(players.size()>1 && timerStarted){
                timer.cancel();
                System.out.println("riparte il countdown");
                timerHandler();
            }
    }

    private void timerHandler(){
        timer = new Timer();
        timerStarted=true;
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 20;
            public void run() {
                System.out.print(i-- +" ... ");
                if (i< 0){
                    timer.cancel();
                    matchStarted=true;
                    //chiama gameConfiguration
                    System.out.println("INIZIA LA PARTITA!");
                }

            }
        }, 0, 1000);
    }

    public int nPlayers(){
        return players.size();
    }


}