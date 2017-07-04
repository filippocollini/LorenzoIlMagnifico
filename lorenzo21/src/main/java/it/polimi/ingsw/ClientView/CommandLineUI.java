package it.polimi.ingsw.ClientView;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClient;
import it.polimi.ingsw.ClientController.SocketClient;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.GameState;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.State;
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
    private Game game;
    private AbstractClient client;
    private State state;

    /**
     * Default constructor
     */
    public CommandLineUI() {

    }

    public void start() {
        Server server = new Server();

        String connection;

        Scanner scanner = new Scanner(System.in);

        do{
            System.out.println("Insert the type of connection (socket or rmi)");
            connection = scanner.nextLine();
        }while(!connection.matches("rmi||socket"));

        AbstractClient client=null;

        if(connection.matches("rmi")){
            try {
                client = new RMIClient(HOST, RMIPORT, this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else if(connection.matches("socket")){
            try {
                Socket socket = new Socket(HOST, SOCKETPORT);
                client = new SocketClient(new SocketPlayer(socket), HOST, SOCKETPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        client.connect();

        this.client = client;


    }

    private void move() throws RemoteException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Fai la tua mossa: ");
        String request = scanner.nextLine();
        client.handle(request, state);

    }

    public void gameStarted(Game game){
        this.game = game;
        System.out.println("Game started, auannad!");
    }

    public void esempio(){
        System.out.println("iniziamo, auannad!");
    }

    public void notifyTurnStarted(){
        System.out.println("E' il tuo turno!");
        state = new GameState();
        try {
            move();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}