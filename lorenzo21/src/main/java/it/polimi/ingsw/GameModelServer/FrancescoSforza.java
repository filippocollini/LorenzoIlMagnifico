package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class FrancescoSforza extends LeaderCard {

    public FrancescoSforza(){
        this.name = "Francesco Sforza";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("ventures");
        single.setQuantity(5);
        this.requires.add(single);
    }
}
