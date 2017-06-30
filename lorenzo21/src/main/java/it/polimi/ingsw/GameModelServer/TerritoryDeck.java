package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 29/06/2017.
 */
public class TerritoryDeck extends Deck {

    private List<TerritoryCard> deck;
    private int turn;


    public TerritoryDeck(List<TerritoryCard> cards,int turn){
        deck = new ArrayList<>();
        this.turn = turn;

        for(TerritoryCard card : cards){
            if(card.getPeriod()==turn)
                deck.add(card);

        }
    }

    @Override
    public void mescola() {
        Collections.shuffle(this.deck);
    }


    public List<TerritoryCard> getDeck() {
         return deck;
    }

    public int getTurn() {
        return turn;
    }



    public TerritoryCard drawfirstCard() {
            TerritoryCard card;
            TerritoryCard next;
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
