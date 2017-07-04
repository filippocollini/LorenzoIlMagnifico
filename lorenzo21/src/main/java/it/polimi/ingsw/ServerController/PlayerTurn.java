package it.polimi.ingsw.ServerController;

import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by filippocollini on 04/07/17.
 */
public class PlayerTurn {

    Timer timer;
    AbstractPlayer p;
    Stanza room;

    public PlayerTurn(AbstractPlayer p, Stanza room){
        this.p = p;
        this.room = room;
        start();

    }

    private void start(){
        try {
            p.notifyTurnStarted();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        startTimer();
    }

    private void startTimer(){
        timer = new Timer();
        timer.schedule(new DisconnectPlayer(), 20*1000L);
    }

    private class DisconnectPlayer extends TimerTask{

        @Override
        public void run() {
            room.playerDisconnected(p);

        }
    }

    public void playerMadeAMove(){
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
