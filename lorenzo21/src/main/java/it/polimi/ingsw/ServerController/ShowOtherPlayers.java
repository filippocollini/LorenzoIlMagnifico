package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 09/07/17.
 */
public class ShowOtherPlayers implements Event {
    @Override
    public boolean isLegal(AbstractClient client, String uuid) {
        return false;
    }

    @Override
    public void eventHappened(AbstractClient client, String uuid) throws RemoteException {

    }
}