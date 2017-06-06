package it.polimi.ingsw.ServerController;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 
 */
public class Rules {

    private final EventInputStream in;

    private final EventOutputStream out;

    private final CallClient callClient;

    private final HashMap <String, Handler> eventMap;

    /**
     * Default constructor
     * @param in
     * @param out
     * @param callClient
     */
    public Rules(EventInputStream in, EventOutputStream out, CallClient callClient) {
        this.in = in;
        this.out = out;
        this.callClient = callClient;
        eventMap= new HashMap();
        createMapping();
    }

    private void createMapping() {
        eventMap.put(Message.LOGIN, this::login);

    }



    /**
     *
     */
    public void handleRequest(Object o){

    }

    public void login(){
        try {
            String username= (String) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void FMOnMarket(){
        in.receiveEvent(new FMonMarket());
    }
    



    private interface Handler{

        public void handle();
    }

}