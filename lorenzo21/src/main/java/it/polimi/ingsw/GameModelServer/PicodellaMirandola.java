package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class PicodellaMirandola extends LeaderCard {

    public PicodellaMirandola() {
        this.name = "Pico della Mirandola";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("ventures");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("buildings");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }
}
