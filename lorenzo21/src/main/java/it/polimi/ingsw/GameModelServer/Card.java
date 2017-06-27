package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public abstract class Card {

    /**
     * Default constructor
     */
    public Card() {
        effetti= new ArrayList<Effect>();
    }

    /**
     * 
     */
    public List<Effect> effetti;


    public abstract void activateEffect(Effect effect);


    public void activateEffects(){
        for(Effect e : effetti){
            activateEffect(e);
        }
    }



}