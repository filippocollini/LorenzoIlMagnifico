package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.net.SocketException;
import java.rmi.RemoteException;

/**
 * 
 */
public abstract class AbstractPlayer<M> /*extends it.polimi.ingsw.GameModelServer.Player*/ {

    private Stanza room;

    public boolean disconnected = false;


    public abstract void dispatchGameSettings(Game game) throws RemoteException;

    public abstract void dispatchEsempio() throws RemoteException;

    public abstract void notifyTurnStarted() throws RemoteException;

    public abstract void notifyActionMade() throws RemoteException;

    public abstract void notifyFMTooLow(int nServants, String event) throws RemoteException;

    public abstract void notifyChooseFavor(String event) throws RemoteException;

    public abstract void notifyNotEnoughResources() throws RemoteException;

    public abstract void askForServants() throws RemoteException;

    public abstract void notifyEndTurn() throws RemoteException;

    public abstract void notifyError() throws RemoteException;

    public abstract void notifyFreeTowerAction(String color) throws RemoteException;

    public abstract void notifyProductioChoice(String choice, String uuid) throws RemoteException;
    /**
     * 
     */
    public void setRoom(Stanza room) {
        this.room=room;
    }

    public Stanza getRoom() {
        return room;
    }

    public abstract M receive() throws SocketException;
    public abstract void send(M message, State state);


}