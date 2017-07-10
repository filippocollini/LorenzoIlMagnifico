package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.Exceptions.NetworkException;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filippocollini on 04/07/17.
 */
public class PlayerTurn {

    private static final Logger LOG = Logger.getLogger(PlayerTurn.class.getName());

    Timer timer;
    AbstractPlayer p;
    Stanza room;

    public PlayerTurn(AbstractPlayer p, Stanza room) throws NetworkException {
        this.p = p;
        this.room = room;
        start();

    }

    private void start() throws NetworkException {
        try {
            p.notifyTurnStarted();
        } catch (RemoteException e) {
            LOG.log(Level.SEVERE, "Cannot reach the server", e);
            throw new NetworkException("Error, cannot reach the server");
        }
        startTimer();
    }

    private void startTimer(){
        System.out.println("se in 10 secondi non farai niente verrai disconnesso");
        timer = new Timer();
        timer.schedule(new DisconnectPlayer(), 10*1000L);
    }

    private class DisconnectPlayer extends TimerTask{

        @Override
        public void run() {
            room.playerDisconnected(p);

        }
    }

    public void playerMadeAMove(){
        System.out.println("riparte il countdown del turno");
        timer.cancel();
        timer.purge();
        startTimer();
    }

    public void endTurn(){
        timer.cancel();
        timer.purge();
    }

    public AbstractPlayer getPlayer() {
        return p;
    }

}
