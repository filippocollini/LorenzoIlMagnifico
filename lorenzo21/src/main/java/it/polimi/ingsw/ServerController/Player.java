package it.polimi.ingsw.ServerController;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 30/06/17.
 */
public class Player implements PlayerInterface{

    @Override
    public void dispatchMessage(Object msg) throws RemoteException {

    }

    @Override
    public void notifyCountdownStarted(Object msg) throws RemoteException {

    }
}
