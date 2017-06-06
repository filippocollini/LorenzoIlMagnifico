package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.SocketHandler;

/**
 * 
 */
public class SocketServer extends AbstractServer {

    /**
     * Default constructor
     */
    public SocketServer(IServer controller) {
        super(controller);
    }

    /**
     * 
     */
    private int port;

    private IServer controller;

    private ServerSocket serverSocket;

    @Override
    public void startServer(){

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                Socket socket= serverSocket.accept();
                executor.submit( () -> {
                    try {
                        new Thread(new SocketPlayer(controller, socket)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        //executor.shutdown();

    }

    @Override
    public Game createRoom(AbstractPlayer player) {
        return null;
    }

    @Override
    public void joinRoom(Stanza room, AbstractPlayer player) {

    }

    @Override
    public void loginPlayer(AbstractPlayer player) {

    }
}