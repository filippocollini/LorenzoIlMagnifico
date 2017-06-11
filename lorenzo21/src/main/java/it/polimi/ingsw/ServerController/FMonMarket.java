package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 26/05/17.
 */
public class FMonMarket implements Event {

    public FMonMarket(){
        
    }

    @Override
    public boolean isLegal() {
        return true;
    }

    public void eventHappened(){ //non serve passare l'event
        System.out.println("market");
    }
}
