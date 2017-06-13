package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Player {

    private String username;
    private List<FamilyMember> familiari;
    private String color;
    private PersonalBoard personalBoard;
    private List<Effect> effetti;
    private int puntiVittoria;
    private int puntiMilitari;
    private int puntiFede;
    private List<CardFactory> carteLeader;
    private BonusTile tesseraBonus;

    public Player(String username, String color, PersonalBoard personalBoard, BonusTile tesseraBonus) {
        this.username = username;
        familiari = new ArrayList<FamilyMember>();
        this.color = color;
        this.personalBoard = personalBoard;
        effetti = new ArrayList<Effect>();
        puntiFede = 0;
        puntiMilitari = 0;
        puntiVittoria = 0;
        carteLeader = new ArrayList<CardFactory>();
        this.tesseraBonus = tesseraBonus;
    }


    public String getUsername(){
        return username;
    }

    public String getColor(){
        return color;
    }

    public PersonalBoard getPB(){
        return personalBoard;
    }

    public BonusTile tessaraBonus(){
        return tesseraBonus;
    }

    public int getVP(){
        return puntiVittoria;
    }

    public int getMP(){
        return puntiMilitari;
    }

    public int getFP(){
        return puntiFede;
    }

    public
    List<CardFactory> getcarteLeader(){
        return carteLeader;
    }

    public PersonalBoard setPB(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
        return this.personalBoard;
    }


    // public void doAction() {}
    // TODO implement here


}