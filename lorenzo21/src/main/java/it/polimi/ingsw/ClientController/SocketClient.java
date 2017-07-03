package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.Player;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
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
                comm.send((M) user);
                msg = (String) comm.receive();
            }
            System.out.println("login t'apposto");
        } catch (SocketException e) {
            e.printStackTrace();
        }



    }

    public String move(String msg){
        comm.send((M) msg);
        try {
            String answer = (String) comm.receive();
            System.out.println(answer);
            if (answer.equalsIgnoreCase(Server.EVENT_FAILED)){
                System.out.println("evento andato male");
                return Server.EVENT_FAILED;
            }else if (answer.equalsIgnoreCase(Server.EVENT_DONE)) {
                System.out.println("evento riuscito");
                return Server.EVENT_DONE;
            }else {
                System.out.println("serve interazione col client");
                return answer;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        //point that should never be reached
        return null;
    }

    @Override
    public String handleClientRequest(String request) {
        String answer = clientRules.responseHandler(request);

        if(!answer.equalsIgnoreCase(ClientRules.ERROR))
            comm.send((M) answer);

        String result = null;
        try {
            result = (String) comm.receive();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return result;
    }


}