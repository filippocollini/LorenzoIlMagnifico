package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.rmi.Callback;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 */
public class RMIClient<M extends Serializable, T extends Serializable> extends AbstractClient implements RMIClientInterface {

    static Callback server;
    String username;

    public RMIClient(String username, Callback server) throws RemoteException {
        this.username=username;
        this.server=server;

    }

    public void startClient() {

        try{
            Registry reg = LocateRegistry.getRegistry(7772);

            Callback server = (Callback) reg.lookup(Callback.NAME);

            UnicastRemoteObject.exportObject(this,0);

            System.out.println("Client up on server");

            int result = login(server, username, this);
            if (result == Callback.FAILURE)
            {
                System.out.println("1. Couldn't send client object to server");
            }
            else
                System.out.println("1. Success sending ClientObject to server");

        } catch (RemoteException | NotBoundException e) {

        }

    }

    private int login(Callback server, String username, AbstractClient client) throws RemoteException {
        return server.joinPlayer(username, client);
    }

    @Override
    public void notifyPlayerLogged(int logged) throws RemoteException {
        if(logged==1)
            System.out.println("Player correctly logged");
        else {
            System.out.println("Error logging player, username not available, try again!");
            login(server, username, this);
        }
    }

    @Override
    public void notify2(long val1, long val2) throws RemoteException, Exception {

    }

}