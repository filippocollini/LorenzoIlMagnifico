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

    @Override
    public Player onceInaRow(Player player) {

        onceinarow = true;
        player.getPB().getsingleresource("Coins").
                setQuantity(player.getPB().getsingleresource("Coins").getquantity()+3);

        return player;
    }
}
