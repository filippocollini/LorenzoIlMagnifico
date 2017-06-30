package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetResourcesIf extends EffectStrategy implements Cloneable{
    private int id;
    private int dicepower;
    private List<Risorsa> rewards;

    public GetResourcesIf() {
        rewards = new ArrayList<>();
    }

    public void setDicepower(int dicepower) {
        this.dicepower = dicepower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResources(List<Risorsa> rewards) {
        this.rewards = rewards;
    }

    public int getDicepower() {
        return dicepower;
    }

    public int getId() {
        return id;
    }

    public List<Risorsa> getRewards() {
        return rewards;
    }


    public void apply(Player player, String color) { //TODO implementa il get di punti con i token
        if(player.getMember(color).getValue()>= this.dicepower){
            for(Risorsa ress : player.getPB().getresources()) {
                for (Risorsa reward : rewards){
                    if(reward.gettipo().equals(ress.gettipo())){
                        ress.setQuantity(ress.getquantity()+reward.getquantity());
                    }
                }
            }
        }else
            System.out.println("non puoi fare l'azione"); //TODO
    } //OVERLOADING

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //TODO
        }
    return null;
    }
}

