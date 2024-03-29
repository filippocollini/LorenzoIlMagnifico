package it.polimi.ingsw.GameModelServer;

import java.util.*;



public class LudovicoIIIGonzaga extends LeaderCard {

    public LudovicoIIIGonzaga() {
        this.name = "Ludovico III Gonzaga";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();

        single.setTipo("Servants");
        single.setQuantity(15);
        this.requires.add((Risorsa) single.clone());
    }

    @Override
    public Player onceInaRow(Player player) {
        onceinarow = true;

        return player;
    }
}
