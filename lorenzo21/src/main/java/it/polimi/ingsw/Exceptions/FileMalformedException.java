package it.polimi.ingsw.Exceptions;

import java.io.IOException;

/**
 * Created by filippocollini on 07/07/17.
 */
public class FileMalformedException extends IOException {

    public FileMalformedException() {
        super();
    }

    public FileMalformedException(String s) {
        super(s);
    }
}
