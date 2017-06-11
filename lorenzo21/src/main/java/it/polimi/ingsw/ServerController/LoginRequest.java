package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientController.ClientRules;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by filippocollini on 11/06/17.
 */
public class LoginRequest implements Event {

    ClientRules clientRules;
    IServer server;
    ObjectInputStream in;
    Socket socket;

    public LoginRequest() {
        try {
            in= new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLegal() {
        return askServer();
    }

    @Override
    public void eventHappened() {

    }

    public boolean askServer(){
        try {
            String username= in.readUTF();
            server.loginPlayer(username);
            return true;
        }catch(Exception e){
            System.out.println("Messaggio di risposta");
            return false;
        }
    }
}
