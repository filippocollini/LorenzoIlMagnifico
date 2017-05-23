package it.polimi.ingsw.ClientModel;

import java.util.*;

/**
 * 
 */
public class Player {

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String color;

    /**
     * 
     */
    private PersonalBoard personalboard;

    /**
     * 
     */
    private Effect effettipermanenti;

    /**
     * 
     */
    private int victoryPoints;

    /**
     * 
     */
    private int MilitaryPoints;

    /**
     * 
     */
    private int faithPoints;

    /**
     * 
     */
    private List<CardFactory> LeaderCards;

    /**
     * 
     */
    private BonusTile bonustile;

    /**
     * 
     */
    private ListRisorse resources;

    /**
     * 
     */
    private List<FamilyMember> familiari;

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
    public void registerObeserver() {
        // TODO implement here
    }

    /**
     * 
     */
    public void unregisterObserver() {
        // TODO implement here
    }

    /**
     * @return
     */
    public void doAction() {
        // TODO implement here
    }

}