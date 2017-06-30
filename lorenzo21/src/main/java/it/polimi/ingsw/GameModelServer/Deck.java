package it.polimi.ingsw.GameModelServer;

import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 
 */
public abstract class Deck {

    /**
     * Default constructor
     */
    public Deck(List<Card> cards) {
        deck=cards;
    }

    /**
     * 
     */
    private List<Card> deck;
    private int turn;


    protected Deck() {
    }


    /**
     *
     */
    public void mescola() {
        Collections.shuffle(deck);
    }

    protected Card drawfirstCard(){
        Card card;
        Card next;
        int i=0;
        Iterator iter = this.deck.iterator();
        card = this.deck.get(0);

        while(iter.hasNext()){
            next = this.deck.get(i+1);
            this.deck.set(i,next);
            i++;

        }
        iter.remove();

    return card;

    }
}