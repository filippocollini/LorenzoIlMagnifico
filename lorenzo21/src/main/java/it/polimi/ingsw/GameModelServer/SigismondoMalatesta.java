package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;

public class SigismondoMalatesta extends LeaderCard{
    public SigismondoMalatesta(){
        this.name = "Sigismondo Malatesta";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("MilitaryPoints");
        single.setQuantity(7);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("FaithPoints");
        single.setQuantity(3);
        this.requires.add(single);
    }
}
