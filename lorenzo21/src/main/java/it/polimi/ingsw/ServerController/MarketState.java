package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.AbstractClient;

/**
 * Created by filippocollini on 04/07/17.
 */
public class MarketState implements State, MarketMoveState {
    @Override
    public void print() {

    }

    @Override
    public void handle(String request, AbstractClient client, String uuid) {
        handleMarketRequest(request);
    }

    @Override
    public String handleMarketRequest(String request) {
        return null;
    }
}
