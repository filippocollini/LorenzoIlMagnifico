package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.Message;
import it.polimi.ingsw.ServerController.Player;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * 
 */
public class SocketClient<M extends Serializable> extends AbstractClient implements Runnable {

    private boolean done = false;
    private SocketPlayer<M> comm;

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        try {
            String msg=Message.LOGINKO;
            while(msg.equals(Message.LOGINKO)){
                msg = (String) comm.receive();
                System.out.println(msg);
                String user = sc.nextLine();
                comm.send((M) user);
                //ora arriva la risposta del login
                msg = (String) comm.receive();
            }
            System.out.println("login t'apposto");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for the match to start...");

        try {
            String started = (String) comm.receive();
            System.out.println(started);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(!done){
            System.out.println("Inserisci richiesta");
            String msg = sc.nextLine();
            comm.send((M) msg);
        }
        comm.close();
    }

    public SocketClient(SocketPlayer<M> sc, String host, int port) throws IOException {

        System.out.println("SocketClient started");
        comm = sc;
        this.run();
    }

}