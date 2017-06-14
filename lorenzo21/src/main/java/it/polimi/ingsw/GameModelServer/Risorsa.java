package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Risorsa implements Cloneable{

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
            e.printStackTrace(); //TODO return to server
            return null;
        }
    }
}