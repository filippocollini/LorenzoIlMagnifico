package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.PlayerInterface;

import java.io.Serializable;
import java.net.SocketException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

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
    public M receive() {
        return null;
    }

    @Override
    public void send(M message) {

    }
}