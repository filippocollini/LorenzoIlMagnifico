package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class GiovannidalleBandeNere extends LeaderCard {

    public GiovannidalleBandeNere(){
        this.name = "Giovanni delle Bande Nere";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("MilitaryPoints");
        single.setQuantity(12);
        this.requires.add(single);
    }

}
