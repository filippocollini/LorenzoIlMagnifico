package it.polimi.ingsw.GameModelServer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public abstract class EffectStrategy implements Cloneable{

    private static final Logger LOG = Logger.getLogger(EffectStrategy.class.getName());

    protected int id;

    public EffectStrategy() {}

    public abstract void setId(int id);

    public int getId() {
        return id;
    }

    public Player apply(Player player){
        return player;
    }

    public List<Risorsa> apply(List<Risorsa> cost){
        return cost;
    }

    public Player apply(Player player, String color){
        return player;
    }

    public FamilyMember apply(FamilyMember member,String type){
        return member;
    }

    public List<Risorsa> apply(List<Risorsa> gained,Player player,String color){
        return gained;
    }


    @Override
    public Object clone()  {
        try{ return super.clone();
        }catch(CloneNotSupportedException e){
            LOG.log(Level.SEVERE, "Error with clone", e);
        }
        return null;
    }

    public void setTospend(List<Risorsa> selling) {
    }

    public void setReward(List<Risorsa> reward) {
    }

    public void setDicepower(int dado) {
    }

    public void setTypecard(String type) {
    }

    public void setSelect(boolean choice) {
    }

    public void setDiceboost(int dice) {
    }

    public void setDiscount(List<Risorsa> discounts) {
    }

    public void setResource(List<Risorsa> discounts) {
    }

    public void setDice(int dado) {
    }

    public void setResourcesfor(List<Risorsa> foreach) {
    }


    public void setResourcesget(List<Risorsa> getres){}

    public void setVP(int vp) {
    }

    public String getTypeCard() {
        return null;
    }

    public List<Risorsa> getTospend() {
        return null;
    }

    public List<Risorsa> getResources(){
        return null;
    }

    public void setPeriod(int period) {
    }

    public void setQuantity(int quantity) {
    }
}