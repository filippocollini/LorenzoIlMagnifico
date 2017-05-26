package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public abstract class DevelopementCard extends Card {

    /**
     * Default constructor
     */
    public DevelopementCard() {
        cost= new ArrayList<Risorsa>();
    }

    /**
     * 
     */
    private String nome;

    /**
     * 
     */
    private int number;

    /**
     * 
     */
    private int period;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private List cost;

    /**
     * 
     */
    private Effect immediateEffect;

    /**
     * 
     */
    private Effect permanentEffect;

    /**
     * 
     */
    private String description;

    public String getNome(){
        return nome;
    }

    public int getNumber(){
        return number;
    }

    public int getPeriod(){
        return period;
    }

    public String getType(){
        return type;
    }

    public String getDescription(){
        return description;
    }

    public Effect getImmediateEffect(){
        return immediateEffect;
    }

    public Effect getPermanentEffect(){
        return permanentEffect;
    }

}