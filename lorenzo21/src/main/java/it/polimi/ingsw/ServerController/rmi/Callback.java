package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by filippocollini on 23/06/17.
 */
public interface Callback extends Remote{
    public static final String NAME = "Callback";
    public static final String FAILURE = "failure";
    public static final String SUCCESS = "success";

    public String joinPlayer(String username, RMIClientInterface client) throws RemoteException;
    public void sendRequest(String request)throws RemoteException;
    public void marketMove(String uuid, String member, String cell) throws RemoteException;
    public void towerMove(String uuid, String member, String tower, int floor) throws RemoteException;
    public void palaceMove(String uuid, String member, String favor) throws RemoteException;
    public void favorChoiceMove(String uuid, String favor, String choice) throws RemoteException;
    public void secondFavorChoiceMove(String uuid, String favor) throws RemoteException;
    public void addServantsMove(String uuid, int nServants) throws RemoteException;
    public void endMove(String uuid) throws RemoteException;
    public void sendLong(long val) throws RemoteException;
    public void harvestMove(String uuid, String member);
    public void productionMove(String uuid, String member);
}
