package it.polimi.ingsw.GameModelServer;

import java.util.List;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationLessResources extends EffectStrategy implements Cloneable {
    private int period;
    private int id;
    private String type;

    public ExcommunicationLessResources(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setTypecard(String type) {
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    public String getTypeCard() {
        return type;
    }

    @Override
    public List<Risorsa> apply(List<Risorsa> reward) {
        int i = 0;

        for(Risorsa singlereward : reward){
            if(this.type.equalsIgnoreCase("WoodStones")){
                if(singlereward.gettipo().equalsIgnoreCase("Woods") || singlereward.gettipo().equalsIgnoreCase("Stones")){
                    reward.get(i).setQuantity(reward.get(i).getquantity()-1);
                }
            }else if(singlereward.gettipo().equals(this.type)){
                reward.get(i).setQuantity(reward.get(i).getquantity()-1);
            }
        }

        return reward;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
