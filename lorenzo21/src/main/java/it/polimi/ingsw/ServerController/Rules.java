package it.polimi.ingsw.ServerController;


import java.util.HashMap;

/**
 * 
 */
public class Rules implements RulesInterface{

    private final HashMap <String, Event> eventMap;

    private TurnHandler turn;

    /**
     * Default constructor
     * @param abstractPlayer
     */
    public Rules(AbstractPlayer abstractPlayer) {
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.FMONMARKET, new FMonMarket());



    }

    public void handleRequest(String request){
        Event event = eventMap.get(request);
        if(event!=null)
            System.out.println("trovato l'evento");
        else
            System.out.println("evento non trovato");
        if(event.isLegal())
            event.eventHappened();
    }

}