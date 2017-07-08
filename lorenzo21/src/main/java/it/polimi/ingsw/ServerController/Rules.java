package it.polimi.ingsw.ServerController;


import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.GameModelServer.Game;

import java.util.HashMap;

/**
 * 
 */
public class Rules implements RulesInterface{

    public final HashMap <String, Event> eventMap;

    private TurnHandler turn;

    /**
     * Default constructor
     */
    public Rules() {
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.FMONMARKET, new FMonMarket());
        eventMap.put(Message.FMONTOWER, new FMonTower());
        eventMap.put(Message.FMONPALACE, new FMonCouncilPalace());
        eventMap.put(Message.FMONHARVEST, new FMonRaccolto());
        eventMap.put(Message.FMONPRODUCTION, new FMonProduzione());

        eventMap.put(Message.ENDTURN, new EndTurn());

    }

    public void notifyGameConfigurationDone(Game game){
        System.out.println("la partita può iniziare");
        
    }

}