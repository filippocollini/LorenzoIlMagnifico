package it.polimi.ingsw.ServerController;

import java.rmi.RemoteException;

/**
 * Send server notifications to the cient
 */
public interface PlayerInterface<M> {
    public void dispatchMessage(M msg) throws RemoteException;
    public void notifyCountdownStarted(M msg) throws RemoteException;
}
