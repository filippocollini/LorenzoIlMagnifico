package it.polimi.ingsw.GameModelServer;

import java.util.*;


public class PicodellaMirandola extends LeaderCard {

    public PicodellaMirandola() {
        this.name = "Pico della Mirandola";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("ventures");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("buildings");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }

    public List<Risorsa> coinsdiscount(List<Risorsa> cost){
        int i = 0;
        for(Risorsa single : cost){
            if(single.gettipo().equalsIgnoreCase("Coins")){
                if(single.getquantity()<=3){
                    cost.get(i).setQuantity(0);
                }else
                    cost.get(i).setQuantity(cost.get(i).getquantity()-3);
            }
            i++;
        }

        return cost;
    }

}
