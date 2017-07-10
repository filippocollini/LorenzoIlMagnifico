package it.polimi.ingsw.ServerController.states;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 04/07/17.
 */
public interface State {
    public void print();
    public String handle(String request, AbstractClient client, String uuid) throws RemoteException;
}
