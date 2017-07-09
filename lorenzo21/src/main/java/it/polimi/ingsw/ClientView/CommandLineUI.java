package it.polimi.ingsw.ClientView;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClient;
import it.polimi.ingsw.ClientController.SocketClient;
import it.polimi.ingsw.Exceptions.ClientException;
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
    private List<Integer> choices;

    /**
     * Default constructor
     */
    public CommandLineUI() {
        choices = new ArrayList<>();
    }

    public void start() throws ClientException {

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

    public void notifyFMTooLow(int nServants, String event){
        System.out.println("Your family member is too low! Do you want to choose another one? (Y - yes, S - spend servants, any other key - make another action");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();
        if(response.equalsIgnoreCase("Y")){
            state = new GameState();
            System.out.println("Make another action");
        }else if (response.equalsIgnoreCase("S"))
            try {
                System.out.println("Adding the equivalent power of "+nServants+ " servants");
                client.powerUp(getUuid(), nServants);
            } catch (RemoteException e) {
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
        else
            System.out.println("Make another action");
    }

    private String getUuid(){
        return client.getUuid();
    }

    public void notifyChooseFavor(String event){
        System.out.println("Choose your palace favor");
        state = new ChooseFavorState();
        try {
            client.handle(event, state);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }
    }

    public void notifyNotEnoughResources(){
        System.out.println("You have not enough resources!");
        state = new GameState();
        System.out.println("Make another action");
    }

    public void askForServants(){
        System.out.println("Do you want to pay servants? ");
        state = new GameState();

    }

    public void notifyError() {
        System.out.println("There was an error, try again");
        state = new GameState();
        System.out.println("Insert your action");
    }

    public void notifyProductionChoice(String choice, String uuid) {
        System.out.println("Choose the effect between "+choice.substring(0,3) + " and "+choice.substring(4, 7));
        Scanner scanner = new Scanner(System.in);
        int request = scanner.nextInt();
        choices.add(request);
        try {
            client.productionMove(uuid, choices);
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }

    }

    public void notifyFreeTowerAction(String color) {
        try{
            if(color.equalsIgnoreCase("color"))
                client.towerFreeMove(getUuid(), "color");
            else if (color.equalsIgnoreCase("territory"))
                client.towerFreeMove(getUuid(), "territory");
            else if (color.equalsIgnoreCase("ventures"))
                client.towerFreeMove(getUuid(), "ventures");
        }catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }
    }


    private class RequestHandler implements Runnable{

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String request;
            while(true){
                request = scanner.nextLine();
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