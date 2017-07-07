package it.polimi.ingsw.Exceptions;

/**
 * Created by filippocollini on 08/07/17.
 */
public class ClientException extends NetworkException{

    public ClientException(){
        super();
    }

    public ClientException(String s){
        super(s);
    }
}
