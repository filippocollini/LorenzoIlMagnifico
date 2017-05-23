package it.polimi.ingsw.GameModelServer;

import java.util.List;

/**
 * 
 */
public abstract class DevelopementCard extends Card {

    /**
     * Default constructor
     */
    public DevelopementCard() {
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

}