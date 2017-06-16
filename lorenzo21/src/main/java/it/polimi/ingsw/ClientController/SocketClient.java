package it.polimi.ingsw.ClientController;

import it.polimi.ingsw.ServerController.SocketPlayer;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * 
 */
public class SocketClient<M extends Serializable> extends Thread {

    private boolean done = false;
    private SocketPlayer<M> comm;

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        try {
            String msg = (String) comm.receive();
            System.out.println(msg);
            String user = sc.nextLine();
            comm.send((M) user);
            //ora arriva la risposta del login
            msg = (String) comm.receive();
            System.out.println(msg);
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

    public SocketClient(SocketPlayer<M> sc) {
        comm = sc;
    }

    public static void main(String [] argv){

        try {
            System.out.println("SocketClient started");

            Scanner sc = new Scanner(System.in);

            Socket socket = new Socket("127.0.0.1", 7771);
            SocketClient<String> server = new SocketClient<String>(new SocketPlayer<>(socket));
            server.start();

            /*System.out.println("(Type 'exit' to stop...)");
            String command = "";
            while(!command.equals("exit")){
                command = sc.nextLine();
            }

            server.done = true;
            socket.close();
            sc.close();*/

        } catch (IOException e) {

        }

    }

}