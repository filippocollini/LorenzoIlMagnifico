package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class GetResources extends EffectStrategy implements Cloneable {

    private int id;
    private List<Risorsa> extendedresources;


    public GetResources() {
        id=0;
        extendedresources = new ArrayList<Risorsa>();
    }

    @Override
    public int getId() {
        return id;
    }

    public List<Risorsa> getResources(){
        return extendedresources;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResources(List<Risorsa> extendedresources) {
        this.extendedresources = extendedresources;
    }

    public void apply(Player player,List<GetResources> effects) {


        // TODO implement here
    }

    @Override
    public Object clone()  {
       try{ return super.clone();
    }catch(CloneNotSupportedException e){
      e.printStackTrace();//TODO
       }
    return null;
    }
}