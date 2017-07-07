package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class CesareBorgia extends LeaderCard{
    public CesareBorgia() {
        this.name = "Cesare Borgia";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("Coins");
        single.setQuantity(12);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("FaithPoints");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("buildings");
        single.setQuantity(3);
        this.requires.add((Risorsa) single.clone());
    }
}
