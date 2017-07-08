package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.State;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 */
public class RMIPlayer <M extends Serializable> extends AbstractPlayer<M> implements Remote {

    private RMIClientInterface playerInt;

    /**
     * Default constructor
     * @param playerInt
     */
    public RMIPlayer(RMIClientInterface playerInt) {
        this.playerInt=playerInt;
    }

    @Override
    public void dispatchGameSettings(Game game) throws RemoteException {
        playerInt.notifyGameStarted(game);
    }

    @Override
    public void dispatchEsempio() throws RemoteException {
        playerInt.notifyProva();
    }

    @Override
    public void notifyTurnStarted() throws RemoteException {
        playerInt.notifyTurnStarted();
    }

    @Override
    public void notifyActionMade() throws RemoteException {
        playerInt.notifyActionMade();
    }

    @Override
    public void notifyFMTooLow(int nServants, String event) throws RemoteException {
        playerInt.notifyFMTooLow(nServants, event);
    }

    @Override
    public void notifyChooseFavor() throws RemoteException {
        playerInt.notifyChooseFavor();
    }

    @Override
    public void notifyNotEnoughResources() throws RemoteException {

    }

    @Override
    public void askForServants() throws RemoteException {

    }

    @Override
    public void notifyEndTurn() throws RemoteException {
        playerInt.notifyEndTurn();
    }

    @Override
    public void notifyError() throws RemoteException {
        playerInt.notifyError();
    }

    @Override
    public M receive() {
        return null;
    }

    @Override
    public void send(M message, State state) {

    }
}