package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationSkipAction extends EffectStrategy implements Cloneable {
    private int id;
    private int period;

    public ExcommunicationSkipAction(){}

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public int getId() {
        return id;
    }


}
