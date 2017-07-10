package it.polimi.ingsw.ServerController.socket;

import java.io.*;

/**
 * Created by filippocollini on 04/06/17.
 */

public class EventOutputStream extends ObjectOutputStream {

    /**
     * Creates a new data output stream to write data to the specified
     * underlying output stream. The counter <code>written</code> is
     * set to zero.
     *
     * @param out the underlying output stream, to be saved for later
     *            use.
     * @see FilterOutputStream#out
     */

    public  EventOutputStream(OutputStream out) throws IOException {
        super();
    }

    public void sendEvent(){

    }
}
