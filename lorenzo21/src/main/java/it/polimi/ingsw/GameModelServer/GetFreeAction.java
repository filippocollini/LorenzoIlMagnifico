package it.polimi.ingsw.GameModelServer;

/**
 * 
 */
public class GetFreeAction extends EffectStrategy implements Cloneable{

    private int id;
    private int dicepower;
    private String typecard;
    public static int harvestOrProduction;
    public static String towerFreeAction ="";

    public GetFreeAction() {
        harvestOrProduction = 0;
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

    public String getTypeCard() {
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
        if(typecard.equalsIgnoreCase("harvest")){
            harvestOrProduction = 1;
            return player;
        } else if (typecard.equalsIgnoreCase("production")){
            harvestOrProduction = 2;
            return player;
        }else {
            if(typecard.equalsIgnoreCase("color")){
                towerFreeAction = "color";
            }
            else if (typecard.equalsIgnoreCase("territory"))
                towerFreeAction = "territory";
            else if (typecard.equalsIgnoreCase("ventures"))
                towerFreeAction = "ventures";
        }

        return player;
    }

}