package it.polimi.ingsw.ClientController;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 23/06/17.
 */
public interface RMIClientInterface extends Remote {
    public static final String NAME = "CallbackClientIntf";

    public void notifyPlayerLogged(int logged) throws RemoteException;
    public void notify2(long val1, long val2) throws RemoteException, Exception;
}
