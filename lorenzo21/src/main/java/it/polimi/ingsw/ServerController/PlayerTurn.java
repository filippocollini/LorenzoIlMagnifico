package it.polimi.ingsw.ServerController;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.Exceptions.NetworkException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the timing of the turn
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
        System.out.println("You have 20 seconds to make an action");
        timer = new Timer();
        timer.schedule(new DisconnectPlayer(), timerActionParsing()*1000L);
    }

    public int timerActionParsing(){
        JsonObject jtimer;
        int timer = 0;


        try {
            File file = new File("lorenzo21/src/main/resources/timeraction.json");
            FileReader read = new FileReader(file.getAbsolutePath());

            jtimer = Json.parse(read).asObject();
            timer = jtimer.getInt("timer",30);

        } catch (java.io.IOException e) {
            LOG.log(Level.SEVERE, "Cannot reach the client", e);
        }


        return timer;
    }

    private class DisconnectPlayer extends TimerTask{

        @Override
        public void run() {
            room.playerDisconnected(p);

        }
    }

    public void playerMadeAMove(){
        System.out.println("Countdown restarted");
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
