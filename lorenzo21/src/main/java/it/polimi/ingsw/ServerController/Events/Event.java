package it.polimi.ingsw.ServerController.Events;

import it.polimi.ingsw.ClientController.AbstractClient;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 26/05/17.
 */
public interface Event extends Serializable {

    @NotNull
    public boolean isLegal(AbstractClient client, String uuid);
    public void eventHappened(AbstractClient client, String uuid) throws RemoteException;
}
