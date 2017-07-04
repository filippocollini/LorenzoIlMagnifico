package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 26/05/17.
 */
public interface Event extends Serializable {

    public boolean isLegal();
    public void eventHappened(AbstractClient client, String uuid) throws RemoteException;
}
