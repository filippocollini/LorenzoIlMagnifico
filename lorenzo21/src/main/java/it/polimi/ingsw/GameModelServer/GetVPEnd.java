package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetVPEnd extends EffectStrategy implements Cloneable{
    private int id;
    private int VP;

    public GetVPEnd(){}

    @Override
    public int getId() {
        return id;
    }

    public int getVP() {
        return VP;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setVP(int VP) {
        this.VP = VP;
    }

    @Override
    public Player apply(Player player) {
        int i = 0;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            if(token.getType().equalsIgnoreCase("VictoryPoints")){
                tokens[i].setPosition(tokens[i].getPosition()+VP);
            }
            i++;
        }
        player.board.setTokens(tokens);

        return player;
    }

    @Override
    public Object clone()  {
        return super.clone();

    }
}
