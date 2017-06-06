package it.polimi.ingsw.ServerController;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by filippocollini on 04/06/17.
 */
public class EventInputStream extends ObjectInputStream {

    Rules rules;

    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public EventInputStream(InputStream in) throws IOException {
        super();
    }

    void receiveEvent(Event event){
        rules.handleRequest(event);
    }
}
