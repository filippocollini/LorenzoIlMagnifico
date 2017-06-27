package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 20/06/2017.
 */
public class CellPB implements Cloneable{
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
        this.card = new DevelopementCard();
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
            e.printStackTrace(); //TODO
        }
    return null;
    }
}
