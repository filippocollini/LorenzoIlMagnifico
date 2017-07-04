package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

/**
 * Created by filippocollini on 26/05/17.
 */
public class PlayLeaderCard implements Event {
    public void eventHappened(Event event){

    }

    @Override
    public boolean isLegal() {
        return false;
    }

    @Override
    public void eventHappened(AbstractClient client, String uuid) {


    }
}
