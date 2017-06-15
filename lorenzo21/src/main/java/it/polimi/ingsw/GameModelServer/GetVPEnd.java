package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetVPEnd extends EffectStrategy implements Cloneable{
    private int id;
    private int VP;

    public GetVPEnd(){}

    @Override
    public int getId() {
        return id;
    }

    public int getVP() {
        return VP;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setVP(int VP) {
        this.VP = VP;
    }

    @Override
    public Object clone()  {
        try{
            return super.clone();
        }catch(CloneNotSupportedException e ){
            e.printStackTrace();//TODO
        }
    return null;
    }
}
