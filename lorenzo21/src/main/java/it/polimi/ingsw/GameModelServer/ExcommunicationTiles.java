package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class ExcommunicationTiles extends Card {

    /**
     * Default constructor
     */
    public ExcommunicationTiles() {
    }

    /**
     * 
     */
    private int periodo;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Effect effect;


    public int getPeriod(){
        return periodo;
    }

    public String getDescription(){
        return description;
    }

    public Effect getEffect(){
        return effect;
    }

    @Override
    public void activateEffect(Effect effect) {

    }
}