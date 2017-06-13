package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class PersonalBoard {

    private List<CardFactory> territories;
    private List<CardFactory> buildings;
    private List<CardFactory> characters;
    private List<CardFactory> ventures;
    private BonusTile tesseraBonus;
    private ListRisorse risorse;


    public PersonalBoard() {
        territories = new ArrayList<CardFactory>();
        buildings = new ArrayList<CardFactory>();
        characters = new ArrayList<CardFactory>();
        ventures = new ArrayList<CardFactory>();
        this.tesseraBonus = tesseraBonus;
        risorse = new ListRisorse();
    }

    public void set() {
        // TODO implement here
    }


    public List<CardFactory> getterritories() {
        return territories;
    }

    public List<CardFactory> getbuildings(){
        return buildings;
    }

    public List<CardFactory> getventures(){
        return ventures;
    }

    public List<CardFactory> getcharacters(){
        return characters;
    }

    public BonusTile gettesserabonus(){
        return tesseraBonus;
    }

    public ListRisorse getresources(){
        return risorse;
    }



}