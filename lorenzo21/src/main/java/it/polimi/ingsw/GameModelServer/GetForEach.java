package it.polimi.ingsw.GameModelServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simone on 15/06/2017.
 */
public class GetForEach extends EffectStrategy implements  Cloneable {
    private int id;
    private int dice;
    private List<Risorsa> resourcesfor;
    private List<Risorsa> resourcesget;

    public GetForEach(){
        resourcesfor = new ArrayList<>();
        resourcesget = new ArrayList<>();

    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public void setResourcesfor(List<Risorsa> resourcesfor) {
        this.resourcesfor = resourcesfor;
    }

    public void setResourcesget(List<Risorsa> resourcesget) {
        this.resourcesget = resourcesget;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDice() {
        return dice;
    }

    public List<Risorsa> getResourcesfor() {
        return resourcesfor;
    }

    public List<Risorsa> getResourcesget() {
        return resourcesget;
    }


    public Player apply(Player player, String color) {
        int coeff;
        int quantity;
        int oldvalue;
        int i;
        coeff = 0;
        if((dice == -1) || (player.getMember(color).getValue() >= dice)){
            for(Risorsa resfor : resourcesfor){
               if(resfor.gettipo().equals("MilitaryPoints") && resfor.getquantity() != 0){
                  for(i=0;i<player.board.getTokens(player.getColor()).length;i++){
                      if(player.getToken()[i].getType().equals("MilitaryPoints"))
                          coeff = player.board.getTokens(player.getColor())[i].getPosition()/resfor.getquantity();
                  }
               }else if(resfor.getquantity() != 0){

                   for( CellPB cell : player.getPB().gettypecards(resfor.gettipo())) {
                       if (cell.getCard() != null)
                           coeff++;
                   }
               }
            }
            for(Risorsa resget : resourcesget){
                if(resget.getquantity() != 0){
                    quantity = resget.getquantity();
                    oldvalue = player.getPB().getsingleresource(resget.gettipo()).getquantity();
                    player.getPB().getsingleresource(resget.gettipo()).setQuantity(oldvalue+(quantity*coeff));
                }
            }
        }else
            System.out.println("non puoi fare l'azione");
        return player;
    }

    @Override
    public Object clone()  {

        return super.clone();
    }
}
