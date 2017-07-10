package it.polimi.ingsw.GameModelServer;

import it.polimi.ingsw.ClientView.CommandLineUI;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Simone on 19/06/2017.
 */
public class CellFaithPoints implements Cloneable {

    private static final Logger LOG = Logger.getLogger(CellFaithPoints.class.getName());

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
            LOG.log(Level.SEVERE, "Error with clone", e);        }
    return null;
    }

}
