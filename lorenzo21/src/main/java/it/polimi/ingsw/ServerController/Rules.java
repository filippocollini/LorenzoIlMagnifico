package it.polimi.ingsw.ServerController;

/**
 * 
 */
public class Rules {

    private final EventInputStream in;

    private final EventOutputStream out;

    private final CallClient callClient;

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
    }

    /**
     *
     */
    public void handleRequest(Object o){

    }

}