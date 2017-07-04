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
        stack = new Stack();
        timerStarted = false;
        matchStarted= false;
        turnHandler = new TurnHandler(stack, this);
    }

    private transient Timer timer;

    public static final int MAXPLAYERS = 4;

    /**
     *
     */
    Stack stack;

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

    TurnHandler turnHandler;

    PlayerTurn turn;



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
            //timer.schedule(new TurnHandler(), 10*1000L);
            //turnHandler.run();
            setStack();
            AbstractPlayer p = (AbstractPlayer) stack.pop();
            for(; stack.size()==1; p= (AbstractPlayer) stack.pop()){
                startPlayerTurn(p);
            }

        }


    }

    private void startPlayerTurn(AbstractPlayer p){
        turn = new PlayerTurn(p, this);
    }

    /*private class TurnHandler extends TimerTask{

        @Override
        public void run() {
            for(AbstractPlayer p : players.values()){
                try {
                    dispatchToPlayer(p);
                    Thread.sleep(10000);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

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

    public void setStack(){
        for(AbstractPlayer p : players.values()){
            if(p.disconnected==false)
                stack.push(p);
        }
    }

    public void playerDisconnected(AbstractPlayer player){
        player.disconnected=true;
        //TODO player disconnesso ritorna
    }

    public void marketEvent(AbstractPlayer abstractPlayer){
        if (turn!=null && turn.getPlayer().equals(abstractPlayer))
            System.out.println("chiamo il metodo vero e proprio");
    }

    public void notifyPlayerMadeAMove(){
        turn.playerMadeAMove();
    }

    public void notifyEndTurn(){
        turn.endTurn();
    }

}