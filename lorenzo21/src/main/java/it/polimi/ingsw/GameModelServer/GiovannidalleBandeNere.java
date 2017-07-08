package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class GiovannidalleBandeNere extends LeaderCard {

    public GiovannidalleBandeNere(){
        this.name = "Giovanni delle Bande Nere";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("MilitaryPoints");
        single.setQuantity(12);
        this.requires.add(single);
    }


    @Override
    public Player onceInaRow(Player player) {

        onceinarow = true;
        player.getPB().getsingleresource("Woods").
                setQuantity(player.getPB().getsingleresource("Woods").getquantity()+1);
        player.getPB().getsingleresource("Stones").
                setQuantity(player.getPB().getsingleresource("Stones").getquantity()+1);
        player.getPB().getsingleresource("Coins").
                setQuantity(player.getPB().getsingleresource("Coins").getquantity()+1);

        return player;
    }
}
