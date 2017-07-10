package it.polimi.ingsw.GameModelServer;



import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class Player extends BoardObserver implements Serializable{

    private static final Logger LOG = Logger.getLogger(Player.class.getName());


    private String username;
    private transient List<FamilyMember> members;
    private String color;
    private transient PersonalBoard personalBoard;
    private transient Effect effects;
    private transient List<LeaderCard> leadcards;

    private transient Token[] token;
    private BonusTile bonustile;


    public Player(String username, String color, Board board) {
        this.username = username;
        members = createFamilyMember(color);
        this.color = color;
        personalBoard = new PersonalBoard();
        effects = new Effect();
        leadcards = new ArrayList<LeaderCard>();
        token = settingtokens();
        this.board = board;
        this.board.addObserver(this);
        bonustile = new BonusTile(); //da attribuire in seguito alla scelta
    }

    private Token[] settingtokens(){
        Token[] tokens = new Token[3];

        tokens[0] = new Token(this.color);
        tokens[0].setType("VictoryPoints");
        tokens[0].setPosition(0);
        tokens[1] = new Token(this.color);
        tokens[1].setType("MilitaryPoints");
        tokens[1].setPosition(0);
        tokens[2] = new Token(this.color);
        tokens[2].setType("FaithPoints");
        tokens[2].setPosition(0);

        return tokens;
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


    public List<LeaderCard> getcarteLeader(){
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
        int l = 0;

        for(Token token : this.token) {
            for (Token btoken : board.getTokens(this.color)) {
                int k = 0;
                this.token[l] = board.getTokens(this.color)[k];

                k++;
            }
            l++;
        }

        /*int n = 0;
        for(Token single : this.token){
            if(this.token[n]
                    .getType()
                    .equalsIgnoreCase("MilitaryPoints")){
                unlockGreenCell(this.token[n].getPosition());
            }
            n++;
        }
*/
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
                                LOG.log(Level.SEVERE, "Cannot parse the file", e);
                            }

                        }
                    }

                }
            }
            i++;
        }
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
        showgoods.append("Leader Cards : ");
        showgoods.append("\n");
        for(LeaderCard card : leadcards){
            showgoods.append(card.getName());
            showgoods.append("\n");
            showgoods.append("Requires : ");
            for(Risorsa req : card.getRequires()) {
                showgoods.append(req.getquantity());
                showgoods.append(" ");
                showgoods.append(req.gettipo());
            }
            if(card.isActive()){
                showgoods.append("Activated ");
            }
            if(card.isOnceinarow()){
                showgoods.append(" Once in a row ability actived\n");
            }
        }
        showgoods.append("\n");

        //BONUSTILE
        showgoods.append(bonustile.showBonusTile());
        showgoods.append("\n");

        return showgoods;
    }




}