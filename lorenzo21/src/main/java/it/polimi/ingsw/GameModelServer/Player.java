package it.polimi.ingsw.GameModelServer;



import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private List<LeaderCard> leadcards;

    private Token[] token;
    private BonusTile bonustile;


    public Player(String username, String color, Board board) {
        this.username = username;
        members = createFamilyMember(color);
        this.color = color;
        personalBoard = new PersonalBoard();
        effects = new Effect();
        leadcards = new ArrayList<LeaderCard>();

        this.board = board;
        this.board.addObserver(this);
        bonustile = new BonusTile(); //da attribuire in seguito alla scelta
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

    public BonusTile getBonustile(){
        return bonustile;
    }


    protected List<LeaderCard> getcarteLeader(){
        return leadcards;
    }



    public void unlockGreenCell(int puntiMilitari){
        int i;
        for(i=0;i<personalBoard.getterritories().size();i++){
            if (personalBoard.getterritories().get(i).getMpNecessary() <= puntiMilitari) {
                personalBoard.getterritories().get(i).setUnlockedcell(true);
            }
        }

    }

    @Override
    public void update() {
        this.token = board.getTokens(this.color);
        for(Token single : this.token){
            if(single.getType().equalsIgnoreCase("MilitaryPoints")){
                unlockGreenCell(single.getPosition());
            }
        }

        int i = 0;
        int coeff = 0;
        for(LeaderCard card : leadcards){
            if(card.getClass().getSimpleName().equalsIgnoreCase("LucreziaBorgia") && card.isActive()){
                coeff = 2;
            }
        }

        for(FamilyMember member : members){
            for(Dices dice : board.getDices()){
                if(member.getColor().equalsIgnoreCase(dice.getColor())){
                    this.members.get(i).setValue(dice.getValue()+coeff); //modifico il dado dalla board

                    for(LeaderCard card : leadcards){ //se ho ludovico il moro setto a 5
                        if(card.getClass().getSimpleName().equalsIgnoreCase("LudovicoilMoro") && card.isActive()){
                            this.members.get(i).setValue(5+coeff);
                        }
                    }

                    for(EffectStrategy excomm : effects.getStrategy()){ //se ho scomunica riduco di uno
                        if(excomm.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationReduction")){
                            Method method;
                            try {
                                method = excomm.getClass().getMethod("apply", FamilyMember.class, String.class);
                                FamilyMember helper = (FamilyMember) method.invoke(excomm,this.members.get(i),"dices");
                                this.members.get(i).setValue(helper.getValue());
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }
                    }

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

    public StringBuilder showPlayergoods(){
        StringBuilder showgoods = new StringBuilder();

        //FAMILY MEMBER
        for(FamilyMember member : members){
            if(!member.isFmUsed()){
                showgoods.append(member.getColor());
                showgoods.append(" member has this power ");
                showgoods.append(member.getValue());
                showgoods.append("\n");
            }else{
                showgoods.append("You've already used ");
                showgoods.append(member.getColor());
                showgoods.append(" member ");
                showgoods.append("\n");
            }
        }
        showgoods.append("\n");
        //PERSONALBOARD
        showgoods.append(personalBoard.showPB());
        showgoods.append("\n");

        //EFFECTS
        if(effects.getStrategy().size()!= 0){
            showgoods.append("These are the ids of your activated effects : ");
            showgoods.append("\n");
            for(EffectStrategy effect : effects.getStrategy()){
                showgoods.append(effect.getId());
                showgoods.append("\n");
            }
        }else
            showgoods.append("You don't have permanent effect active");
        showgoods.append("\n");

        //LEADER CARDS

        //BONUSTILE
        showgoods.append(bonustile.showBonusTile());
        showgoods.append("\n");

        return showgoods;
    }

}