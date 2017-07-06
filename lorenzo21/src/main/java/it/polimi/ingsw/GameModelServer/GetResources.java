package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class GetResources extends EffectStrategy implements Cloneable {

    private int id;
    private List<Risorsa> extendedresources;


    public GetResources() {
        id=0;
        extendedresources = new ArrayList<Risorsa>();
    }

    @Override
    public int getId() {
        return id;
    }

    public List<Risorsa> getResources(){
        return extendedresources;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResource(List<Risorsa> extendedresources) {
        this.extendedresources = extendedresources;
    }

    @Override
    public Player apply(Player player) {
        /*int i = 0;   //al momento quello nel commento non aggiunge PF
        int j = 0;
        int oldvalue;
        for(Risorsa rex : player.getPB().getresources()) {
            for (Risorsa reward : extendedresources){
                if(reward.gettipo().equals(rex.gettipo())){
                    player.getPB().getresources().get(j).setQuantity(rex.getquantity()+reward.getquantity());
                }
            }
            j++;
        }
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            for(Risorsa reward : extendedresources){
                if(reward.gettipo().equals(token.getType())){
                    oldvalue = tokens[i].getPosition();
                    tokens[i].setPosition(oldvalue + reward.getquantity());
                }
            }
            i++;
        }
        player.board.setTokens(tokens);
        */
        player = Game.getimmediateBonus(player,this.extendedresources,false);

        return player;
    }

    @Override
    public Object clone()  {
       return super.clone();
    }
}