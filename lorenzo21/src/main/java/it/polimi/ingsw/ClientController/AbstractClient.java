package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.State;

import java.rmi.RemoteException;
import java.util.List;

/**
 * 
 */
public abstract class AbstractClient  {


    /**
     * Default constructor
     */
    public AbstractClient() {

    }

    public abstract String handleClientRequest(String request);

    /**
     *
     */
    private RMIClient rmiClient;

    /**
     *
     */
    private SocketClient socketClient;

    /**
     *
     */
    private AbstractPlayer player;


    public abstract void connect();

    public abstract void handle(String request, State state) throws RemoteException;

    public abstract void marketMove(String uuid) throws RemoteException;

    public abstract void towerMove(String uuid) throws RemoteException;

    public abstract void towerFreeMove(String uuid, String color) throws RemoteException;

    public abstract void palaceMove(String uuid) throws RemoteException;

    public abstract void harvestMove(String uuid) throws RemoteException;

    public abstract void productionMove(String uuid, List<Integer> choices) throws RemoteException;

    public abstract void fmChoice(String uuid, String choice) throws RemoteException;

    public abstract void favorChoice(String uuid) throws RemoteException;

    public abstract void secondfavorChoice(String uuid) throws RemoteException;

    public abstract void addServants(String uuid) throws RemoteException;

    public abstract void endMove(String uuid) throws RemoteException;


    public abstract void powerUp(String uuid, int nServants) throws RemoteException;

    public abstract String getUuid();
}