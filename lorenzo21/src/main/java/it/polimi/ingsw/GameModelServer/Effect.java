package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Effect {

    /**
     * Default constructor
     */
    public Effect(EffectStrategy strategy) {
        this.strategy=strategy;
        if((int) strategy.getId()/1000 == 2)
            this.permanent=true;
        else
            this.permanent=false;
    }


    private EffectStrategy strategy;
    private boolean permanent;

    public void setStrategy(EffectStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    public EffectStrategy getStrategy() {
        return strategy;
    }

    public boolean getPermanent(){
        return permanent;
    }

}