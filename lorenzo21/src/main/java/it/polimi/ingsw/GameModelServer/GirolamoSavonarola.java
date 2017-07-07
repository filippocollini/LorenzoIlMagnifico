package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class GirolamoSavonarola extends LeaderCard {

    public GirolamoSavonarola(){
        this.name = "Girolamo Savonarola";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("Coins");
        single.setQuantity(18);
        this.requires.add(single);
    }

}

