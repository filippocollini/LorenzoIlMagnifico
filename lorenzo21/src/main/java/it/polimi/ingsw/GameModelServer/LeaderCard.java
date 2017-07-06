package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class LeaderCard extends Card {

    protected String name;
    protected List<Risorsa> requires;


    public LeaderCard() {
        requires = new ArrayList<>();
    }

    @Override
    public EffectStrategy activateEffect(int id) {
        return null;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRequisiti(List<Risorsa> requisiti){
        this.requires = requisiti;
    }




}