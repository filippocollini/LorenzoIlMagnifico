package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;

public class FedericodaMontefeltro extends LeaderCard {
    public FedericodaMontefeltro(){
        this.name = "Federico da Montefeltro";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("territory");
        single.setQuantity(5);
        this.requires.add((Risorsa) single.clone());
    }

}
