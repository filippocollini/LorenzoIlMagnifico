package it.polimi.ingsw.ClientController;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

/**
 *  interfaccia condivisa tra socketClient e RMIClient
 */
public interface IClient<M extends Serializable, T extends Serializable> extends Remote {


}