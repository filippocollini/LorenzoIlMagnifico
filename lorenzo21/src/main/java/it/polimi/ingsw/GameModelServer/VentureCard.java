package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class VentureCard extends DevelopementCard {

   public VentureCard(){}
    private List<Risorsa> cost1;
    private boolean choice;


    public List<Risorsa> getCost1(){
        return cost1;
    }

    public boolean getChoice(){
        return choice;
    }

    public void setCost1(List<Risorsa> cost1){
        this.cost1 = cost1;
    }

    public void setChoice(boolean choice){
        this.choice = choice;
    }

    @Override
    public void activateEffect(Effect effect){

    }

}