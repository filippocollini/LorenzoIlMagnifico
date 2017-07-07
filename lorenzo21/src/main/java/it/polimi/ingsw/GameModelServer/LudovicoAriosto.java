package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class LudovicoAriosto extends LeaderCard {

    public LudovicoAriosto(){
        this.name = "Ludovico Ariosto";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("characters");
        single.setQuantity(5);
        this.requires.add(single);
    }
}
