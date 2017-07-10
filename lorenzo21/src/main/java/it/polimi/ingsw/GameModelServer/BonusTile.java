package it.polimi.ingsw.GameModelServer;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class BonusTile implements Serializable {

    private static final Logger LOG = Logger.getLogger(BonusTile.class.getName());


    private String type1;
    private String type2;
    private transient List<Risorsa> bonus1;
    private transient List<Risorsa> bonus2;
    boolean chosen;


    public BonusTile() {
        chosen = false;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setBonus1(List<Risorsa> bonus1) {
        this.bonus1 = bonus1;
    }

    public void setBonus2(List<Risorsa> bonus2) {
        this.bonus2 = bonus2;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public List<Risorsa> getBonus1() {
        return bonus1;
    }

    public List<Risorsa> getBonus2() {
        return bonus2;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            LOG.log(Level.SEVERE, "Error with clone", e);
        }
        return null;
    }

    public StringBuilder showBonusTile(){
        StringBuilder showbt = new StringBuilder();
        List<Risorsa> bonus;
        List<String> types = new ArrayList<>();
        types.add(type1);
        types.add(type2);
        int i;

        for(i = 0; i<types.size();i++){
            showbt.append("On ");
            showbt.append(types.get(i));
            showbt.append(" action you get these Bonuses : ");
            showbt.append("\n");
            if(types.get(i).equalsIgnoreCase(type1)) {
                bonus = bonus1;
            }else
                bonus = bonus2;

            for(Risorsa res : bonus){
                showbt.append(res.getquantity());
                showbt.append(" ");
                showbt.append(res.gettipo());
                showbt.append("\n");
            }
            showbt.append("\n");

        }
        return showbt;
    }

}