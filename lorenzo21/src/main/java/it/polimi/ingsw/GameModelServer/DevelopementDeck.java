package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class DevelopementDeck extends Deck {

    private List<DevelopementCard> deck;
    public DevelopementDeck(List<DevelopementCard> cards) {
        deck = cards;
    }

    public List<DevelopementCard> getDeck() {
        return deck;
    }

    @Override
    public void mescola() {
        Collections.shuffle(deck);
    }

    @Override
    public Card drawfirstCard() {
        return super.drawfirstCard();
    }
}