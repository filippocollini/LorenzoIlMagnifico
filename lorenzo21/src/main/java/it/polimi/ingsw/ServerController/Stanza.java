package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.GameModelServer.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
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
            timer = new Timer();
            timer.schedule(new GameHandler(), 20*1000L);
            timerStarted=true;
            System.out.println("parte il countdown");
        }else
            if(players.size()==MAXPLAYERS){
                timer.cancel();
                timer.purge();
                timer.schedule(new GameHandler(), 0L);
                System.out.println("numero massimo di giocatori raggiunto, INIZIA LA PARTITA!");
            }else if(players.size()>1 && timerStarted){
                timer.cancel();
                timer.purge();
                System.out.println("riparte il countdown");
                timer = new Timer();
                timer.schedule(new GameHandler(), 20*1000L);
                timerStarted=true;
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
                    timer.purge();
                    matchStarted=true;
                    //chiama gameConfiguration
                    System.out.println("INIZIA LA PARTITA!");
                    /*for(AbstractPlayer c: players.values()){
                        c.send("inizia la partita");
                    }*/
                }

            }
        }, 0, 1000);

    }

    public int nPlayers(){
        return players.size();
    }

    private class GameHandler extends TimerTask{

        @Override
        public void run() {
            System.out.println("start");
            //configuration();
            System.out.println("creato tutto");
            //dispatchGameToPlayers();
            dispatchProva();
            timer.schedule(new TurnHandler(), 10*1000L);

        }


    }

    private class TurnHandler extends TimerTask{

        @Override
        public void run() {
            for(AbstractPlayer p : players.values()){
                try {
                    dispatchToPlayer(p);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dispatchToPlayer(AbstractPlayer player) throws RemoteException {
        player.dispatchEsempio();
    }

    private void configuration(){
        this.game=new Game(players, this);
    }

    private void dispatchGameToPlayers(){
        for (AbstractPlayer p : players.values()){
            try {
                p.dispatchGameSettings(game);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatchProva(){
        for (AbstractPlayer p : players.values()){
            try {
                p.dispatchEsempio();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}