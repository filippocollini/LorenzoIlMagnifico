package it.polimi.ingsw.GameModelServer;

import it.polimi.ingsw.Exceptions.FileMalformedException;

import java.util.*;

/**
 * 
 */
public class VentureCard extends DevelopementCard {

   public VentureCard() throws FileMalformedException {
       super();
       cost1 = new ArrayList<>();
   }
    private List<Risorsa> cost1;
    private boolean choice;


    @Override
    public List<Risorsa> getCost1() {
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

        for(GetResourcesIf effect : resif){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostDice effect : boost){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetVPEnd effect : getvp){
            if(effect.getId() == id)
                righteffect = effect;
        }
        return righteffect;
    }
}