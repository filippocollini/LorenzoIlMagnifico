package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationCoverMarket extends EffectStrategy implements Cloneable{
    private int id;
    private int period;

    public ExcommunicationCoverMarket(){}

    public int getPeriod() {
        return period;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Object clone() {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO
        }
    return null;
    }
}
