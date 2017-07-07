package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Effect {

    /**
     * Default constructor
     */
    public Effect() {
        strategy = new ArrayList<>();
    }


    private List<EffectStrategy> strategy;



    public List<EffectStrategy> getStrategy() {
        return strategy;
    }




}