package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationServants extends EffectStrategy implements Cloneable {
    private int id;
    private int period;

    public ExcommunicationServants(){}

    @Override
    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public Object clone()  {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO
        }
    return null;
    }
}
