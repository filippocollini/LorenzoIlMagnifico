package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class LeaderCard extends Card {

    String name;
    List<Risorsa> requisiti;


    public LeaderCard() {
        requisiti = new ArrayList<>();
    }

    @Override
    public EffectStrategy activateEffect(int id) {
        return null;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRequisiti(List<Risorsa> requisiti){
        this.requisiti = requisiti;
    }




}