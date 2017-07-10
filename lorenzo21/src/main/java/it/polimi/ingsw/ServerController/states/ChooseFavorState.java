package it.polimi.ingsw.ServerController.states;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ServerController.Events.Event;
import it.polimi.ingsw.ServerController.Rules;

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
