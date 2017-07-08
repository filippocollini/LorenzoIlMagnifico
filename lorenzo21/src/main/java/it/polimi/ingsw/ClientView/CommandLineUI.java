package it.polimi.ingsw.ClientView;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClient;
import it.polimi.ingsw.ClientController.SocketClient;
import it.polimi.ingsw.Exceptions.ClientException;
import it.polimi.ingsw.Exceptions.NetworkException;
import it.polimi.ingsw.GameModelServer.DevelopementCard;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.*;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class CommandLineUI extends AbstactUI {

    private static final Logger LOG = Logger.getLogger(CommandLineUI.class.getName());

    private static final int SOCKETPORT= 7771;
    private static final int RMIPORT= 7772;
    private static final String HOST = "127.0.0.1";
    private Game game;
    private AbstractClient client;
    private State state;
    public boolean needInput = true;
    public String request="";

    /**
     * Default constructor
     */
    public CommandLineUI() {
    }

    public void start() throws ClientException {

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
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
        }else if(connection.matches("socket")){
            try {
                Socket socket = new Socket(HOST, SOCKETPORT);
                client = new SocketClient(new SocketPlayer(socket), HOST, SOCKETPORT);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
        }

        client.connect();

        if (client != null) {
            this.client = client;
        }else
            throw new ClientException("Client is not connected");


        new RequestHandler().run();

    }

    public void gameStarted(Game game){
        this.game = game;
        System.out.println("Game started, auannad!");
    }

    public void esempio(){
        System.out.println("iniziamo, auannad!");
    }

    public void notifyTurnStarted(){
        System.out.println("It's your turn! Make your move!");
        state = new GameState();

    }

    public void notifyActionMade(){
        System.out.println("Ora puoi solo vedere, ma non toccare!");
        state = new NonActionState();
    }

    public void notifyEndTurn(){
        System.out.println("Turn ended");
        state = null;
    }

    public void notifyFMTooLow(){
        System.out.println("Your family member is too low! Do you want to choose another one? (Y - yes, any other key - make another action");
        state = new GameState();

    }

    public void notifyChooseFavor(){
        System.out.println("Choose your palace favor");
        //mettere needInput = false
        state = new GameState();
    }

    public void notifyChooseSecondFavor(){

    }

    public void notifyNotEnoughResources(){
        System.out.println("It's your turn! Make your move!");
        state = new GameState();

    }

    public void askForServants(){
        System.out.println("It's your turn! Make your move!");
        state = new GameState();

    }


    private class RequestHandler implements Runnable{

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while(true){
                if (needInput) {
                    request = scanner.nextLine();
                }
                if(state!=null) {
                    try {
                        client.handle(request, state);
                    } catch (RemoteException e) {
                        LOG.log(Level.SEVERE, "Cannot reach the server", e);
                    }
                }
            }

        }
    }


}