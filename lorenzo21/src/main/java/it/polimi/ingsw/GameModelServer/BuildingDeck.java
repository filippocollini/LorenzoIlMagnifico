package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Simone on 30/06/2017.
 */
public class BuildingDeck extends Deck {

    private List<BuildingCard> deck;
    private int turn;


    public BuildingDeck(List<BuildingCard> cards, int turn){
        deck = new ArrayList<>();
        this.turn = turn;

        for(BuildingCard card : cards){
            if(card.getPeriod()==turn)
                deck.add(card);

        }
    }

    @Override
    public void mescola() {
        Collections.shuffle(this.deck);
    }


    public List<BuildingCard> getDeck() {
        return deck;
    }


    public BuildingCard drawfirstCard() {
        BuildingCard card;
        BuildingCard next;
        int i=0;
        Iterator iter = deck.iterator();
        card = deck.get(0);

        while(iter.hasNext()){
            next = deck.get(i+1);
            deck.set(i,next);
            i++;

        }
        iter.remove();

        return card;
    }

}
