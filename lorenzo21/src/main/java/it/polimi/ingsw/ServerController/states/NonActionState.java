package it.polimi.ingsw.ServerController.states;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ServerController.Events.Event;
import it.polimi.ingsw.ServerController.NonActionRules;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 05/07/17.
 */
public class NonActionState implements State {

    NonActionRules rules;

    public NonActionState(){
        rules = new NonActionRules();
    }

    @Override
    public void print() {

    }

    @Override
    public String handle(String request, AbstractClient client, String uuid) throws RemoteException {
        return handleShowRequest(request, client, uuid);
    }

    public String handleShowRequest(String request, AbstractClient client, String uuid) throws RemoteException {
        Event event = rules.eventMap.get(request);
        if(event!=null){
            System.out.println("trovato l'evento");
            if(event.isLegal(client, uuid)){
                event.eventHappened(client, uuid);
            return "ok";}
        } else
            System.out.println("evento non trovato");
            return "ko";
    }
}
