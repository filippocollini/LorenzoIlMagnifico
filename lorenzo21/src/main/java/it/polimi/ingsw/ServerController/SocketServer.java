package it.polimi.ingsw.ServerController;

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
    public void startServer() throws IOException{

        serverSocket = new ServerSocket(port);
        while(true){
            new ConnectionHandler(serverSocket.accept());
        }

    }

    private class ConnectionHandler extends Thread{
        private final Socket socket;

        public ConnectionHandler(Socket socket){
            this.socket=socket;
            start();
        }

        public void run() {
            try{
                SocketPlayer socketPlayer = new SocketPlayer(controller, socket);
                new Thread(socketPlayer).start();
            }catch(IOException e){
                System.out.println("errore");
            }

        }

    }


}