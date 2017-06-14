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

    }

    @Override
    public void activateEffect(Effect effect) {

    }

    private String name;
    private int number;
    private int period;
    private String cardtype;
    private List<Risorsa> cost1;
    private boolean choice;
    private List<Integer> immediateeffect;
    private List<Integer> permanenteffect;

    public String getName(){
        return name;
    }

    public int getNumber(){
        return number;
    }

    public int getPeriod(){
        return period;
    }

    public String getCardtype(){
        return cardtype;
    }

    public List<Risorsa> getCost1(){
        return cost1;
    }

    public boolean getChoice(){
        return choice;
    }

    public List<Integer> getImmediateeffect(){
        return immediateeffect;
    }

    public List<Integer> getPermanenteffect(){
        return permanenteffect;
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

    public void setChoice(boolean choice){
        this.choice = choice;
    }

    public void setImmediateEffect(List<Integer> immediateeffect){
        this.immediateeffect = immediateeffect;
    }

    public void setPermanentEffect(List<Integer> permanenteffect){
        this.permanenteffect = permanenteffect;
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