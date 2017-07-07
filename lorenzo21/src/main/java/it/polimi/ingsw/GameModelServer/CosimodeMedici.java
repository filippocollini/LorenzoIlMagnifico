package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class CosimodeMedici extends LeaderCard {
    public CosimodeMedici() {
        this.name = "Cosimo de' Medici";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("buildings");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("characters");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }
}
