package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.states.State;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This class represents the client when the type of connection that has been chosed is RMI
 *  We only implemented the connection of the client
 */
public class SocketClient<M extends Serializable> extends AbstractClient  {

    private static final Logger LOG = Logger.getLogger(SocketClient.class.getName());

    private boolean done = false;
    private SocketPlayer<M> comm;


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
            System.out.println("login done");
        } catch (SocketException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }



    }

    public String handle(String msg, State state){
        comm.send((M) msg, state);
        try {
            String answer = (String) comm.receive();
            System.out.println(answer);
            if (answer.equalsIgnoreCase(Server.EVENT_FAILED)){
                System.out.println("Event failed");
                return "ko";
            }else if (answer.equalsIgnoreCase(Server.EVENT_DONE)) {
                System.out.println("Event done");
                return "ok";
            }else {
                System.out.println("Need client interation");

            }

        } catch (SocketException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
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


}