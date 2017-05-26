package it.polimi.ingsw.ClientModel;

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
        observerCollection= new ArrayList<Observer>();
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
    public List<Observer> observerCollection;


    /**
     * 
     */
    public void notifyObserver() {
        for(Observer observer : observerCollection){
            observer.notify();
        }
    }

    /**
     * 
     */
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    /**
     * 
     */
    public void unregisterObserver(Observer observer) {
        observerCollection.remove(observerCollection.equals(observer));
    }

}