package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.AbstractServer;
import it.polimi.ingsw.ServerController.ConnectionInterface;
import it.polimi.ingsw.ServerController.Server;
import it.polimi.ingsw.ServerController.rmi.RMIServer;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filippocollini on 27/06/17.
 */
public class SocketServer extends AbstractServer{

    private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());

    private boolean doneSub = false;
    private ServerSocket socketSubscriber = null;
    private Thread subscriberListener;

    public SocketServer(ConnectionInterface connectionHandler) {
        super(connectionHandler);
    }


    public void startSocketSubscriberListener(int socketPort) {
        subscriberListener = new Thread() {
            @Override
            public void run() {
                try {
                    socketSubscriber = new ServerSocket(socketPort);
                    while (!doneSub) {
                        try {
                            Socket client = socketSubscriber.accept();
                            SocketSubscriberHandler handler = new SocketSubscriberHandler(getConnectionHandler(), new SocketPlayer<Serializable>(client));
                            handler.start();
                            System.out.println("Adding new Player");
                        } catch (SocketTimeoutException | SocketException e) {
                            LOG.log(Level.SEVERE, "Cannot reach the server", e);
                        }
                    }
                    socketSubscriber.close();
                } catch (IOException e) {
                    throw new AssertionError("Error closing the socket", e);
                } finally {
                    if (socketSubscriber != null) {
                        try {
                            socketSubscriber.close();
                        } catch (IOException e) {
                            LOG.log(Level.SEVERE, "Cannot reach the server", e);
                        }
                    }
                }
            }
        };
        subscriberListener.start();

    }
}


