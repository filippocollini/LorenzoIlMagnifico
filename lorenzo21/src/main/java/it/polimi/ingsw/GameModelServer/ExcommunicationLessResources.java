package it.polimi.ingsw.GameModelServer;

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

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    public String getType() {
        return type;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
