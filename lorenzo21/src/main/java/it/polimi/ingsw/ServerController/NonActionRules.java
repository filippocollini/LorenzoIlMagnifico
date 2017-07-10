package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ServerController.Events.*;

import java.util.HashMap;

/**
 * Created by filippocollini on 05/07/17.
 */
public class NonActionRules {

    public final HashMap<String, Event> eventMap;

    /**
     * Default constructor
     */
    public NonActionRules() {
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.ENDTURN, new EndTurn());
        eventMap.put(Message.SHOWBOARD, new ShowBoard());
        eventMap.put(Message.SHOWOTHERPLAYERS, new ShowOtherPlayers());
        eventMap.put(Message.SHOWPLAYERGOODS, new ShowPlayerGoods());

    }

    public void notifyQualcosa(){
        System.out.println("");

    }
}
