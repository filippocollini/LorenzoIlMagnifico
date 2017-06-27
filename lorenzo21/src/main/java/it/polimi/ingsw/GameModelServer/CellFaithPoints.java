package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 19/06/2017.
 */
public class CellFaithPoints implements Cloneable {
    private int quantity;
    private int victoryPoints;



    public CellFaithPoints(){}

    public int getQuantity() {
        return quantity;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }


    @Override
    public Object clone() {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e ){
            e.printStackTrace(); //TODO
        }
    return null;
    }

    //TODO metodo che garantisce il tornare indietro
}
