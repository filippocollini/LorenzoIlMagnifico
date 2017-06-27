package it.polimi.ingsw.ServerController.socket;

import it.polimi.ingsw.ServerController.AbstractPlayer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;

/**
 * Created by filippocollini on 14/06/17.
 */

public class SocketPlayer<M extends Serializable> extends AbstractPlayer<M>{

    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public SocketPlayer(Socket sc) {
        socket = sc;

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch (IOException ex) {

        }
    }

    public M receive() throws SocketException {
        try{
            return ((M) in.readObject());
        }catch(NoSuchElementException | ClassNotFoundException | IOException e){
            throw new SocketException("Socket failed");
        }
    }

    public void send(M message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {

        }
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new AssertionError("error closing the socket", e);
        }
    }

    public Socket getClient(){
        return socket;
    }

}
