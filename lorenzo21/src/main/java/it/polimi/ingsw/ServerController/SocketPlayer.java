package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.SocketClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * TODO binding between Client and socketPlayer
 */

/**
 * serve per la comunicazione del client col server
 */
public class SocketPlayer extends AbstractPlayer implements Runnable {

    /**
     * Default constructor
     */
    public SocketPlayer(IServer controller, Socket socket) throws IOException{
        this.socket=socket;
        this.controller=controller;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        rules= new Rules();//con che parametri Rules?
    }

    /**
     * 
     */
    private Socket socket;

    /**
     *
     */
    private IServer controller;

    /**
     *
     */
    private ObjectInputStream in;

    /**
     *
     */
    private ObjectOutputStream out;

    /**
     *
     */
    private Rules rules;

    /**
     * 
     */
    @Override
    public void run() {
        while (true){
            try {
                Object request=in.readObject();
                rules.handleRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    SocketPlayer bind(SocketClient client, SocketPlayer handler) {
        return (client, handler) ->
                handler.handle(client, handler);
    }

    public void handle(SocketClient client, SocketPlayer handler){

    }

}
