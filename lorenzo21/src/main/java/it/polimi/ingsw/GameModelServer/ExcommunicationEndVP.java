package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationEndVP extends EffectStrategy implements Cloneable {
    private int id;
    private int period;
    private String type;

    public ExcommunicationEndVP (){}

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public int getId() {
        return id;
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
