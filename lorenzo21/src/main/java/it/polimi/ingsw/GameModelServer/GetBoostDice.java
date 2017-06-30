package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetBoostDice extends EffectStrategy implements Cloneable {

    private int id;
    private String effecttype;
    private int diceboost;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDiceboost(int diceboost) {
        this.diceboost = diceboost;
    }

    public void setEffecttype(String effecttype) {
        this.effecttype = effecttype;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDiceboost() {
        return diceboost;
    }

    public String getEffecttype() {
        return effecttype;
    }


    public void apply(Player player, String color) { //il client fa l'azione, il server prima di fare l'azione controlla se il giocatore ha diritto al boost
        int oldvalue;
        oldvalue = player.getMember(color).getValue();
        player.getMember(color).setValue(oldvalue+this.diceboost);

    }

    @Override
    public Object clone()   {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();//TODO
        }
    return null;
    }
}
