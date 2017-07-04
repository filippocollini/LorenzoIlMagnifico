package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 23/06/17.
 */
public interface Callback extends Remote{
    public static final String NAME = "Callback";
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    public String joinPlayer(String username, RMIClientInterface client) throws RemoteException;
    public void sendRequest(String request)throws RemoteException;
    public void marketMove(String uuid) throws RemoteException;
    public void sendLong(long val) throws RemoteException, Exception;

}
