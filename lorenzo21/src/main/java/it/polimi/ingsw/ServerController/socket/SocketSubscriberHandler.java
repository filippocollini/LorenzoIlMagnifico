package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.*;
import it.polimi.ingsw.ServerController.socket.SocketPlayer;
import java.io.Serializable;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by filippocollini on 14/06/17.
 */

public class SocketSubscriberHandler<M extends Serializable,T extends Serializable> extends Thread implements PlayerInterface<M> {

    private ConcurrentLinkedQueue<M> buffer = new ConcurrentLinkedQueue<M>();

    private Rules rules;

    ConnectionInterface<M, T> broker;

    SocketPlayer<Serializable> comm; //TODO astrarlo per permettere anche la connessione RMI

    public SocketSubscriberHandler(ConnectionInterface<M,T> b, SocketPlayer<Serializable> sc) {
        comm = sc;
        broker = b;
        rules = new Rules(comm);
    }
    public void dispatchMessage(M msg) {
        buffer.add(msg);
        synchronized(buffer){
            buffer.notify();
        }
    }

    @Override
    public void notifyCountdownStarted(M msg) throws RemoteException {

    }

    @Override
    public void run() {
        login();
        System.out.println("login fatto");
        comm.send(Message.LOGINOK);
        //TODO sincronizzare questo in base al turno
        while(!isDone()){
            try {
                String request = (String) comm.receive();
                System.out.println("richiesta ricevuta");
                rules.handleRequest(request);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(){
        String username="";
        boolean correct=false;
        while (!correct) {
            try {
                comm.send("Insert Username to Login: ");
                username = (String) comm.receive();
                System.out.println("Ricevuto il nome");
            } catch (SocketException e) {
                e.printStackTrace();
            }
            correct=broker.joinPlayer(comm, username);
            if(correct==false)
                comm.send(Message.LOGINKO);
            /*if (room != null) {
                broker.subscribe(this, (T) room);
                correct = true;
                System.out.println("Player Joined");
                comm.send("Player joined");
                //START TIMEOUT
            } else
                comm.send("Username not available, try again!\n");
            */

        }
    }

    private boolean done = false;
    private boolean isDone() {
        return done;
    }
    public void finish(){
        done=true;
    }


}
