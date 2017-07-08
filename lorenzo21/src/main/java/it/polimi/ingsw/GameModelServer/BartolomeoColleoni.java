package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;


public class BartolomeoColleoni extends LeaderCard {
    public BartolomeoColleoni() {
        this.name ="Bartolomeo Colleoni";
        Risorsa single = new Risorsa();
        requires = new ArrayList<>();
        single.setTipo("territory");
        single.setQuantity(4);
        this.requires.add((Risorsa) single.clone());
        single.setTipo("ventures");
        single.setQuantity(2);
        this.requires.add((Risorsa) single.clone());
    }

    @Override
    public Player onceInaRow(Player player) {
        onceinarow = true;
        int i = 0;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens) {
            if (token.getType().equalsIgnoreCase("VictoryPoints")) {
                tokens[i].setPosition(token.getPosition() + 4);
            }
            i++;
        }
        player.board.setTokens(tokens);
        return player;

    }
}
