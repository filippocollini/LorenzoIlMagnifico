package it.polimi.ingsw.ClientModel;

import it.polimi.ingsw.*;

import java.util.*;
import java.util.Observer;

/**
 * 
 */
public class Board {

    /**
     * Default constructor
     */
    public Board() {
    }

    /**
     * 
     */
    public List VictoryPoints;

    /**
     * 
     */
    public List FaithPoints;

    /**
     * 
     */
    public List MilitaryPoints;

    /**
     * 
     */
    public List Order;

    /**
     * 
     */
    public List produzione;

    /**
     * 
     */
    public List raccolto;

    /**
     * 
     */
    public List Market;

    /**
     * 
     */
    public List CouncilPalace;

    /**
     * 
     */
    public List<ExcommunicationTiles> ExcommunicationTiles;

    /**
     * 
     */
    public List<Integer> Dices;

    /**
     * 
     */
    public Tower territoriesTower;

    /**
     * 
     */
    public Tower buildingsTower;

    /**
     * 
     */
    public Tower charactersTower;

    /**
     * 
     */
    public Tower venturesTower;

    /**
     * 
     */
    public Observer ObserverCollection;


    /**
     * 
     */
    public void notifyObserver() {
        // TODO implement here
    }

    /**
     * 
     */
    public void registerObserver() {
        // TODO implement here
    }

    /**
     * 
     */
    public void unregisterObserver() {
        // TODO implement here
    }

}