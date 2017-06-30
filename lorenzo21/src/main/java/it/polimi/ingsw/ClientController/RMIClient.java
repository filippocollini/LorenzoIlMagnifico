package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.rmi.Callback;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * 
 */
public class RMIClient<M extends Serializable, T extends Serializable> extends AbstractClient implements RMIClientInterface {

    private String username;
    static Callback server;
    private String host;
    private int port;


    public RMIClient(String host, int port) throws RemoteException {
        this.host=host;
        this.port=port;
        startClient();
    }


    public void startClient() {

        Callback server;

        try{
            Scanner sc = new Scanner(System.in);

            Registry reg = LocateRegistry.getRegistry(host, port);

            server = (Callback) reg.lookup(Callback.NAME);

            UnicastRemoteObject.exportObject(this,0);

            System.out.println("Client up on server");
            int result;

            do{
                System.out.println("Insert username:");
                String user = sc.nextLine();
                this.username = user;
                result = login(server, username, this);
                if (result == Callback.FAILURE)
                {
                    System.out.println("1. Couldn't send client object to server");
                }
                else
                    System.out.println("1. Success sending ClientObject to server");
            }while(result== Callback.FAILURE);



        } catch (RemoteException | NotBoundException e) {

        }

    }

    private int login(Callback server, String username, RMIClientInterface client) throws RemoteException {
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