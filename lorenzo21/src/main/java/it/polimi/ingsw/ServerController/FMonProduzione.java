package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by filippocollini on 26/05/17.
 */
public class FMonProduzione implements Event {
    public void eventHappened(Event event){

    }

    @Override
    public boolean isLegal(AbstractClient client, String uuid) {
        return true;
    }

    @Override
    public void eventHappened(AbstractClient client, String uuid) throws RemoteException {
        client.productionMove(uuid, new ArrayList<Integer>());

    }
}
