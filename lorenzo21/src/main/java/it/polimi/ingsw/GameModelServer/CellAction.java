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
    private String type;

    public CellAction(){
        bonus = new ArrayList<Risorsa>();
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBonus(List<Risorsa> bonus) {
        this.bonus = bonus;
    }

    public void setFamilyMemberinCell(FamilyMember member) {
        this.fMisOn = true;
        this.member = member;
    }

    public String getcolorMember() {
        return member.getColorplayer();
    }

    public int getDice() {
        return dice;
    }

    public FamilyMember getMember() {
        return member;
    }

    public List<Risorsa> getBonus() {
        return bonus;
    }

    public String getType() {
        return type;
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
