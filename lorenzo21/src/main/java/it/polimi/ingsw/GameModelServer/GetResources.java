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

    @Override
    public void apply(Player player) {  //TODO implementare anche i token della board con i punti
        for(Risorsa rex : player.getPB().getresources()) {
            for (Risorsa reward : extendedresources){
                if(reward.gettipo().equals(rex.gettipo())){
                    rex.setQuantity(rex.getquantity()+reward.getquantity());
                }
            }
        }

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