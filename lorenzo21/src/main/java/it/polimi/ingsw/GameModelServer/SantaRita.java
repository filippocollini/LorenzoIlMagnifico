package it.polimi.ingsw.GameModelServer;

import java.util.*;


public class SantaRita extends LeaderCard{


    public SantaRita(){
        this.name = "Santa Rita";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("FaithPoints");
        single.setQuantity(8);
        this.requires.add((Risorsa) single.clone());
    }

    /*public List<Risorsa> doublebonusfromcard(List<Risorsa> resources){
        int i = 0;
        for(Risorsa bonus : resources){
            if(bonus.gettipo().equalsIgnoreCase("Coins") ||
                    bonus.gettipo().equalsIgnoreCase("Woods") ||
                    bonus.gettipo().equalsIgnoreCase("Stones") ||
                    bonus.gettipo().equalsIgnoreCase("Servants")){
                resources.get(i).setQuantity(resources.get(i).getquantity()*2);
            }
            i++;
        }

        return resources;
    }*/
}
