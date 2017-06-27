package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 19/06/2017.
 */
public class CellAction implements Cloneable {
    private int dice;
    private List<Risorsa> bonus;
    private boolean fMisOn;
    private FamilyMember member;

    public CellAction(){
        bonus = new ArrayList<Risorsa>();
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setBonus(List<Risorsa> bonus) {
        this.bonus = bonus;
    }

    public void setFamilyMemberinCell(String color) {
        this.fMisOn = true;
        this.member = new FamilyMember(color);
    }

    public String getcolorMember() {
        return member.getColor();
    }

    public int getDice() {
        return dice;
    }

    public List<Risorsa> getBonus() {
        return bonus;
    }

    public boolean isfMOn(){
        return fMisOn;
    }

    @Override
    protected Object clone() {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();//TODO
        }
    return null;
    }
}
