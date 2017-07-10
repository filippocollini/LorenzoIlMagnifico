package it.polimi.ingsw.ServerController.Events;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ServerController.Events.Event;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 05/07/17.
 */
public class EndTurn implements Event {

    @Override
    public boolean isLegal(AbstractClient client, String uuid) {
        return true;
    }

    @Override
    public void eventHappened(AbstractClient client, String uuid) throws RemoteException {
        System.out.println("end turn");
        client.endMove(uuid);
    }
}
