package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class DevelopementCard extends Card implements Cloneable{

    /**
     * Default constructor
     */
    public DevelopementCard() {
        cost1= new ArrayList<Risorsa>();
        cost2= new ArrayList<Risorsa>();
    }

    @Override
    public void activateEffect(Effect effect) {

    }

    private String name;
    private int number;
    private int period;
    private String cardtype;
    private List<Risorsa> cost1;
    private List<Risorsa> cost2;
    private int immediateeffect;
    private int permanenteffect;

    public String getName(){
        return this.name;
    }

    public int getNumber(){
        return this.number;
    }

    public int getPeriod(){
        return this.period;
    }

    public String getCardtype(){
        return this.cardtype;
    }

    public List<Risorsa> getCost1(){
        return this.cost1;
    }

    public List<Risorsa> getCost2(){
        return this.cost2;
    }

    public int getImmediateeffect(){
        return this.immediateeffect;
    }

    public int getPermanenteffect(){
        return this.permanenteffect;
    }

    public void setname(String name){
        this.name = name;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setPeriod(int period){
        this.period = period;
    }

    public void setCardtype(String cardtype){
        this.cardtype = cardtype;
    }

    public void setCost1(List<Risorsa> cost1){
        this.cost1 = cost1;
    }

    public void setCost2(List<Risorsa> cost2){
         this.cost2 = cost2;
    }

    public void setImmediateEffect(int immediateeffect){
        this.immediateeffect = immediateeffect;
    }

    public void setPermanentEffect(int permanenteffect){
        this.permanenteffect = permanenteffect;
    }

    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO return to server
            return null;
        }
    }

}