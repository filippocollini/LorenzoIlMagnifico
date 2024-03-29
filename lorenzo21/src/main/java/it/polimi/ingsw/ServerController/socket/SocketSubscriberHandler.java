package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.*;
import it.polimi.ingsw.ServerController.states.GameState;

import java.io.Serializable;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filippocollini on 14/06/17.
 */

public class SocketSubscriberHandler<M extends Serializable,T extends Serializable> extends Thread implements PlayerInterface<M> {

    private static final Logger LOG = Logger.getLogger(SocketSubscriberHandler.class.getName());

    private ConcurrentLinkedQueue<M> buffer = new ConcurrentLinkedQueue<M>();

    private Rules rules;

    ConnectionInterface<M, T> broker;

    SocketPlayer<Serializable> comm;

    public SocketSubscriberHandler(ConnectionInterface<M,T> b, SocketPlayer<Serializable> sc) {
        comm = sc;
        broker = b;
        rules = new Rules();
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
        comm.send(Message.LOGINOK, null);

        String result;
        receiveRequest();

    }

    private void login(){
        String username="";
        boolean correct=false;
        while (!correct) {
            try {
                username = (String) comm.receive();
            } catch (SocketException e) {
                LOG.log(Level.SEVERE, "Cannot reach the server", e);
            }
            correct=broker.joinPlayer(comm, username);
            if(correct==false)
                comm.send(Message.LOGINKO, new GameState());
        }
    }

    public String receiveRequest(){
        String request = null;
        try {
            request = (String) comm.receive();
        } catch (SocketException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
        }
        String result = "...";
        try {
            System.out.println("richiesta ricevuta");
            result=broker.handleRequest(request);
            comm.send(result, new GameState());
            if (!result.equalsIgnoreCase(Server.EVENT_DONE) && !result.equalsIgnoreCase(Server.EVENT_FAILED));
            System.out.println(comm.receive());
        } catch (SocketException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);

        }finally {
            comm.close();
        }
        return result;
    }

    private boolean done = false;
    private boolean isDone() {
        return done;
    }
    public void finish(){
        done=true;
    }


}
