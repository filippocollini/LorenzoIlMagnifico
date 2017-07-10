package it.polimi.ingsw.GameModelServer;

import it.polimi.ingsw.Exceptions.FileMalformedException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Simone on 20/06/2017.
 */
public class CellPB implements Cloneable{

    private static final Logger LOG = Logger.getLogger(CellPB.class.getName());

    private int index;
    private int mpNecessary;
    private boolean unlockedcell;
    private DevelopementCard card;
    private int victoryPoints;

    public CellPB (){

    }

    public void setUnlockedcell(boolean unlockedcell) {
        this.unlockedcell = unlockedcell;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setMpNecessary(int mpNecessary) {
        this.mpNecessary = mpNecessary;
    }

    public void setCard(DevelopementCard card) {
        try {
            this.card = new DevelopementCard();
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse the development card's file", e);
        }
        this.card = card;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean getUnlockedcell(){
        return unlockedcell;
    }

    public int getIndex() {
        return index;
    }

    public int getMpNecessary() {
        return mpNecessary;
    }

    public DevelopementCard getCard() {
        return card;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public Object clone() {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            LOG.log(Level.SEVERE, "Error with clone", e);        }
    return null;
    }
}
