package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 26/05/17.
 */
public class FMonMarket implements Event {

    public FMonMarket(){
        
    }

    @Override
    public boolean isLegal(AbstractClient client, String uuid) {

        return true;
    }

    public void eventHappened(AbstractClient client, String uuid) throws RemoteException {
        System.out.println("market");
        client.marketMove(uuid);

    }
}
