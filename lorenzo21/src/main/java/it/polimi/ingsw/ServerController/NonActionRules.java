package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.util.HashMap;

/**
 * Created by filippocollini on 05/07/17.
 */
public class NonActionRules {

    public final HashMap<String, Event> eventMap;

    private TurnHandler turn;

    /**
     * Default constructor
     */
    public NonActionRules() {
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.ENDTURN, new EndTurn());


    }

    public void notifyQualcosa(){
        System.out.println("");

    }
}