package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 */
public abstract class Deck {

    /**
     * Default constructor
     */
    public Deck() {
        carte= new ArrayList<Card>();
    }

    /**
     * 
     */
    private List<Card> carte;


    /**
     *
     */
    public void mescola() {
        Collections.shuffle(carte);
    }

}