package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class LudovicoilMoro extends LeaderCard {
    public LudovicoilMoro(){
        this.name = "Ludovico il Moro";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("characters");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("territory");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("ventures");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("buildings");
        single.setQuantity(2);
        this.requires.add(single);
    }
}
