package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class ListRisorse {


    private List<Risorsa> risorse;

    public ListRisorse() {
        risorse = new ArrayList<Risorsa>();

    }

    public List<Risorsa> getrisorse(){
        return risorse;
    }

    public void setRisorse(List<Risorsa> risorsa){
        this.risorse = risorsa;
    }

}
