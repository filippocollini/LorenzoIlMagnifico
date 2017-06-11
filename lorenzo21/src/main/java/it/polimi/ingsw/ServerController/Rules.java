package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.ClientRules;

import java.util.HashMap;

/**
 * 
 */
public class Rules {

    private final EventInputStream in;

    private final EventOutputStream out;

    private final ClientRules clientRules;

    private final HashMap <String, Event> eventMap;

    private TurnHandler turn;

    /**
     * Default constructor
     * @param in
     * @param out
     * @param clientRules
     */
    public Rules(EventInputStream in, EventOutputStream out, ClientRules clientRules) {
        this.in = in;
        this.out = out;
        this.clientRules = clientRules;
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.FMONMARKET, new FMonMarket());
        eventMap.put(Message.LOGIN, new LoginRequest());

    }

    public void handleRequest(String request){
        Event event = eventMap.get(request);
        if(event.isLegal())
            event.eventHappened();
    }

}