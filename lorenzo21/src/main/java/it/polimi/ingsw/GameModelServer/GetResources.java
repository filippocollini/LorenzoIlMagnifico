package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class GetResources extends EffectStrategy {

    int id;
    List<Risorsa> extendedresources;


    public GetResources() {
        id=0;
        extendedresources = new ArrayList<Risorsa>();
    }
    public Integer getid(){
        return id;
    }
    public List<Risorsa> getextendedresources(){
        return extendedresources;
    }





    public void apply(Player player) {
        // TODO implement here
    }

}