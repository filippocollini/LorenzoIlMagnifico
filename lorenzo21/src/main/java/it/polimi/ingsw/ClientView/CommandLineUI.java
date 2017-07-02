package it.polimi.ingsw.ClientView;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClient;
import it.polimi.ingsw.ClientController.SocketClient;
import it.polimi.ingsw.ServerController.Rules;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;

/**
 * 
 */
public class CommandLineUI extends AbstactUI {

    private static final int SOCKETPORT= 7771;
    private static final int RMIPORT= 7772;
    private static final String HOST = "127.0.0.1";

    /**
     * Default constructor
     */
    public CommandLineUI() {

    }

    public static void main(String[] args) {
        Server server = new Server();

        Scanner scanner = new Scanner(System.in);

        String connection;

        do{
            System.out.println("Insert the type of connection (socket or rmi)");
            connection = scanner.nextLine();
        }while(!connection.matches("rmi||socket"));

        AbstractClient client=null;

        if(connection.matches("rmi")){
            try {
                client = new RMIClient(HOST, RMIPORT);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else if(connection.matches("socket")){
            try {
                Socket socket = new Socket(HOST, SOCKETPORT);
                client = new SocketClient(new SocketPlayer(socket), HOST, SOCKETPORT);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Rules rules;

        if(client==null)
            System.out.println("manda eccezione");
        else
            rules = new Rules();

    }


}