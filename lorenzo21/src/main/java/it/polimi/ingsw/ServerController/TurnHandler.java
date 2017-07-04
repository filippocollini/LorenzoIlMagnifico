package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.util.Stack;

/**
 * Created by filippocollini on 10/06/17.
 */
public class TurnHandler implements Runnable{

    private Game game;
    private Rules rules;
    private Stack<AbstractPlayer> stack;
    private Stanza room;

    public TurnHandler(Stack<AbstractPlayer> stack, Stanza room){
        this.stack = stack;
        this.room = room;
    }


    @Override
    public void run() {
        

    }

    public void modifyTurnOrder(){
        room.setStack();//TODO
    }
}
