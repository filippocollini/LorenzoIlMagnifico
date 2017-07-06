package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.ServerController.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
        String uuid = UUID.randomUUID().toString();
        map.put(uuid, rmiPlayer);
        boolean used = getConnectionHandler().joinPlayer(rmiPlayer, username);
        if(false == used)
            return Callback.FAILURE;

        return uuid;
    }

    @Override
    public void sendRequest(String request) throws RemoteException {

    }

    public void marketMove(String uuid, String member, String cell) throws RemoteException{
        /*for (String s : map.keySet())
            System.out.println(" ... "+s);
        for (AbstractPlayer p : map.values())
            System.out.println(" ... "+p);*/

        AbstractPlayer player = map.get(uuid);
        //System.out.println(player);

        Stanza room = player.getRoom();
        //System.out.println(room);

        room.marketEvent(player, member, cell);

    }

    @Override
    public void endMove(String uuid) throws RemoteException {
        AbstractPlayer player = map.get(uuid);

        Stanza room = player.getRoom();

        room.endEvent(player);
    }

    @Override
    public void sendLong(long val) throws RemoteException, Exception {

    }

}
