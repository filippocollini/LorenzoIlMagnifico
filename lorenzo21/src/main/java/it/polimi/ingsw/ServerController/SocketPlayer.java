package it.polimi.ingsw.ServerController;

import java.io.IOException;
import java.net.Socket;

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
        out = new EventOutputStream(socket.getOutputStream());
        in = new EventInputStream(socket.getInputStream());
        rules= new Rules(in, out, callClient);
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
    private EventInputStream in;

    /**
     *
     */
    private EventOutputStream out;

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
                Object request = in.readObject();
                in.receiveEvent((Event)request);
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



}
