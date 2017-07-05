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

    public void setResource(List<Risorsa> rewards) {
        this.rewards = rewards;
    }

    public int getDicepower() {
        return dicepower;
    }

    public int getId() {
        return id;
    }

    public List<Risorsa> getResource() {
        return rewards;
    }


    public Player apply(Player player, String color) {
        int i = 0;
        int j = 0;
        int oldvalue;
        if(player.getMember(color).getValue()>= this.dicepower){
            for(Risorsa ress : player.getPB().getresources()) {
                for (Risorsa reward : rewards){
                    if(reward.gettipo().equals(ress.gettipo())){
                        player.getPB().getresources().get(j).setQuantity(ress.getquantity()+reward.getquantity());
                    }
                }
                j++;
            }
            for(Token token : player.board.getTokens(player.getColor())){
                for(Risorsa reward : rewards){
                    if(reward.gettipo().equals(token.getType())){
                        oldvalue = player.board.getTokens(player.getColor())[i].getPosition();
                        player.board.getTokens(player.getColor())[i].setPosition(oldvalue + reward.getquantity());
                    }
                }
                i++;
            }
        }else
            System.out.println("non puoi fare l'azione"); //TODO
        return player;
    }

    @Override
    public Object clone() {
            return super.clone();
    }
}

