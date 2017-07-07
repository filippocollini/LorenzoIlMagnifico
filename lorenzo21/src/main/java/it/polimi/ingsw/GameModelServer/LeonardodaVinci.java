package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class LeonardodaVinci extends LeaderCard {
    public LeonardodaVinci(){
        this.name = "Leonardo da Vinci";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("characters");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("territory");
        single.setQuantity(2);
        this.requires.add(single);
    }
}
