package it.polimi.ingsw.ServerController;

/**
 * Created by filippocollini on 04/07/17.
 */
public interface MarketMoveState extends State {
    public String handleMarketRequest(String request);
}
