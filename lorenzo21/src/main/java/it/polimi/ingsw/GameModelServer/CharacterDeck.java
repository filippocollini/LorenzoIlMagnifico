package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Simone on 30/06/2017.
 */
public class CharacterDeck extends Deck {

    private List<CharacterCard> deck;
    private int turn;


    public CharacterDeck(List<CharacterCard> cards, int turn){
        deck = new ArrayList<>();
        this.turn = turn;

        for(CharacterCard card : cards){
            if(card.getPeriod()==turn)
                deck.add(card);

        }
    }

    @Override
    public void mescola() {
        Collections.shuffle(this.deck);
    }


    public List<CharacterCard> getDeck() {
        return deck;
    }


    public CharacterCard drawfirstCard() {
        CharacterCard card;
        CharacterCard next;
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
