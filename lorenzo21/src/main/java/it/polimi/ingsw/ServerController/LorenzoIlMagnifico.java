package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.ClientView.CommandLineUI;
import it.polimi.ingsw.Exceptions.ClientException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filippocollini on 03/07/17.
 */
public class LorenzoIlMagnifico {

    private static final Logger LOG = Logger.getLogger(LorenzoIlMagnifico.class.getName());


    public static void main(String[] args) {
        CommandLineUI cli = new CommandLineUI();
        try {
            cli.start();
        } catch (ClientException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }
    }
}
