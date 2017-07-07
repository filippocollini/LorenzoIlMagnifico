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
    private BonusTile bonustile;


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
        bonustile = new BonusTile();
    }

    public FamilyMember getMember(String color) {
        int i = 0;
        for(FamilyMember member : members) {
            if (member.getColor().equalsIgnoreCase(color))
                return members.get(i);
            else
            i++;
        }
        return null; //TODO familiare già utilizzato
    }

    public void setBonustile(BonusTile bonustile) {
        this.bonustile = bonustile;
    }

    public List<FamilyMember> getMembers() {
        return members;
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
        System.out.println("ciao");
        int i = 0;
        for(FamilyMember member : members){
            for(Dices dice : board.getDices()){
                if(member.getColor().equalsIgnoreCase(dice.getColor())){
                    this.members.get(i).setValue(dice.getValue());
                    System.out.println("n: "+members.get(i).getValue());
                }
            }
            i++;
        }
    }




    public FamilyMember spendservants(FamilyMember member ,int servants){ //servant==punti da aggiungere al dado
        int oldvalue;
        int oldservants;
        int coeff = 1;

        for(EffectStrategy effect : this.effects.getStrategy()){
            if(effect.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationServants")){
                coeff = 2;
            }
        }

        oldservants = this.getPB().getsingleresource("Servants").getquantity();
        if(oldservants>(coeff*servants)){
            oldvalue = member.getValue();
            member.setValue(oldvalue + (servants));
            this.getPB().getsingleresource("Servants").setQuantity(oldservants - (coeff*servants));
        }else
            System.out.println("non hai abbastanza servants "); //TODO
        return member;

    }

}