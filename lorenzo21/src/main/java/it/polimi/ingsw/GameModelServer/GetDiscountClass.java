package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * Created by Simone on 14/06/2017.
 */
public class GetDiscountClass extends EffectStrategy implements Cloneable{

    public GetDiscountClass(){
        discount = new ArrayList<>();
    }

    private int id;
    private boolean select;
    private List<Risorsa> discount;

    public void setId(int id) {
        this.id = id;
    }

    public void setDiscount(List<Risorsa> discount) {
        this.discount = discount;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public List<Risorsa> getDiscount() {
        return discount;
    }

    public boolean getSelect(){
        return select;
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
