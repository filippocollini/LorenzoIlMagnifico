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
        card = this.deck.get(0);
        Iterator iter = deck.iterator();
        System.out.println(deck.size());

        while(iter.hasNext()){
            next = (TerritoryCard) iter.next();
            deck.set(i,next);
            i++;
        }
        deck.remove(deck.size()-1);

        return card;
    }
}
