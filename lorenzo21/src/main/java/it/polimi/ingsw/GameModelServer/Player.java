package it.polimi.ingsw.GameModelServer;

import it.polimi.ingsw.ClientModel.Cell;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Player extends BoardObserver implements Serializable{

    private String username;
    private List<FamilyMember> members;
    private String color;
    private PersonalBoard personalBoard;
    private List<Effect> effects;
    private int puntiVittoria;
    private int puntiMilitari;
    private int puntiFede;
    private List<CardFactory> carteLeader;
    private BonusTile tesseraBonus;
    private Token[] token;

    //private int genericResources;
    //private int buildingcost;

    public Player(String username, String color,Board board/*,  BonusTile tesseraBonus*/) {
        this.username = username;
        members = new ArrayList<>();
        this.color = color;
        personalBoard = new PersonalBoard();
        effects = new ArrayList<Effect>();
        puntiFede = 0;
        puntiMilitari = 0;
        puntiVittoria = 0;
        carteLeader = new ArrayList<CardFactory>();
        this.tesseraBonus = tesseraBonus;
        token = inizializationToken(color);
        this.board = board;
        this.board.addObserver(this);
    }

    public Token[] inizializationToken(String color) {
        Token[] token = new Token[4];
        token[0] = new Token(color);
        token[0].setType("Victory");
        token[0].setPosition(0);
        token[1]=new Token(color);
        token[1].setType("Military");
        token[1].setPosition(0);
        token[2] = new Token(color);
        token[2].setType("Faith");
        token[2].setPosition(0);
        token[3] = new Token(color);
        token[3].setType("Order");

        return token;
    }

    public FamilyMember getMember(String color) {
        int i = 0;
        for(FamilyMember member : members) {
            if (member.getColor() == color)
                return members.get(i);
            else
            i++;
        }
        return null; //TODO familiare già utilizzato
    }


    public Token[] getToken() {
        return token;
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

    //TODO appena i punti militari arrivano a X sblocchi la cella nella PB
    public static void unlockGreenCell(int puntiMilitari, PersonalBoard pboard){
        int i;
        for(i=0;i<pboard.getterritories().size();i++){
            if (pboard.getterritories().get(i).getMpNecessary() <= puntiMilitari)
            pboard.getterritories().get(i).setUnlockedcell(true);
        }

    }

    @Override
    public void update() {

    }
// public void doAction() {}
    // TODO implement here


}