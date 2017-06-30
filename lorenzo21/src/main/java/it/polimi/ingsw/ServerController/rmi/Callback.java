package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.ServerController.Stanza;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 23/06/17.
 */
public interface Callback extends Remote{
    public static final String NAME = "Callback";
    public static final int FAILURE = -1;
    public static final int SUCCESS = 0;

    public int joinPlayer(String username, RMIClientInterface client) throws RemoteException;
    public String sendObject(Object Obj)throws RemoteException;
    public String sendLong(long val) throws RemoteException, Exception;

}
