package it.polimi.ingsw.ServerController.rmi;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClientInterface;
import it.polimi.ingsw.ServerController.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by filippocollini on 21/06/17.
 */
public class RMIServer extends AbstractServer implements Callback{
    public RMIServer(ConnectionInterface connectionHandler) {
        super(connectionHandler);
    }

    private Object callbackObj;

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
    public synchronized int joinPlayer(String username, RMIClientInterface client) throws RemoteException{
        if(!(client instanceof RMIClientInterface))
            return Callback.FAILURE;
        System.out.println("sono nell'rmi server");
        getConnectionHandler().joinPlayer(new RMIPlayer(), username);

        return Callback.SUCCESS;
    }

    @Override
    public synchronized String sendObject(Object Obj) throws RemoteException {
        return "mueve tu cuerpo alegria macarena";
    }

    @Override
    public String sendLong(long val) throws RemoteException, Exception {
        return null;
    }

}
