package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 08/07/17.
 */
public class ChooseFavorState implements State {
    Rules rules;

    public ChooseFavorState(){
        rules = new Rules();
    }

    @Override
    public void print() {

    }

    @Override
    public String handle(String request, AbstractClient client, String uuid) throws RemoteException {
        Event event = rules.eventMap.get(request);
        event.eventHappened(client, uuid);
        return "ok";
    }
}
