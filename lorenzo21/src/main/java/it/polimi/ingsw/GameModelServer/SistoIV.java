package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class SistoIV extends LeaderCard{
    public SistoIV(){
        this.name = "Sisto IV";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("Woods");
        single.setQuantity(6);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("Stones");
        single.setQuantity(6);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("Coins");
        single.setQuantity(6);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("Servants");
        single.setQuantity(6);
        this.requires.add((Risorsa) single.clone());
    }

}
