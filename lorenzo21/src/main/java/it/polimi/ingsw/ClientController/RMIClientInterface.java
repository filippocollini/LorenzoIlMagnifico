package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.GameModelServer.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Interface that permits the client to interface with the server
 */
public interface RMIClientInterface<M> extends Remote {
    public static final String NAME = "CallbackClientIntf";

    public void notifyPlayerLogged(int logged) throws RemoteException;
    public void notify2(long val1, long val2) throws RemoteException;
    public void dispatchMessage(M msg) throws RemoteException;
    public void notifyGameStarted(Game game) throws RemoteException;
    public void notifyProva() throws RemoteException;
    public void notifyTurnStarted() throws RemoteException;
    public void notifyActionMade() throws RemoteException;
    public void notifyFMTooLow(int nServants, String event) throws RemoteException;
    public void notifyChooseFavor(String event) throws RemoteException;
    public void notifyNotEnoughResources() throws RemoteException;
    public void askForServants() throws RemoteException;
    public void notifyEndTurn() throws RemoteException;
    public void notifyError() throws RemoteException;
    public void notifyProductionChoice(String choice, String uuid) throws RemoteException;
    public void notifyFreeTowerAction(String color) throws RemoteException;
    public void print(StringBuilder s) throws RemoteException;
    public void dispatchGameEnded() throws RemoteException;
}
