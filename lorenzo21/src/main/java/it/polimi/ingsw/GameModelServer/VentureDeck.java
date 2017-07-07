package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Simone on 30/06/2017.
 */
public class VentureDeck extends Deck {
    private List<VentureCard> deck;
    private int turn;


    public VentureDeck(List<VentureCard> cards, int turn){
        deck = new ArrayList<>();
        this.turn = turn;

        for(VentureCard card : cards){
            if(card.getPeriod()==turn)
                deck.add(card);

        }
    }

    @Override
    public void mescola() {
        Collections.shuffle(this.deck);
    }


    public List<VentureCard> getDeck() {
        return deck;
    }


    public VentureCard drawfirstCard() {
        VentureCard card;
        VentureCard next;
        int i=0;
        Iterator iter = deck.iterator();
        card = deck.get(0);

        while(iter.hasNext()){
            next = (VentureCard) iter.next();
            deck.set(i,next);
            i++;

        }
        deck.remove(deck.size()-1);

        return card;
    }
}
