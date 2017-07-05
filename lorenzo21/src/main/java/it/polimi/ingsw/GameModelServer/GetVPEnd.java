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
        for(Token token : player.board.getTokens(player.getColor())){
            if(token.getType().equalsIgnoreCase("VictoryPoints")){
                player.board.getTokens(player.getColor())[i]
                        .setPosition(player.board.getTokens(player.getColor())[i].getPosition()+VP);
            }
            i++;
        }

        return player;
    }

    @Override
    public Object clone()  {
        return super.clone();

    }
}
