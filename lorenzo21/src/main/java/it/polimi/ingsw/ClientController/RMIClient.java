package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ClientView.CommandLineUI;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.ServerController.State;
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
    private Callback server; //TODO NullPointerException
    private String host;
    private int port;
    CommandLineUI cli;
    private String uuid;



    public RMIClient(String host, int port, CommandLineUI cli) throws RemoteException {
        this.host=host;
        this.port=port;
        this.cli = cli;
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
            String result;

            do{
                System.out.println("Insert username:");
                String user = sc.nextLine();
                this.username = user;
                result = login(server, username, this);
                if (result.equals(Callback.FAILURE))
                {
                    System.out.println("1. Couldn't send client object to server");
                }
                else
                    System.out.println("1. Success sending ClientObject to server");
            }while(result.equals(Callback.FAILURE));

            this.uuid=result;

            this.server = server;

        } catch (RemoteException | NotBoundException e) {

        }

    }

    private String login(Callback server, String username, RMIClientInterface client) throws RemoteException {
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

    @Override
    public void dispatchMessage(Object msg) throws RemoteException {

    }

    @Override
    public void notifyGameStarted(Game game) throws RemoteException {
        cli.gameStarted(game);
    }

    @Override
    public void notifyProva() throws RemoteException {
        cli.esempio();
    }

    @Override
    public void notifyTurnStarted() throws RemoteException {
        cli.notifyTurnStarted();
    }

    @Override
    public void notifyActionMade() throws RemoteException {
        cli.notifyActionMade();
    }

    @Override
    public void notifyEndTurn() throws RemoteException {
        cli.notifyEndTurn();
    }


    public void handle(String request, State state) throws RemoteException {
        System.out.println("gestisco");
        state.handle(request, this, this.uuid);
    }

    public void marketMove(String uuid) throws RemoteException {
        /*System.out.println("server: ");
        System.out.println(server);
        System.out.println("uuid: "+uuid);*/
        String member = askMember();
        String cell = askCellMarket();
        server.marketMove(uuid, member, cell);
    }

    public String askMember() {
        String choice;
        System.out.println("Which FM do you want to use? White - Black - Orange - Neutral"); //TODO
        Scanner scan = new Scanner(System.in);
        choice = scan.nextLine();
        while(!(choice.equals("White") || choice.equals("Black") || choice.equals("Orange") || choice.equals("Neutral"))) {
            System.out.println("Error on input : Which FM do you want to use? White - Black - Orange - Neutral"); //TODO
            Scanner scans = new Scanner(System.in);
            choice = scans.nextLine();
        }
        return choice;
    }

    public String askCellMarket(){
        String choice;

        System.out.println("What do you want to pick from Market?" +
                    "5 Coins(C) - 5 Servants(S) - 3MP and 2 Coins(MPC) - 2PalaceFavors(PF) ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextLine();
        while(!choice.equalsIgnoreCase("C") && !choice.equalsIgnoreCase("S") &&
                !choice.equalsIgnoreCase("MPC") && !choice.equalsIgnoreCase("PF")){
            System.out.println("Error in input");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }
        return choice;
    }

    public void endMove(String uuid) throws RemoteException {
        server.endMove(uuid);
    }


    @Override
    public String handleClientRequest(String request) {
        return null;
    }

    @Override
    public void connect() {

    }
}