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

    @Override
    public Player onceInaRow(Player player) {

        onceinarow = true;
        int i = 0;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            if(token.getType().equalsIgnoreCase("FaithPoints")){
                tokens[i].setPosition(token.getPosition()+1);
            }
            i++;
        }
        player.board.setTokens(tokens);
        return player;
    }
}

