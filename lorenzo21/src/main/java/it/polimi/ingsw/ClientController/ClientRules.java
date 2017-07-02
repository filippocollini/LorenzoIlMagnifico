package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.EventInputStream;
import it.polimi.ingsw.ServerController.EventOutputStream;
import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.TurnHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

/**
 * Created by filippocollini on 11/06/17.
 */
public class ClientRules {

    private final EventInputStream in;

    private final EventOutputStream out;

    private final ClientRules clientRules;

    private final HashMap<String, Handler> eventMap;

    private TurnHandler turn;

    public ClientRules(EventInputStream in, EventOutputStream out, ClientRules clientRules){
        this.in=in;
        this.out=out;
        this.clientRules=clientRules;
        eventMap= new HashMap<>();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.ESEMPIO_REPLY, this::esempio);

    }

    public void esempio(){

    }

    //----------------------------------------------------------


    public void responseHandler(Object object) {
        Handler handler = eventMap.get(object);
        if (handler != null) {
            handler.handle();
        }
    }

    @FunctionalInterface
    private interface Handler {
        void handle();
    }

}
