package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class LucreziaBorgia extends LeaderCard {
    public LucreziaBorgia(){
        this.name = "Lucrezia Borgia";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("samedevelopment");
        single.setQuantity(6);
        this.requires.add((Risorsa) single.clone());
    }

}
