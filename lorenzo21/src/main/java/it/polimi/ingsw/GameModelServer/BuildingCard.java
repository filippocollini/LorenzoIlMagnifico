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
    public EffectStrategy activateEffect(int id) {
        EffectStrategy righteffect = null;
        if(id==0)
            return righteffect;
        for(GetResources effect : getres){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetFreeAction effect : freeaction){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostandDiscount effect : discountandboost){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for (GetResourcesSelling effect : selling){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetResourcesIf effect : resif){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostDice effect : boost){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetForEach effect : resfor){
            if(effect.getId() == id)
                righteffect = effect;
        }
        return righteffect;
    }
}