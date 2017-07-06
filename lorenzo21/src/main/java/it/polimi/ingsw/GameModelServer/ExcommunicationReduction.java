package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationReduction extends EffectStrategy implements Cloneable {
    private int period;
    private int id;
    private String type;
    private int dice;

    public ExcommunicationReduction(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setTypecard(String type) {
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTypeCard() {
        return type;
    }

    public int getPeriod() {
        return period;
    }

    public int getDice() {
        return dice;
    }

    @Override
    public FamilyMember apply(FamilyMember member,String type) {

        if(this.type.equalsIgnoreCase("dices") && this.type.equals(type)){
            if (!member.getColor().equalsIgnoreCase("Neutral")){
                    member.setValue(member.getValue() - dice);
            }
        }else if(this.type.equals(type)){
            member.setValue(member.getValue() - dice);
        }

        return member;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
