package it.polimi.ingsw.ServerController;

import java.io.Serializable;

/**
 * Created by filippocollini on 26/05/17.
 */
public interface Event extends Serializable {

    public boolean isLegal();
    public String eventHappened();
}
