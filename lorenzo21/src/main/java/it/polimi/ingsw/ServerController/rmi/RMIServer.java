package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.ServerController.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by filippocollini on 21/06/17.
 */
public class RMIServer extends AbstractServer implements Callback{

    public RMIServer(ConnectionInterface connectionHandler) {
        super(connectionHandler);
        map = new HashMap<>();
    }

    private Object callbackObj;

    private HashMap<String, AbstractPlayer> map;

    public void startRMIServer(int rmiPort) {

        Registry reg = null;
        try {
            reg = LocateRegistry.createRegistry(rmiPort);

            Callback brokerInt = (Callback) UnicastRemoteObject.exportObject(this, 0);

            reg.rebind(Callback.NAME, brokerInt);

        } catch (RemoteException e) {
            System.out.println("LANCIA ECCEZIONE");
        }
    }

    @Override
    public synchronized String joinPlayer(String username, RMIClientInterface client) throws RemoteException{
        if(!(client instanceof RMIClientInterface))
            return Callback.FAILURE;
        RMIPlayer rmiPlayer = new RMIPlayer(client);
        boolean used = getConnectionHandler().joinPlayer(rmiPlayer, username);
        if(false == used)
            return Callback.FAILURE;
        String uuid = UUID.randomUUID().toString();
        map.put(uuid, rmiPlayer);
        return uuid;
    }

    @Override
    public void sendRequest(String request) throws RemoteException {

    }

    public void marketMove(String uuid) throws RemoteException{
        map.get(uuid).getRoom().marketEvent(map.get(uuid));
    }

    @Override
    public void sendLong(long val) throws RemoteException, Exception {

    }

}
