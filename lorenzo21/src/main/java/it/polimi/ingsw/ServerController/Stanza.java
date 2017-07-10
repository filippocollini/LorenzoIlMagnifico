package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.Exceptions.FileMalformedException;
import it.polimi.ingsw.Exceptions.NetworkException;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.GameModelServer.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This class represents the room that contains the players that are playing the game
 */
public class Stanza implements Serializable {

    private static final Logger LOG = Logger.getLogger(Stanza.class.getName());

    /**
     * Default constructor
     */
    public Stanza() {
        players= new HashMap<>();
        usernames= new HashMap<>();
        timerStarted = false;
        matchStarted= false;

    }

    /**
     * Timer to handle players turns
     */
    private transient Timer timer;

    /**
     * Constant that represent the maximum number of players in a match
     */
    public static final int MAXPLAYERS = 4;

    /**
     * HashMap that maps usernames and abstractPlayers
     */
    public HashMap<String, AbstractPlayer> players = null;

    /**
     * HashMap that maps abstractPlayers and usernames
     */
    public HashMap<AbstractPlayer, String> usernames = null;

    /**
     * This represents the match played by players of this room
     */
    private Game game;

    /**
     * This boolean is used to handle the timer when a new player join the game
     */
    private boolean timerStarted;

    /**
     * This boolean is used to handle the timer when the number of player has reached MAXPLAYERS
     */
    public boolean matchStarted;

    /**
     * This handles the turn and is use to check that a player can make an action only if it's his turn
     */
    private transient PlayerTurn turn;

    private boolean endTurn=false;

    /**
     * This is the list that represents the order of the players for the current turn and it will be updated
     * checking the tokens on the palace
     */
    private List<Player> playersInOrder;

    /**
     * This method join the player to the game
     */
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

    /**
     * This method returns the number of the players in the room
     */
    public int nPlayers(){
        return players.size();
    }

    /**
     * This method permits to put the family member (real or ghost member) into the palace
     */
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

    /**
     * This method permits to put the family member (real or ghost member) in the harvest space to make an
     * harvest action
     */
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

    /**
     * This method permits to put the family member (real or ghost member) in the production space to make a
     * production action
     */
    public void productionEvent(AbstractPlayer player, String member, List<Integer> choices) {
        String control="";
        if (turn!=null && turn.getPlayer()==player){
            control = game.addFMonProduction(game.herethePlayer(usernames.get(player)), member, choices);
        }
        if (control.equals(Game.SUCCESS)){
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

    /**
     * This method permits to power up the value of the family member spending an amount of servants
     */
    public void powerUpEvent(AbstractPlayer player, String member, int nServants) {
        String control = "";
        if (turn!=null && turn.getPlayer()==player){
            control = game.spendservants(game.herethePlayer(usernames.get(player)), member, nServants);
        }
        if(control.equalsIgnoreCase("FAIL"))
            notifyError();
    }

    /**
     * This method permits to play a leader card and activate its effect
     */
    public void leaderMove(AbstractPlayer player, String card) {
        String control = "";
        if (turn!=null && turn.getPlayer()==player){
            control = game.activeLeaderCard(game.herethePlayer(usernames.get(player)), card);
        }
        if(control.equalsIgnoreCase("FAIL"))
            notifyError();

    }

    /**
     * This method permits to discart a leader card to receive a palace favor
     */
    public void discardLeaderCard(AbstractPlayer player, String card, String favor) {
        String control = "";
        if (turn!=null && turn.getPlayer()==player){
            control = game.discardLeaderCard(game.herethePlayer(usernames.get(player)), card, favor);
        }
        if(control.equalsIgnoreCase("FAIL"))
            notifyError();
    }

    /**
     * This method permits to see the player's resources and points
     */
    public void showPlayerGoods(AbstractPlayer player) {
        StringBuilder control= null;
        if (turn != null && turn.getPlayer() == player) {
            control = game.getPlayer(game.herethePlayer(usernames.get(player)).getColor()).showPlayergoods();
        }
            notifyPrint(control);
    }

    /**
     * This method permits to see the others player's resources and points
     */
    public void showOtherPlayers(AbstractPlayer player) {
        StringBuilder control= null;
        if (turn != null && turn.getPlayer() == player) {
            control = game.showotherPlayers();
        }
        notifyPrint(control);
    }

    /**
     * This method permits to see the board
     */
    public void showBoard(AbstractPlayer player) {
        StringBuilder control= null;
        if (turn != null && turn.getPlayer() == player) {
            control = game.getBoard().showBoard();
        }
        notifyPrint(control);
    }


    /**
     * This class permits to handle the turns
     */
    private class GameHandler extends TimerTask{

        @Override
        public void run() {
            System.out.println("start");
            configuration();
            try {
                dispatchGameToPlayers();
            } catch (NetworkException e) {
                LOG.log(Level.SEVERE, "Cannot reach the client", e);
            }

            for(int i=0;i<3;i++){
                System.out.println("Era: "+(i+1));
                for(int j=0;j<2;j++){
                    setBoard();
                    game.fillTowers(j+1);
                    if (i==0 && j==0)
                        playersInOrder = game.setOrderFirstTurn();
                    else
                        playersInOrder = game.reOrder(playersInOrder,game.getBoard());
                    System.out.println("Turno: "+(j+1));
                    for(int k = 0; k<4; k++){
                        AbstractPlayer p = players.get(playersInOrder.get(k).getUsername());
                        try {
                            if (false==p.disconnected)
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
                    }
                    game.resetBoard();
                }
            }

        }


    }

    /**
     * This method sets the board at the beginning of the game
     */
    private void setBoard(){
        game.getBoard().setDices(game.rollDices(game.getBoard().getDices()));
    }

    /**
     * This method let the player start the turn
     */
    private void startPlayerTurn(AbstractPlayer p) throws NetworkException {
        this.turn = new PlayerTurn(p, this);
    }

    /**
     * This method configures all the settings from the file
     */
    private void configuration(){
        try {
            this.game=new Game(players, this);
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse some files", e);

        }
    }

    /**
     * This method dispatch the game to the players
     */
    private void dispatchGameToPlayers() throws NetworkException {
        for (AbstractPlayer p : players.values()){
            try {
                p.dispatchGameSettings(game);
            } catch (RemoteException e) {
                LOG.log(Level.SEVERE, "Cannot reach the client", e);
                throw new NetworkException("Cannot reach the client");
            }
        }
    }

    /**
     * This method disconnect a player for inactivity
     */
    public void playerDisconnected(AbstractPlayer player){
        player.disconnected=true;
    }

    /**
     * This method permits to put the family member in the market space to choose the resource he wants to get
     */
    public void marketEvent(AbstractPlayer abstractPlayer, String member, String cell){
        String control="";
        if (turn!=null && turn.getPlayer()==abstractPlayer){
            control = game.addFMonMarket(game.herethePlayer(usernames.get(abstractPlayer)), member, cell);
        }
        if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on market");
            }
            notifyPlayerMadeAMove();
            notifyActionMade();
        }else
            notifyError();

    }

    /**
     * This method permits to put the family member (real or ghost member) in one of the tower's spaces
     * to take a card, get the eventual bonus and activate the card's effect
     */
    public void towerEvent(AbstractPlayer abstractPlayer, String member, String tower, int floor, boolean free){
        String control="";
        if (turn!=null && turn.getPlayer()==abstractPlayer){
            control = game.addFMonTowerControl(game.herethePlayer(usernames.get(abstractPlayer)), member, tower, floor, free);
        }
        if (control.equals(Game.SUCCESS)){
            if(Game.PALACE!=0){
                for (; Game.PALACE>0;Game.PALACE--)
                    notifyChooseFavor("fm on tower");
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

    /**
     * This method notifies that the player can make another tower action
     */
    private void notifyAnotherTowerAction(String color) {
        try {
            turn.getPlayer().notifyFreeTowerAction(color);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

    /**
     * This method will notify the client to print what it pass to him following his request
     */
    private void notifyPrint(StringBuilder s) {
        try {
            turn.getPlayer().print(s);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

    /**
     * This method will notify the client that his turn is ended
     */
    public void endEvent(AbstractPlayer abstractPlayer){
        if (turn!=null && turn.getPlayer()==abstractPlayer)
            System.out.println(abstractPlayer.toString()+" ha finito il turno");
        notifyEndTurn();
        synchronized (Stanza.this){
            Stanza.this.notify();
        }
    }

    /**
     * This method will notify the turn that the player has made a move and it should not be disconnected
     */
    public void notifyPlayerMadeAMove(){
        turn.playerMadeAMove();
    }

    /**
     * This method will notify the client that the family member that he choose has not enough power
     */
    public void notifyFMTooLow(int nServants, String event){
        try {
            turn.getPlayer().notifyFMTooLow(nServants, event);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    /**
     * This method will notify the client to choose a palace favor
     */
    public void notifyChooseFavor(String event){
        try {
            turn.getPlayer().notifyChooseFavor(event);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    /**
     * This method will notify the client to make a choice for a building card for a production
     */
    public void notifyProductionChoice(AbstractPlayer player, String choice){
        try {
            turn.getPlayer().notifyProductioChoice(choice, usernames.get(player));
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    /**
     * This method will notify the client that he has not enough resources to make that action
     */
    public void notifyNotEnoughResources(){
        try {
            turn.getPlayer().notifyNotEnoughResources();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }

    }

    /**
     * This method will notify the client that he has made a move and he will not be able to perform another
     * game move
     */
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

    /**
     * This method will notify the client that he has ended his turn
     */
    public void notifyEndTurn(){
        turn.endTurn();
        try {
            turn.getPlayer().notifyEndTurn();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

    /**
     * This method will notify the client that an error occurs and he should try another action
     */
    public void notifyError(){
        try {
            turn.getPlayer().notifyError();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }

}