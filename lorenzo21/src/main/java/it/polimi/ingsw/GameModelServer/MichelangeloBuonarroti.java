package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class MichelangeloBuonarroti extends LeaderCard {

    public MichelangeloBuonarroti(){
        this.name = "Michelangelo Buonarroti";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("Stones");
        single.setQuantity(10);
        this.requires.add(single);
    }
}
