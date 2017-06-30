package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class GetFreeAction extends EffectStrategy implements Cloneable{

    private int id;
    private int dicepower;
    private String type;

    public GetFreeAction() {
    }

    public void setDicepower(int dicepower) {
        this.dicepower = dicepower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDicepower() {
        return dicepower;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }


    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO return to server
            return null;
        }
    }

    /**
     * @param player 
     * @return
     */
    public void apply(Player player) {
        // TODO implement here
    }

}