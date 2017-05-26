package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Cell {

    /**
     * Default constructor
     */
    public Cell() {
    }

    /**
     * 
     */
    private boolean fMIsPresent;

    /**
     * 
     */
    private Card carta;

    /**
     * 
     */
    public Risorsa bonus;


    public boolean isfMPresent(){
        return fMIsPresent;
    }

    public Card getCard(){
        return carta;
    }

    public Risorsa getResourceBonus(){
        return bonus;
    }

}