package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetResourcesSelling extends EffectStrategy implements Cloneable{

    private int id;
    private int dicepower;
    private List<Risorsa> tospend;
    private List<Risorsa> reward ;


    public GetResourcesSelling(){
        tospend = new ArrayList<>();
        reward = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDicepower(int dicepower) {
        this.dicepower = dicepower;
    }

    public void setReward(List<Risorsa> reward) {
        this.reward = reward;
    }

    public void setTospend(List<Risorsa> tospend) {
        this.tospend = tospend;
    }

    public int getId() {
        return id;
    }

    public int getDicepower() {
        return dicepower;
    }

    public List<Risorsa> getTospend() {
        return tospend;
    }

    public List<Risorsa> getReward() {
        return reward;
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

    //apply(Player, id)
}



