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
     * Creates a ObjectInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */

    public EventInputStream(InputStream in) throws IOException {
        super();
    }

    /*

    public void invokeIsLegal(Event event){
        //Controllo
        //pattern command
        if(event.isLegal(event))
            receiveEvent(event);
        else {
            System.out.println("lancia eccezione");
        }
    }

    public void receiveEvent(Event event){

        rules
    }

    */
}
