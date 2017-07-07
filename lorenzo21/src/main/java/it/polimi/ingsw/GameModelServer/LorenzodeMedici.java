package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class LorenzodeMedici extends LeaderCard {
    public LorenzodeMedici(){
        this.name = "Lorenzo de' Medici";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("VictoryPoints");
        single.setQuantity(35);
        this.requires.add((Risorsa) single.clone());
    }

}
