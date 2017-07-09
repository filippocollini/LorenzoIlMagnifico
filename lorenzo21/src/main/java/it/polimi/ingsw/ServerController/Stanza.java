package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.Exceptions.FileMalformedException;
import it.polimi.ingsw.Exceptions.NetworkException;
import it.polimi.ingsw.GameModelServer.Game;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class Stanza implements Serializable {

    private static final Logger LOG = Logger.getLogger(Stanza.class.getName());

    /**
     * Default constructor
     */
    public Stanza() {
        players= new HashMap<>();
        usernames= new HashMap<>();
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

    public HashMap<AbstractPlayer, String> usernames = null;
    /**
     *
     */
    private Game game;

    /**
     *
     */
    private boolean timerStarted;

    public boolean matchStarted;

    private transient TurnHandler turnHandler;

    private transient PlayerTurn turn;

    private boolean endTurn=false;



    public void joinPlayer(AbstractPlayer player, String username) {
        players.put(username, player);
        usernames.put(player, username);
        if(players.size()>1 && !timerStarted) {
            timer = new Timer();
            timer.schedule(new GameHandler(), 10*1000L);
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
                timer.schedule(new GameHandler(), 10*1000L);
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

    public void palaceEvent(AbstractPlayer player, String member, String favor) {
        String control="";
        if (turn!=null && turn.getPlayer()==player){
            control = game.addFMonPalace(game.herethePlayer(usernames.get(player)), member, favor);
        }
        if (control.equals(Game.SUCCESS)){
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else
            notifyError();
    }

    public void harvestEvent(AbstractPlayer player, String member) {
        String control="";
        if (turn!=null && turn.getPlayer()==player){
            control = game.addFMonHarvest(game.herethePlayer(usernames.get(player)), member);
        }
        if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on harvest");
            }
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else if(control.equals(Game.FMPRESENT)){//TODO LUDOVICOARIOSTO
            notifyError();
        }else
            notifyFMTooLow(Integer.parseInt(control), "fm on harvest");
    }

    public void productionEvent(AbstractPlayer player, String member, List<Integer> choices) {
        String control="";
        if (turn!=null && turn.getPlayer()==player){
            control = game.addFMonProduction(game.herethePlayer(usernames.get(player)), member, choices);
        }else if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on production");
            }
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else if(control.equals(Game.FAIL))
            notifyError();
        else{
            notifyProductionChoice(player, control);
        }
    }

    public void powerUpEvent(AbstractPlayer player, String member, int nServants) {
        if (turn!=null && turn.getPlayer()==player){
            //TODO PULLARE il metodo
        }
    }

    private class GameHandler extends TimerTask{

        @Override
        public void run() {
            System.out.println("start");
            configuration();
            System.out.println("creato tutto");
            //dispatchGameToPlayers();
            try {
                dispatchProva();
            } catch (NetworkException e) {
                LOG.log(Level.SEVERE, "Cannot reach the client", e);
            }
            //timer.schedule(new TurnHandler(), 10*1000L);
            //turnHandler.run();
            setStack();
            for(int i=0;i<3;i++){
                System.out.println("Era: "+(i+1));
                for(int j=0;j<2;j++){
                    setBoard();
                    game.fillTowers(j+1);
                    System.out.println("Turno: "+(j+1));
                    AbstractPlayer p = (AbstractPlayer) stack.pop();
                    System.out.println("turno iniziatooooooooooo");
                    try {
                        startPlayerTurn(p);
                    } catch (NetworkException e) {
                        LOG.log(Level.SEVERE, "Cannot start player's turn", e);
                    }
                    try {
                        synchronized (Stanza.this){
                            Stanza.this.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if(stack.isEmpty()){
                        setStack();
                    }
                }
            }
            /*for(AbstractPlayer p = (AbstractPlayer) stack.pop(); stack.size()==1;p= (AbstractPlayer) stack.pop()){
                System.out.println("turno iniziatooooooooooo");
                startPlayerTurn(p);
                try {
                    synchronized (Stanza.this){
                        Stanza.this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ci arrivo?");
            }*/

            //TODO turni e ere

            /*int i = 0;
            while(i<2){
                System.out.println("turno iniziatooooooooooo");
                AbstractPlayer p = (AbstractPlayer) stack.pop();
                startPlayerTurn(p);
                try {
                    synchronized (Stanza.this){
                        Stanza.this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }*/

        }


    }

    private void setBoard(){
        game.getBoard().setDices(game.rollDices(game.getBoard().getDices()));
    }

    private void startPlayerTurn(AbstractPlayer p) throws NetworkException {
        this.turn = new PlayerTurn(p, this);
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
        try {
            this.game=new Game(players, this);
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse some files", e);

        }
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

    private void dispatchProva() throws NetworkException {
        for (AbstractPlayer p : players.values()){
            try {
                p.dispatchEsempio();
            } catch (RemoteException e) {
                LOG.log(Level.SEVERE, "Cannot reach the client", e);
                throw new NetworkException("Cannot reach the client");
            }
        }
    }

    public void setStack(){
        for(AbstractPlayer p : players.values()){
            if(p.disconnected==false)
                stack.push(p);
        }
        //TODO modificare token nella board
    }

    public void playerDisconnected(AbstractPlayer player){
        player.disconnected=true;
        //TODO player disconnesso ritorna
    }

    public void marketEvent(AbstractPlayer abstractPlayer, String member, String cell){
        String control="";
        if (turn!=null && turn.getPlayer()==abstractPlayer){
            control = game.addFMonMarket(game.herethePlayer(usernames.get(abstractPlayer)), member, cell);
        }
        if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on harvest");
            }
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else
            notifyError();

    }

    public void towerEvent(AbstractPlayer abstractPlayer, String member, String tower, int floor, boolean free){
        String control="";
        if (turn!=null && turn.getPlayer()==abstractPlayer){
            control = game.addFMonTowerControl(game.herethePlayer(usernames.get(abstractPlayer)), member, tower, floor, free);
        }
        if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on harvest");
            }
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else
            if(control.equals(Game.FAIL) || control.equals(Game.SAMETOWER)) {
                notifyError();
            }else if (control.equals(Game.NOTENOUGHRESOURCES))
                notifyNotEnoughResources();
            else if(control.equalsIgnoreCase("color"))
                notifyAnotherTowerAction("color");
            else if (control.equalsIgnoreCase("territory"))
                notifyAnotherTowerAction("territory");
            else if (control.equalsIgnoreCase("ventures"))
                notifyAnotherTowerAction("ventures");
            else if (control.equalsIgnoreCase("buildings"))
                notifyAnotherTowerAction("buildings");
            else if (control.equalsIgnoreCase("characters"))
                notifyAnotherTowerAction("characters");
            else
                notifyFMTooLow(Integer.parseInt(control), "fm on tower");

    }

    private void notifyAnotherTowerAction(String color) {
        try {
            turn.getPlayer().notifyFreeTowerAction(color);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

    public void choiceEvent(AbstractPlayer abstractPlayer, String choice){
        String control;
        if (turn!=null && turn.getPlayer()==abstractPlayer){
            control="";
        }
            

    }

    public void endEvent(AbstractPlayer abstractPlayer){
        if (turn!=null && turn.getPlayer()==abstractPlayer)
            System.out.println(abstractPlayer.toString()+" ha finito il turno");
        notifyEndTurn();
        synchronized (Stanza.this){
            Stanza.this.notify();
        }
    }

    public void notifyPlayerMadeAMove(){
        turn.playerMadeAMove();
    }

    public void notifyFMTooLow(int nServants, String event){
        try {
            turn.getPlayer().notifyFMTooLow(nServants, event);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    public void notifyChooseFavor(String event){
        try {
            turn.getPlayer().notifyChooseFavor(event);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    public void notifyProductionChoice(AbstractPlayer player, String choice){
        try {
            turn.getPlayer().notifyProductioChoice(choice, usernames.get(player));
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    public void notifyNotEnoughResources(){

    }

    public void askForServants(){

    }



    public void notifyActionMade(){
        try {
            turn.getPlayer().notifyActionMade();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
        //notifico che ha fatto l'azione, poi nella cli entrerà nello stato in cui potrà solo vedere la board
        //oppure finire il turno
        //forse da mettere insieme a notifyPlayerMadeAMove
    }

    public void notifyEndTurn(){
        turn.endTurn();
        try {
            turn.getPlayer().notifyEndTurn();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

    public void notifyError(){
        try {
            turn.getPlayer().notifyError();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

}