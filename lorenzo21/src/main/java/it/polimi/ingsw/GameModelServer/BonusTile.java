package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class BonusTile {

    private String type1;
    private String type2;
    private List<Risorsa> bonus1;
    private List<Risorsa> bonus2;
    boolean chosen;


    public BonusTile() {
        chosen = false;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setBonus1(List<Risorsa> bonus1) {
        this.bonus1 = bonus1;
    }

    public void setBonus2(List<Risorsa> bonus2) {
        this.bonus2 = bonus2;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public List<Risorsa> getBonus1() {
        return bonus1;
    }

    public List<Risorsa> getBonus2() {
        return bonus2;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO return to server
            return null;
        }
    }
}