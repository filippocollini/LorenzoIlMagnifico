package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class CosimodeMedici extends LeaderCard {
    public CosimodeMedici() {
        this.name = "Cosimo de' Medici";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("buildings");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("characters");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }

    @Override
    public Player onceInaRow(Player player) {
        onceinarow = true;
        int i = 0;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            if("VictoryPoints".equalsIgnoreCase(token.getType())) {
                tokens[i].setPosition(token.getPosition() + 1);
            }
            i++;
        }
        player.board.setTokens(tokens);
        player.getPB().getsingleresource("Servants").
                setQuantity(player.getPB().getsingleresource("Servants").getquantity()+3);

        return player;
    }
}
