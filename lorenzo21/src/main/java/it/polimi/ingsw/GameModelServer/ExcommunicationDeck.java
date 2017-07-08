package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class ExcommunicationDeck extends Deck {

    /**
     * Default constructor
     */
    public ExcommunicationDeck(List<ExcommunicationTiles> tiles,int turn) {

        tilesdeck = new ArrayList<>();
        this.turn = turn;

        for(ExcommunicationTiles tile : tiles){
            if(tile.getPeriod()==turn)
                tilesdeck.add(tile);

        }

    }

    private List<ExcommunicationTiles> tilesdeck;
    private int turn;

    @Override
    public void mescola() {
        Collections.shuffle(this.tilesdeck);
    }

    public List<ExcommunicationTiles> getTilesdeck() {
        return tilesdeck;
    }

    public ExcommunicationTiles drawfirstCard() {
        ExcommunicationTiles tile;
        ExcommunicationTiles next;
        int i=0;
        Iterator iter = tilesdeck.iterator();
        tile = tilesdeck.get(0);

        while(iter.hasNext()){
            next = (ExcommunicationTiles) iter.next();
            tilesdeck.set(i,next);
            i++;

        }
        tilesdeck.remove(tilesdeck.size()-1);

        return tile;
    }
}