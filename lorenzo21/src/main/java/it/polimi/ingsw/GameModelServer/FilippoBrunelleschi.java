package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class FilippoBrunelleschi extends LeaderCard {
    public FilippoBrunelleschi(){
        this.name = "Filippo Brunelleschi";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("buildings");
        single.setQuantity(5);
        this.requires.add(single);
    }

}
