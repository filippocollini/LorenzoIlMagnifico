package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 09/07/17.
 */
public interface PowerUpState extends State {
    public void handlePowerUp(String request);

}
