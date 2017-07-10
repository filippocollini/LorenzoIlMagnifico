package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 04/07/17.
 */
public interface GeneralMoveState extends State{
    public String handleGeneralRequest(String request, AbstractClient client, String uuid) throws RemoteException;
}
