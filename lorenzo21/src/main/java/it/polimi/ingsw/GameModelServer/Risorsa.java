package it.polimi.ingsw.GameModelServer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class Risorsa implements Cloneable{

    private static final Logger LOG = Logger.getLogger(Risorsa.class.getName());

    private String tipo;
    private int quantity;

    public Risorsa() {
        this.tipo = tipo;
        this.quantity = quantity;
    }



    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }


    public int getquantity() {
        return quantity;
    }

    public String gettipo() {
        return tipo;
    }

    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            LOG.log(Level.SEVERE, "Error with clone", e);
            return null;
        }
    }
}