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


    public void apply(Player player,String color) {
        int oldvalue;
        int newvalue;
        int i;
        if(player.getMember(color).getValue() >= dicepower){
            for(Risorsa resource : player.getPB().getresources()){ //Risorse
                for(Risorsa single : tospend){  //risorse da spendere
                    if(single.getquantity()!=0 && single.gettipo().equals(resource.gettipo().concat("to"))) {
                        oldvalue = player.getPB().getsingleresource(resource.gettipo()).getquantity();
                        player.getPB().getsingleresource(resource.gettipo()).setQuantity(oldvalue - single.getquantity());
                    }
                }
                for(Risorsa singlereward : reward){  //risorse da guadagnare
                    if(singlereward.getquantity() != 0 && singlereward.gettipo().equals(resource.gettipo().concat("re"))){
                        newvalue = player.getPB().getsingleresource(resource.gettipo()).getquantity();
                        player.getPB().getsingleresource(resource.gettipo()).setQuantity(newvalue + singlereward.getquantity());
                    }
                }
            }
            for(Token token : player.board.getTokens(player.getColor())){ //Points
                for(Risorsa single : tospend){ //punti da spendere
                    if(single.getquantity() !=0 && single.gettipo().equals(token.getType().concat("to"))){
                        oldvalue = token.getPosition();
                        for(i=0;i<player.board.getTokens(player.getColor()).length;i++){
                            if(player.board.getTokens(player.getColor())[i].getType().equals(token.getType()))
                            player.board.getTokens(player.getColor())[i].setPosition(oldvalue - single.getquantity());
                        }

                    }
                }
                for(Risorsa singlereward : reward){  //punti da guadagnare
                    if(singlereward.getquantity() !=0 && singlereward.gettipo().equals(token.getType().concat("re"))){
                        newvalue = token.getPosition();
                        for(i=0;i<player.board.getTokens(player.getColor()).length;i++){
                            if(player.board.getTokens(player.getColor())[i].getType().equals(token.getType()))
                                player.board.getTokens(player.getColor())[i].setPosition(newvalue + singlereward.getquantity());
                        }
                    }
                }
            }
        }else
            System.out.println("non puoi");//TODO
    }
}



