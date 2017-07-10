package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that permits the communication between client and server
 */
public interface Callback extends Remote{

    public static final String NAME = "Callback";
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    public String joinPlayer(String username, RMIClientInterface client) throws RemoteException;
    public void sendRequest(String request)throws RemoteException;
    public void marketMove(String uuid, String member, String cell) throws RemoteException;
    public void towerMove(String uuid, String member, String tower, int floor, boolean free) throws RemoteException;
    public void palaceMove(String uuid, String member, String favor) throws RemoteException;
    public void favorChoiceMove(String uuid, String favor, String choice) throws RemoteException;
    public void secondFavorChoiceMove(String uuid, String favor) throws RemoteException;
    public void addServantsMove(String uuid, int nServants) throws RemoteException;
    public void endMove(String uuid) throws RemoteException;
    public void sendLong(long val) throws RemoteException;
    public void harvestMove(String uuid, String member) throws RemoteException;
    public void productionMove(String uuid, String member, List<Integer> choices) throws RemoteException;
    public void powerUpMove(String uuid, String member, int nServants) throws RemoteException;
    public void leaderMove(String uuid, String card) throws RemoteException;
    public void discardLeaderCard(String uuid, String card, String favor) throws RemoteException;

    public void showPlayerGoods(String uuid) throws RemoteException;

    public void showOtherPlayers(String uuid) throws RemoteException;

    public void showBoard(String uuid) throws RemoteException;
}
