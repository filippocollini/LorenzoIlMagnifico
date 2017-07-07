package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 04/07/17.
 */
public class GameState implements State, GeneralMoveState {

    Rules rules;

    public GameState(){
        rules = new Rules();
    }

    @Override
    public void print() {

    }

    @Override
    public void handle(String request, AbstractClient client, String uuid) throws RemoteException {
        System.out.println("gestisco");
        handleGeneralRequest(request, client, uuid);
    }

    public void handleGeneralRequest(String request, AbstractClient client, String uuid) throws RemoteException {
        Event event = rules.eventMap.get(request);
        if(event!=null)
            System.out.println("trovato l'evento");
        else
            System.out.println("evento non trovato");

        if(event.isLegal(client, uuid))//anche qua passo il client?
            event.eventHappened(client, uuid);
    }
}
