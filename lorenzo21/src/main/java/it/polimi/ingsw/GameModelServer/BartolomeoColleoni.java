package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class BartolomeoColleoni extends LeaderCard {
    public BartolomeoColleoni() {
        this.name ="Bartolomeo Colleoni";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("territory");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("ventures");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }
}
