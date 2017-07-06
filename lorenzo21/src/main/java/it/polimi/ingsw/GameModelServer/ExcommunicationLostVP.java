package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationLostVP extends EffectStrategy implements Cloneable {
    private int id;
    private int period;
    private String type;
    private int quantity;

    public ExcommunicationLostVP(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setTypecard(String type) {
        this.type = type;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public Player apply(Player player) {
        int malus = 0; //calcolo dei punti vittoria da perdere
        int coeff = 0; //quante risorse o punti ha il giocatore
        int i = 0;

        if(this.type.equalsIgnoreCase("GenericResources")){
            for(Risorsa res : player.getPB().getresources()){
                coeff = coeff +(res.getquantity());
            }

        }else if(this.type.equalsIgnoreCase("BuildingCost")){
            for(CellPB cell : player.getPB().getbuildings()){
                for(Risorsa cost : cell.getCard().getCost1()){
                    if(cost.gettipo().equalsIgnoreCase("Woods")
                            || cost.gettipo().equalsIgnoreCase("Stones"))
                        coeff = coeff + (cost.getquantity());
                }
            }

        }else{
            for(Token token : player.board.getTokens(player.getColor())){
                if(token.getType().equals(this.type)){
                    coeff = token.getPosition();
                }
            }
        }
        malus = coeff/this.quantity;
        Token[] tokens = player.board.getTokens(player.getColor());
        for(Token token : tokens){
            if(token.getType().equalsIgnoreCase("VictoryPoints")) {
                tokens[i].setPosition(token.getPosition()-malus);
            }
            i++;
        }
        player.board.setTokens(tokens);


        return player;
    }

    public String getTypeCard() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
