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
        return super.clone();
    }


    public Player apply(Player player, String color) {
        int oldvalue;
        int i;
        Player control = player;


        if(player.getMember(color).getValue() >= dicepower){

            for(Risorsa resource : control.getPB().getresources()){ //Risorse
                for(Risorsa single : tospend){  //risorse da spendere
                    if(single.getquantity()!=0 && single.gettipo().equals(resource.gettipo().concat("to"))) {
                        oldvalue = control.getPB().getsingleresource(resource.gettipo()).getquantity();
                        if(oldvalue-single.getquantity()>=0) {
                            control.getPB().getsingleresource(resource.gettipo()).setQuantity(oldvalue - single.getquantity());
                        }else{
                            System.out.println("no more resources to spend");
                            return player;
                        }
                    }
                }
            }

            for(Token token : control.board.getTokens(player.getColor())){ //Points
                for(Risorsa single : tospend){ //punti da spendere
                    if(single.getquantity() !=0 && single.gettipo().equals(token.getType().concat("to"))){
                        oldvalue = token.getPosition();
                        for(i=0;i<control.board.getTokens(control.getColor()).length;i++){
                            if(control.board.getTokens(control.getColor())[i].getType().equals(token.getType())) {
                                if (oldvalue - single.getquantity() >= 0) {
                                    control.board.getTokens(control.getColor())[i].setPosition(oldvalue - single.getquantity());
                                } else {
                                    System.out.println("no more points to spend");
                                    return player;
                                }
                            }
                        }
                    }
                }

            }

            player = control;
        }else
            System.out.println("non puoi");
        return player;
    }

    public List<Risorsa> apply(List<Risorsa> gained,Player player,String color){
        List<Risorsa> palacechoice = new ArrayList<>();
        int j = 0;

        if(player.getMember(color).getValue() >= dicepower){

            for(Risorsa res : reward){
                if(res.gettipo().equalsIgnoreCase("PalaceFavorre"))
                    palacechoice = Game.choosePalaceFavor(Game.getPalaceFavors(),res.getquantity());
            }
            for(Risorsa rex : reward){
                for(Risorsa choice : palacechoice){
                    if(rex.gettipo().equalsIgnoreCase(choice.gettipo().concat("re")))
                        reward.get(j).setQuantity(rex.getquantity()+choice.getquantity());
                }
                j++;
            }

            for(Risorsa singlereward : reward){  //risorse da guadagnare
                if(singlereward.getquantity() != 0) {
                    gained.add(singlereward);
                }
            }

        }else
            System.out.println("non puoi");
        return gained;
    }
}



