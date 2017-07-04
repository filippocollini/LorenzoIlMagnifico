package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 04/07/17.
 */
public interface State {
    public void print();
    public void handle(String request, AbstractClient client, String uuid) throws RemoteException;
}
