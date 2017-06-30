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
    private String typecard;

    public void setId(int id) {
        this.id = id;
    }

    public void setDiscount(List<Risorsa> discount) {
        this.discount = discount;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTypecard(String typecard) {
        this.typecard = typecard;
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

    public String getTypecard() {
        return typecard;
    }

    @Override
    public void apply(Player player) {//il client fa l'azione, il server controlla se ha diritto ad uno sconto
        int oldvalue;
        if(!select){ //TODO
        }
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
