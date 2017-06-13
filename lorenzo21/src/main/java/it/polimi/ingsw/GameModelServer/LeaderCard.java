package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class LeaderCard extends Card {

    String name;
    List<Risorsa> requisiti;
    int effetto;

    public LeaderCard() {
        requisiti = new ArrayList<Risorsa>();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEffetto(int effetto){
        this.effetto = effetto;
    }

    public void setRequisiti(List<Risorsa> requisiti){
        this.requisiti = requisiti;
    }

    @Override
    public void activateEffect(Effect effect){

    }

}