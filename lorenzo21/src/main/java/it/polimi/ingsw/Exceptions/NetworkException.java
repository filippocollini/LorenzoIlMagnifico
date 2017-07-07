package it.polimi.ingsw.Exceptions;

import java.rmi.RemoteException;

/**
 * Created by filippocollini on 07/07/17.
 */
public class NetworkException extends RemoteException {

    public NetworkException(){
        super();
    }

    public NetworkException(String s){
        super(s);
    }


}
