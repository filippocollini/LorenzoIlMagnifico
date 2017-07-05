package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

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
    public void handle(String request, AbstractClient client, String uuid) throws RemoteException {
        handleShowRequest(request, client, uuid);
    }

    public void handleShowRequest(String request, AbstractClient client, String uuid) throws RemoteException {
        Event event = rules.eventMap.get(request);
        if(event!=null){
            System.out.println("trovato l'evento");
            if(event.isLegal(client, uuid))//anche qua passo il client?
                event.eventHappened(client, uuid);
            //TODO metodi nel game che devo chiamre?
        } else
            System.out.println("evento non trovato");
            //TODO notifyError
    }
}
