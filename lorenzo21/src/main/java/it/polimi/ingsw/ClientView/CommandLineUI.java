package it.polimi.ingsw.ClientView;

import it.polimi.ingsw.ClientController.AbstractClient;
import it.polimi.ingsw.ClientController.RMIClient;
import it.polimi.ingsw.ClientController.SocketClient;
import it.polimi.ingsw.Exceptions.ClientException;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.*;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;
import it.polimi.ingsw.ServerController.states.ChooseFavorState;
import it.polimi.ingsw.ServerController.states.GameState;
import it.polimi.ingsw.ServerController.states.NonActionState;
import it.polimi.ingsw.ServerController.states.State;

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
    private static boolean ended = false;

    /**
     * Default constructor
     */
    public CommandLineUI() {
        choices = new ArrayList<>();
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


        this.client = client;

        new RequestHandler().run();

    }

    public void gameStarted(Game game){
        this.game = game;
        System.out.println("Game started");
    }

    public void esempio(){
        System.out.println("Let's start!");
    }

    public void notifyTurnStarted(){
        System.out.println("It's your turn! Make your move!");
        state = new GameState();

    }

    public void notifyActionMade(){
        System.out.println("Now you cannot make any family member move");
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
        if("Y".equalsIgnoreCase(response)){
            state = new GameState();
            System.out.println("Make another action");
        }else if ("S".equalsIgnoreCase(response))
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
        Scanner scanner = new Scanner(System.in);
        String request;
        String control = "";
        System.out.println("You have not enough resources!");
        state = new GameState();
        System.out.println("Make another action");
        request = scanner.nextLine();
        while("ko".equalsIgnoreCase(control))
            try {
                control = client.handle(request, state);
            } catch (RemoteException e) {
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
    }

    public void askForServants(){
        System.out.println("Do you want to pay servants? ");
        state = new GameState();

    }

    public void notifyError() {
        Scanner scanner = new Scanner(System.in);
        String request;
        String control ="";
        System.out.println("There was an error, try again");
        state = new GameState();
        System.out.println("Insert your action");
        request = scanner.nextLine();
        while("ko".equalsIgnoreCase(control))
            try {
                control = client.handle(request, state);
            } catch (RemoteException e) {
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
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
            if("color".equalsIgnoreCase(color))
                client.towerFreeMove(getUuid(), "color");
            else if ("territory".equalsIgnoreCase(color))
                client.towerFreeMove(getUuid(), "territory");
            else if ("ventures".equalsIgnoreCase(color))
                client.towerFreeMove(getUuid(), "ventures");
        }catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }
    }

    public void print(StringBuilder s) {
        System.out.println(s);
    }

    public void notifyEndGame() {
        ended = true;
    }


    private class RequestHandler implements Runnable{

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String request;
            String control = "ko";
            while(!ended){
                request = scanner.nextLine();
                if(state!=null) {
                    try {
                        while("ko".equalsIgnoreCase(control))
                            control = client.handle(request, state);
                    } catch (RemoteException e) {
                        LOG.log(Level.SEVERE, "Cannot reach the server", e);
                    }
                }
            }

        }
    }


}