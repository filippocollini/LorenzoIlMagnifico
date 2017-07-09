package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 14/06/2017.
 */
public class GetFreeandDiscount extends EffectStrategy implements Cloneable{

    public GetFreeandDiscount(){
        discount = new ArrayList<>();
    }

    private int id;

    private List<Risorsa> discount;
    private String typecard;
    private int dicepower;
    public static String towerFreeAction="";

    public void setId(int id) {
        this.id = id;
    }

    public void setDicepower(int dicepower) {
        this.dicepower = dicepower;
    }

    public void setResource(List<Risorsa> discount) {
        this.discount = discount;
    }



    public void setTypecard(String typecard) {
        this.typecard = typecard;
    }

    public int getId() {
        return id;
    }

    public List<Risorsa> getResource() {
        return discount;
    }

    public int getDicepower() {
        return dicepower;
    }



    public String getTypeCard() {
        return typecard;
    }

    public Player apply(Player player) {
        FamilyMember ghostmember = new FamilyMember(null,player.getColor());
        ghostmember.setValue(dicepower);
        String tower = null;
        boolean free = true;
        int floor;

        if(typecard.equalsIgnoreCase("color")){
            towerFreeAction = "color";
        }
        else if (typecard.equalsIgnoreCase("territory"))
            towerFreeAction = "territory";
        else if (typecard.equalsIgnoreCase("ventures"))
            towerFreeAction = "ventures";
        else if (typecard.equalsIgnoreCase("buildings"))
            towerFreeAction = "buildings";
        else if(typecard.equalsIgnoreCase("characters"))
            towerFreeAction = "characters";

        /*if (typecard.equalsIgnoreCase("characters"))
            tower = typecard;
        else if (typecard.equalsIgnoreCase("buildings"))
            tower = typecard;
        ghostmember.setValue( Game.controlboost(player , ghostmember,tower).getValue());

        floor = Game.askFloor(ghostmember,player.board.getTower(tower),player);

        //CONTROL DISCOUNT
        if (!Game.controlpurchase(player,player.board.getTower(tower).getFloors().get(floor).getCard(),free)) {
            ghostmember.setValue(dicepower);
            System.out.println("you cannot buy the card! PORACCIO!!!"); //TODO
            return player;
        }

        ghostmember = Game.isFMok(ghostmember,floor,player,dicepower);
        Game.addFMonTowerAction(player, ghostmember, floor, tower,free);*/

        return player;
    }

    public List<Risorsa> apply(List<Risorsa> cost) {//il client fa l'azione, il server controlla se ha diritto ad uno sconto
        int i = 0;
        int oldvalue;
        for(Risorsa res : cost){
            for(Risorsa sale : discount){
                if(res.gettipo().equals(sale.gettipo())) {
                    oldvalue = cost.get(i).getquantity();
                    cost.get(i).setQuantity(oldvalue - sale.getquantity());
                }
            }
            i++;
        }
        return cost;
    }

    public Risorsa apply(Risorsa cost){
        int oldvalue;
        for(Risorsa sale : discount){
            if(sale.gettipo().equals(cost.gettipo())){
                oldvalue = cost.getquantity();
                cost.setQuantity(oldvalue - sale.getquantity());
            }
        }
        return cost;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
