package it.polimi.ingsw.GameModelServer;

/**
 * 
 */
public class GetFreeAction extends EffectStrategy implements Cloneable{

    private int id;
    private int dicepower;
    private String typecard;

    public GetFreeAction() {
    }

    public void setDicepower(int dicepower) {
        this.dicepower = dicepower;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypecard(String type) {
        this.typecard = type;
    }

    public int getDicepower() {
        return dicepower;
    }

    public int getId() {
        return id;
    }

    public String getTypecard() {
        return typecard;
    }


    @Override
    public Object clone(){
        return super.clone();

    }


    public Player apply(Player player) {
        FamilyMember ghostmember = new FamilyMember("ghost","ghost");
        ghostmember.setValue(dicepower);
        String tower = null;
        int floor;
        boolean free = false;
        if(typecard.equalsIgnoreCase("harvest") || typecard.equalsIgnoreCase("production")){
            //TODO action production e harvest
        }else {
            if(typecard.equalsIgnoreCase("color")){
                tower = Game.askTower();
            }
            else if (typecard.equalsIgnoreCase("territory"))
                tower = "territory";
            else if (typecard.equalsIgnoreCase("venture"))
                tower = "ventures";
            ghostmember.setValue( Game.controlboost(player , ghostmember,tower).getValue());

            floor = Game.askFloor(ghostmember,player.board.getTower(tower),player);

            if (!Game.controlpurchase(player,player.board.getTower(tower).getFloors().get(floor).getCard(),free)) {
                ghostmember.setValue(dicepower);
                System.out.println("you cannot buy the card! PORACCIO!!!"); //TODO
                return player;
            }
            ghostmember = Game.isFMok(ghostmember,floor,player,dicepower);
            player = Game.addFMonTowerAction(player, ghostmember, floor, tower, free);
        }

        return player;
    }

}