package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.State;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * 
 */
public class SocketClient<M extends Serializable> extends AbstractClient  {

    private boolean done = false;
    private SocketPlayer<M> comm;
    private ClientRules clientRules;


    public void connect() {
        login();

        System.out.println("Waiting for the match to start...");

        /*//controllo che sia il turno di questo client
        String ahead="";

        while(ahead!=null){
            System.out.println("Inserisci la richiesta: ");

            Scanner sc = new Scanner(System.in);
            String request = sc.nextLine();
            ahead = move(request);
            if (!ahead.equalsIgnoreCase(Server.EVENT_DONE) && !ahead.equalsIgnoreCase(Server.EVENT_FAILED)) {
                request = sc.nextLine();
                handleClientRequest(request);
            }
        }


        try {
            String started = (String) comm.receive();
            System.out.println(started);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        */
    }

    public SocketClient(SocketPlayer<M> sc, String host, int port) throws IOException {

        System.out.println("SocketClient started");
        comm = sc;
        clientRules = new ClientRules();
        //this.connect();
    }

    private void login(){
        Scanner sc = new Scanner(System.in);
        try {
            String msg=Message.LOGINKO;
            while(msg.equals(Message.LOGINKO)){
                System.out.println("Insert username to login: ");
                String user = sc.nextLine();
                comm.send((M) user, null);
                msg = (String) comm.receive();
            }
            System.out.println("login t'apposto");
        } catch (SocketException e) {
            e.printStackTrace();
        }



    }

    public String handle(String msg, State state){
        comm.send((M) msg, state);
        try {
            String answer = (String) comm.receive();
            System.out.println(answer);
            if (answer.equalsIgnoreCase(Server.EVENT_FAILED)){
                System.out.println("evento andato male");
                return "ko";
            }else if (answer.equalsIgnoreCase(Server.EVENT_DONE)) {
                System.out.println("evento riuscito");
                return "ok";
            }else {
                System.out.println("serve interazione col client");

            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void marketMove(String uuid) throws RemoteException {

    }

    @Override
    public void towerMove(String uuid) throws RemoteException {

    }

    @Override
    public void towerFreeMove(String uuid, String color) throws RemoteException {

    }

    @Override
    public void palaceMove(String uuid) throws RemoteException {

    }

    @Override
    public void harvestMove(String uuid) throws RemoteException {

    }

    @Override
    public void productionMove(String uuid, List<Integer> choices) throws RemoteException {

    }

    @Override
    public void fmChoice(String uuid, String choice) throws RemoteException {

    }

    @Override
    public void favorChoice(String uuid) throws RemoteException {

    }

    @Override
    public void secondfavorChoice(String uuid) throws RemoteException {

    }

    @Override
    public void addServants(String uuid) throws RemoteException {

    }

    @Override
    public void endMove(String uuid) throws RemoteException {

    }

    @Override
    public void playLeaderCard(String uuid) throws RemoteException {

    }

    @Override
    public void powerUp(String uuid, int nServants) throws RemoteException {

    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public void discardLeaderCard(String uuid) throws RemoteException {

    }

    @Override
    public void showPlayerGoods(String uuid) throws RemoteException {

    }

    @Override
    public void showOtherPlayers(String uuid) throws RemoteException {

    }

    @Override
    public void showBoard(String uuid) throws RemoteException {

    }

    @Override
    public String handleClientRequest(String request) {
        /*String answer = clientRules.responseHandler(request);

        if(!answer.equalsIgnoreCase(ClientRules.ERROR))
            comm.send((M) answer, );
        */
        String result = null;
        /*try {
            result = (String) comm.receive();
        } catch (SocketException e) {
            e.printStackTrace();
        }*/

        return result;
    }


}