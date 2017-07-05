package it.polimi.ingsw.GameModelServer;



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
    private Effect effects;
    private List<LeaderCard> carteLeader;
    private BonusTile tesseraBonus;
    private Token[] token;



    public Player(String username, String color, Board board/*,  BonusTile tesseraBonus*/) {
        this.username = username;
        members = createFamilyMember(color);
        this.color = color;
        personalBoard = new PersonalBoard();
        effects = new Effect();
        carteLeader = new ArrayList<LeaderCard>();
        this.tesseraBonus = tesseraBonus;
        this.board = board;
        this.board.addObserver(this);
    }

    public FamilyMember getMember(String color) {//questo color deve essere passato come scanner del client
        int i = 0;
        for(FamilyMember member : members) {
            if (member.getColor() == color)
                return members.get(i);
            else
            i++;
        }
        return null; //TODO familiare già utilizzato
    }

    private List<FamilyMember> createFamilyMember(String color){
        FamilyMember black = new FamilyMember("Black",color);
        FamilyMember orange = new FamilyMember("Orange",color);
        FamilyMember white = new FamilyMember("White",color);
        FamilyMember neutral = new FamilyMember("Neutral",color);
        List<FamilyMember> fms = new ArrayList<>();
        fms.add(black);
        fms.add(orange);
        fms.add(white);
        fms.add(neutral);

        return fms;
    }

    public Token[] getToken() {
        return token;
    }

    public Effect getEffects() {
        return effects;
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

    protected List<LeaderCard> getcarteLeader(){
        return carteLeader;
    }


    //TODO appena i punti militari arrivano a X sblocchi la cella nella PB
    public void unlockGreenCell(int puntiMilitari, PersonalBoard pboard){
        int i;
        for(i=0;i<pboard.getterritories().size();i++){
            if (pboard.getterritories().get(i).getMpNecessary() <= puntiMilitari)
            pboard.getterritories().get(i).setUnlockedcell(true);
        }

    }

    @Override
    public void update() {
        this.token = board.getTokens(this.color);
    }
// public void doAction() {}
    // TODO implement here



    public FamilyMember spendservants(FamilyMember member ,int servants){
        int oldvalue;
        int oldservants;
        oldservants = this.getPB().getsingleresource("Servants").getquantity();
        if(oldservants>servants){
            oldvalue = member.getValue();
            member.setValue(oldvalue + servants);
            this.getPB().getsingleresource("Servants").setQuantity(oldservants - servants);
        }else
            System.out.println("non hai abbastanza servants "); //TODO
        return member;

    }

}