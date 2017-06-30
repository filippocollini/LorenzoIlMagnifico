package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class BuildingCard extends DevelopementCard {

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

    public BuildingCard(){
        super();
        cost1 = new ArrayList<>();
    }


    @Override
    public void activateEffect(int id) {
        super.activateEffect(id);
    }
}