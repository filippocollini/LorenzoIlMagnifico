package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class CellTower implements Cloneable {

    /**
     * Default constructor
     */
    public CellTower() {
        fMIsPresent = false;
    }

    private boolean fMIsPresent;
    private FamilyMember fmOnIt;
    private DevelopementCard carta;
    private Risorsa bonus;
    private int dice;


    public boolean isfMPresent(){
        return fMIsPresent;
    }

    public void setFmOnIt(FamilyMember fmOnIt) {
        this.fmOnIt = fmOnIt;
    }

    public FamilyMember getFmOnIt() {
        return fmOnIt;
    }

    public DevelopementCard getCard(){
        return carta;
    }

    public Risorsa getResourceBonus(){
        return bonus;
    }

    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setBonus(Risorsa bonus) {
        this.bonus = bonus;
    }

    public void setCarta(DevelopementCard carta) {
        this.carta = carta;
    }

    public void setfMIsPresent(boolean fMIsPresent) {
        this.fMIsPresent = fMIsPresent;
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