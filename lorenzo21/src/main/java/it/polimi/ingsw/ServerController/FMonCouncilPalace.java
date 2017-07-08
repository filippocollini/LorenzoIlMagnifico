package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

/**
 * Created by filippocollini on 26/05/17.
 */
public class FMonCouncilPalace implements Event {

    public void eventHappened(Event event){

    }

    @Override
    public boolean isLegal(AbstractClient client, String uuid) {
        return true;
    }

    @Override
    public void eventHappened(AbstractClient client, String uuid) {


    }
}
