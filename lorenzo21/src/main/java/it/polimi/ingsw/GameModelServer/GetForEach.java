package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetForEach extends EffectStrategy implements  Cloneable {
    private int id;
    private int dice;
    private List<Risorsa> resourcesfor;
    private List<Risorsa> resourcesget;

    public GetForEach(){
        resourcesfor = new ArrayList<>();
        resourcesget = new ArrayList<>();

    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setResourcesfor(List<Risorsa> resourcesfor) {
        this.resourcesfor = resourcesfor;
    }

    public void setResourcesget(List<Risorsa> resourcesget) {
        this.resourcesget = resourcesget;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDice() {
        return dice;
    }

    public List<Risorsa> getResourcesfor() {
        return resourcesfor;
    }

    public List<Risorsa> getResourcesget() {
        return resourcesget;
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
