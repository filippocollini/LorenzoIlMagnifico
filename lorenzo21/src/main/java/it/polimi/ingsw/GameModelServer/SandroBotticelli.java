package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class SandroBotticelli extends LeaderCard{

    public SandroBotticelli(){
        this.name = "Sandro Botticelli";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("Woods");
        single.setQuantity(10);
        this.requires.add((Risorsa) single.clone());
    }

}
