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


    @Override
    public Player onceInaRow(Player player) {

        onceinarow = true;
        int i = 0;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            if(token.getType().equalsIgnoreCase("VictoryPoints")){
                tokens[i].setPosition(token.getPosition()+1);
            }else if(token.getType().equalsIgnoreCase("MilitaryPoints")){
                tokens[i].setPosition(token.getPosition()+2);
            }
            i++;
        }
        player.board.setTokens(tokens);
        return player;
    }
}
