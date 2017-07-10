package it.polimi.ingsw.GameModelServer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetResourcesIf extends EffectStrategy implements Cloneable{

    private static final Logger LOG = Logger.getLogger(GetResourcesIf.class.getName());

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
        int oldvalue;
        if(player.getMember(color).getValue()>= this.dicepower){
            /*for(Risorsa ress : player.getPB().getresources()) {
                for (Risorsa reward : rewards){
                    if(reward.gettipo().equals(ress.gettipo())){
                        player.getPB().getresources().get(j).setQuantity(ress.getquantity()+reward.getquantity());
                    }
                }
                j++;
            }
            Token[] tokens = player.board.getTokens(player.getColor());
            for(Token token : tokens){
                for(Risorsa reward : rewards){
                    if(reward.gettipo().equals(token.getType())){
                        oldvalue = tokens[i].getPosition();
                        tokens[i].setPosition(oldvalue + reward.getquantity());
                    }
                }
                i++;
            }
            player.board.setTokens(tokens);
            */
            player = Game.getimmediateBonus(player,this.rewards,false);
        }else
            System.out.println("non prendi risorse perchè ha valore troppo basso");
        return player;
    }

    @Override
    public Object clone() {
        try{
            return super.clone();
        }catch(NullPointerException e){
            LOG.log(Level.SEVERE, "Error with clone", e);
        }
        return null;
    }
}

