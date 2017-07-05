package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class TerritoryCard extends DevelopementCard {


    public TerritoryCard() {
        super();
    }
    @Override
    public EffectStrategy activateEffect(int id){
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

        for(GetResourcesIf effect : resif){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostDice effect : boost){
            if(effect.getId() == id)
                righteffect = effect;
        }


        return righteffect;
    }

    @Override
    public String getName() {
        return super.getName();
    }
}