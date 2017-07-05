package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetBoostDice extends EffectStrategy implements Cloneable {

    private int id;
    private String typecard;
    private int diceboost;

    public GetBoostDice(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDiceboost(int diceboost) {
        this.diceboost = diceboost;
    }

    public void setTypecard(String effecttype) {
        this.typecard = effecttype;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDiceboost() {
        return diceboost;
    }

    public String getTypecard() {
        return typecard;
    }


    public FamilyMember apply(FamilyMember member) {
        int oldvalue;
        oldvalue = member.getValue();
        member.setValue(oldvalue+this.diceboost);
        return member;
    }

    @Override
    public Object clone()   {

            return super.clone();

    }
}
