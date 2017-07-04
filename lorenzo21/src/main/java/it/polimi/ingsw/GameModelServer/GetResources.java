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

    public List<Risorsa> getResource(){
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
        int i = 0;
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
        for(Token token : player.board.getTokens(player.getColor())){
            for(Risorsa reward : extendedresources){
                if(reward.gettipo().equals(token.getType())){
                    oldvalue = player.board.getTokens(player.getColor())[i].getPosition();
                    player.board.getTokens(player.getColor())[i].setPosition(oldvalue + reward.getquantity());
                }
            }
            i++;
        }

        return player;
    }

    @Override
    public Object clone()  {
       return super.clone();
    }
}