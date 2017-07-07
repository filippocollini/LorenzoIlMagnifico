package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class LeaderDeck extends Deck {

    private List<LeaderCard> deckleader;

    public LeaderDeck(List<LeaderCard> leaders) {
        deckleader = new ArrayList<>();
        this.deckleader = leaders;
    }

    @Override
    public void mescola() {
        Collections.shuffle(this.deckleader);
    }

    public List<LeaderCard> getDeckleader() {
        return deckleader;
    }

    @Override
    protected LeaderCard drawfirstCard() {
        LeaderCard card;
        LeaderCard next;
        int i=0;
        Iterator iter = deckleader.iterator();
        card = deckleader.get(0);

        while(iter.hasNext()){
            next = deckleader.get(i+1);
            deckleader.set(i,next);
            i++;

        }
        iter.remove();

        return card;

    }
}