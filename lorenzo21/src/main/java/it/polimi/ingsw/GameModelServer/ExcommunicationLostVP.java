package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationLostVP extends EffectStrategy implements Cloneable {
    private int id;
    private int period;
    private String type;
    private int quantity;

    public ExcommunicationLostVP(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    @Override
    public Object clone()  {

        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();//TODO
        }
    return null;
    }
}
