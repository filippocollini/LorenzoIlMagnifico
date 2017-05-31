package it.polimi.ingsw.ServerController;

import it.polimi.ingsw.GameModelServer.Game;

import java.util.*;

/**
 * send request to the server
 */
public interface IServer {

    Game createRoom(AbstractPlayer player);

    void joinRoom(Stanza room, AbstractPlayer player);

    void loginPlayer(AbstractPlayer player);



}