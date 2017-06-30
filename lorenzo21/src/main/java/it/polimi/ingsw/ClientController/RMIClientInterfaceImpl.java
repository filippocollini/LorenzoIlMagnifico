package it.polimi.ingsw.ClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by filippocollini on 29/06/17.
 */
public class RMIClientInterfaceImpl extends UnicastRemoteObject implements RMIClientInterface {
    protected RMIClientInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public void notifyPlayerLogged(int logged) throws RemoteException {
        System.out.println("loggato: "+logged);
    }

    @Override
    public void notify2(long val1, long val2) throws RemoteException, Exception {

    }
}
