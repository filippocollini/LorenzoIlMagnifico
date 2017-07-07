package it.polimi.ingsw.GameModelServer;

import it.polimi.ingsw.Exceptions.FileMalformedException;

import java.util.*;

/**
 * 
 */
public class CharacterCard extends DevelopementCard {

    private List<Risorsa> cost;
    private boolean choice;

    public CharacterCard() throws FileMalformedException {
        super();
        cost = new ArrayList<>();
    }

    public List<Risorsa> getCost1(){
        return cost;
    }

    public boolean getChoice(){
        return choice;
    }

    public void setCost1(List<Risorsa> cost1){
        this.cost = cost1;
    }

    public void setChoice(boolean choice){
        this.choice = choice;
    }

    public List<DevelopementCard> getCharacterCard(ArrayList<DevelopementCard> listcard) {
        int i;
        List<DevelopementCard> charactcard = new ArrayList<DevelopementCard>();

        for (i = 0; i < listcard.size(); i++) {
            if (listcard.get(i).getCardtype() == "characters") {
                charactcard.add(i, listcard.get(i));
            }
        }
        return charactcard;
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

