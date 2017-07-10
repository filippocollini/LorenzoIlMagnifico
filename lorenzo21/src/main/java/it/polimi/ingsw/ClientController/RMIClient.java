package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ClientView.CommandLineUI;
import it.polimi.ingsw.GameModelServer.Game;
import it.polimi.ingsw.GameModelServer.Risorsa;
import it.polimi.ingsw.ServerController.State;
import it.polimi.ingsw.ServerController.rmi.Callback;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
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
    private List<Risorsa> rewards;
    private boolean secondChoice = false;



    public RMIClient(String host, int port, CommandLineUI cli) throws RemoteException {
        this.host=host;
        this.port=port;
        this.cli = cli;
        //this.rewards = new ArrayList<>();
        startClient();
    }


    public void startClient() {

        Callback server;

        try{
            Scanner sc = new Scanner(System.in);

            Registry reg = LocateRegistry.getRegistry(host, port);

            System.out.println("registry fatto");

            server = (Callback) reg.lookup(Callback.NAME);

            System.out.println("lookup fatto");

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
            e.printStackTrace();
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
    public void notifyFMTooLow(int nServants, String event) throws RemoteException {
        cli.notifyFMTooLow(nServants, event);
    }

    @Override
    public void notifyChooseFavor(String event) throws RemoteException {
        cli.notifyChooseFavor(event);
    }

    @Override
    public void notifyNotEnoughResources() throws RemoteException {
        cli.notifyNotEnoughResources();
    }

    @Override
    public void askForServants() throws RemoteException {

    }

    @Override
    public void notifyEndTurn() throws RemoteException {
        cli.notifyEndTurn();
    }

    @Override
    public void notifyError() throws RemoteException {
        cli.notifyError();
    }

    @Override
    public void notifyProductionChoice(String choice, String uuid) throws RemoteException {
        cli.notifyProductionChoice(choice, uuid);
    }

    @Override
    public void notifyFreeTowerAction(String color) {
        cli.notifyFreeTowerAction(color);
    }

    @Override
    public void print(StringBuilder s) throws RemoteException {
        cli.print(s);
    }


    public String handle(String request, State state) throws RemoteException {
        System.out.println("gestisco");
        return state.handle(request, this, this.uuid);
    }

    public void powerUp(String uuid, int nServants) throws RemoteException{
        String member = askMember();
        server.powerUpMove(uuid, member, nServants);
    }

    public void marketMove(String uuid) throws RemoteException {
        /*System.out.println("server: ");
        System.out.println(server);
        System.out.println("uuid: "+uuid);*/
        String member = askMember();
        String cell = askCellMarket();
        server.marketMove(uuid, member, cell);
    }

    public void towerMove(String uuid) throws RemoteException {
        String member = askMember();
        String tower = askTower();
        int floor = askFloor();
        boolean free = false;
        server.towerMove(uuid
                , member
                , tower
                , floor
                , free);
    }

    public void towerFreeMove(String uuid, String color) throws RemoteException {
        String tower;
        if (color.equalsIgnoreCase("color"))
            tower = askTower();
        else{
            tower = color;
        }
        int floor = askFloor();
        boolean free = true;
        server.towerMove(uuid, null, tower, floor, free);
    }

    public void harvestMove(String uuid) throws RemoteException {
        String member = askMember();
        server.harvestMove(uuid, member);
    }

    @Override
    public void productionMove(String uuid, List<Integer> choices) throws RemoteException {
        String member = askMember();
        server.productionMove(uuid, member, choices);
    }

    public void fmChoice(String uuid, String choice) throws RemoteException{
        //server.fmChoiceMove(uuid, choice);
    }

    @Override
    public void favorChoice(String uuid) throws RemoteException {
        String choice = choosePalaceFavor();
        String member = askMember();
        server.favorChoiceMove(uuid, member, choice);
    }

    @Override
    public void secondfavorChoice(String uuid) throws RemoteException {
        String choice = chooseSecondPalaceFavor();
        server.secondFavorChoiceMove(uuid, choice);
    }

    @Override
    public void addServants(String uuid) throws RemoteException {

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

    public String askTower(){
        String type;
        System.out.println("Which tower do you want to occupy? territory - buildings - ventures - characters");
        Scanner scanner = new Scanner(System.in);
        type = scanner.nextLine();
        while(!(type.equalsIgnoreCase("territory") || type.equalsIgnoreCase("characters")
                || type.equalsIgnoreCase("ventures") || type.equalsIgnoreCase("buildings"))){
            System.out.println("Error on input : Which tower do you want to occupy? territory - buildings - ventures - characters");
            Scanner scanning = new Scanner(System.in);
            type = scanning.nextLine();
        }
        return type;
    }

    public int askFloor(){
        boolean correct = true;
        int floor = 0;
        System.out.println("Where do you want to put your FM? 1 - 3 - 5 - 7");
        while(correct) {
            Scanner scandice = new Scanner(System.in);
            floor = scandice.nextInt();
            if(!(floor == 1 || floor == 3 || floor == 5 || floor == 7)) {
                System.out.println("Error on input: Where do you want to put your FM? 1 - 3 - 5 - 7");
            }else
                correct=false;
        }
        return floor;
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

    public String choosePalaceFavor() { //questa list<risorsa> è la lista parsata dei possibili bonus palazzo
        List<Risorsa> rewards = new ArrayList<>();
        Risorsa res = new Risorsa();
        String choice="";
        int i;
        System.out.println("Choose your palace favor(type the name):\n" + "2 Coins\n" + "1+1 WoodStone\n" + "2 Servants\n" + "2 MilitaryPoints\n" + "1 FaithPoints\n");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextLine();
        while(!(choice.equals("Coins") || choice.equals("WoodStone") || choice.equals("Servants") || choice.equals("MilitaryPoints") || choice.equals("FaithPoints"))){
            System.out.println("Error in input!");
            System.out.println("Choose your palace favor(type the name):\n" + "2 Coins\n" + "1+1 WoodStone\n" + "2 Servants\n" + "2 MilitaryPoints\n" + "1 FaithPoints\n");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }

        this.rewards = rewards;

        return choice;
    }

    public String chooseSecondPalaceFavor(){

        String choice = "";

        for (Risorsa previouschoice : this.rewards) {
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();
            while ((choice.equals("WoodStone") && previouschoice.gettipo().equals("Woods")) || choice.equals(previouschoice.gettipo())) {
                System.out.println("You've already chosen this type of favor, type another type");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextLine();
            }
        }
        return choice;
    }

    public void palaceMove(String uuid) throws RemoteException{
        if(secondChoice==false){
            String palaceFavor = choosePalaceFavor();
            String member = askMember();
            server.palaceMove(uuid, member, palaceFavor);

        }else{
            String palaceFavor = chooseSecondPalaceFavor();
            String member = askMember();
            server.palaceMove(uuid, member, palaceFavor);
            secondChoice = false;
        }
        secondChoice = true;

    }



    public void endMove(String uuid) throws RemoteException {
        server.endMove(uuid);
    }

    @Override
    public void playLeaderCard(String uuid) throws RemoteException {
        System.out.println("Insert the name of the leader card without spaces between the words");
        Scanner scanner = new Scanner(System.in);
        String card = scanner.nextLine();
        server.leaderMove(uuid, card);
    }

    @Override
    public String getUuid() {
        return this.uuid;
    }

    @Override
    public void discardLeaderCard(String uuid) throws RemoteException {
        System.out.println("Insert the name of the leader card without spaces between the words");
        Scanner scanner = new Scanner(System.in);
        String card = scanner.nextLine();
        String palaceFavor = choosePalaceFavor();
        server.discardLeaderCard(uuid, card, palaceFavor);
    }

    @Override
    public void showPlayerGoods(String uuid) throws RemoteException {
        server.showPlayerGoods(uuid);
    }

    @Override
    public void showOtherPlayers(String uuid) throws RemoteException {
        server.showOtherPlayers(uuid);
    }

    @Override
    public void showBoard(String uuid) throws RemoteException {
        server.showBoard(uuid);
    }


    @Override
    public String handleClientRequest(String request) {
        return null;
    }

    @Override
    public void connect() {

    }
}