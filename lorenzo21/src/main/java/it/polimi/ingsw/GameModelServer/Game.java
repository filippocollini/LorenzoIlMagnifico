package it.polimi.ingsw.GameModelServer;




import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.ServerController.AbstractPlayer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Game {

    private HashMap<String, AbstractPlayer> players;
    private Board board;
    private List<PersonalBoard> personalboard;
    private int turn;
    private List<BonusTile> bonustiles;
    private GameStatus stato;


    public Game(HashMap<String, AbstractPlayer> players) {
        this.players = players;
        turn = 1;
        bonustiles = new ArrayList<BonusTile>();
        this.stato = stato;
    }

    //setting initial game

    public void setBoard() {
        this.board = Board.getInstance();
    }

    /* public void setPersonalboard(List<PersonalBoard> personalboard, HashMap<String,AbstractPlayer> players){
        int i;
        for (i = 0; i<players.size(); i++){
            personalboard.add(i, new PersonalBoard());
            players.setPB(personalboard.get(i));
        }

    }
        */


    public void setLeaderCard(){}

    public void setDevelopementCard(Board board){}

    public void setFirstTurn(HashMap<String , AbstractPlayer> players){
        List turno = new ArrayList();
        turno.add(players.values());
        Collections.shuffle(turno);
    }

    public void setBonustiles(List<Player> players){}

    public void setResources(List<Player> players){

    }


    //parsing developement cards
    public List<DevelopementCard> developementParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new DevelopementCard();
        List<DevelopementCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa MPnecessary = new Risorsa();
        Risorsa MPtospend = new Risorsa();


        try {
            File file = new File("C:/Users/Simone/Desktop/card.json");
            FileReader reading = new FileReader(file.getAbsolutePath());

            jarraycard = Json.parse(reading).asArray();

            for (i = 0; i < jarraycard.size(); i++) {
                List<Risorsa> resourceslist = new ArrayList<>();
                List<Integer> effectper = new ArrayList<>();
                List<Integer> effectimm = new ArrayList<>();

                jcard = jarraycard.get(i).asObject();
                //NAME
                singlecard.setname(jcard.get("nome").asString());

                //NUMBER
                singlecard.setNumber(jcard.get("number").asInt());

                //CARDTYPE
                singlecard.setCardtype(jcard.get("type").asString());

                //PERIOD
                singlecard.setPeriod(jcard.get("period").asInt());

                //COST CHOICE
                singlecard.setChoice(jcard.getBoolean("choice", false));

                //COST
                coin.setTipo("Coins");
                coin.setQuantity(jcard.get("Coins").asInt());
                resourceslist.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jcard.get("Woods").asInt());
                resourceslist.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jcard.get("Stones").asInt());
                resourceslist.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jcard.get("Servants").asInt());
                resourceslist.add(3, (Risorsa) servant.clone());
                MPnecessary.setTipo("MPnecessary");
                MPnecessary.setQuantity(jcard.get("MPnecessary").asInt());
                resourceslist.add(4, (Risorsa) MPnecessary.clone());
                MPtospend.setTipo("MPtospend");
                MPtospend.setQuantity(jcard.get("MPtospend").asInt());
                resourceslist.add(5, (Risorsa) MPtospend.clone());
                singlecard.setCost1(resourceslist);

                //IMMEDIATE EFFECTS
                jarrayimm = jcard.get("immediateeffect").asArray();

                for(j=0; j<jarrayimm.size();j++){
                    effectimm.add(j,jarrayimm.get(j).asInt());
                }
                singlecard.setImmediateEffect(effectimm);

                //PERMANENT EFFECTS
                jarrayper = jcard.get("permanenteffect").asArray();
                for(k=0; k<jarrayper.size();k++){
                    effectper.add(k,jarrayper.get(k).asInt());
                }
                singlecard.setPermanentEffect(effectper);

                //CREATING DEVELOP CARD OBJECT
                cardList.add(i, (DevelopementCard) singlecard.clone());

            }


        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    //parsing 'getfreeaction' effects
   public List<GetFreeAction> freeactionparse(){
        int i;
        List <GetFreeAction> effectlist = new ArrayList<>();
        GetFreeAction singleeffect = new GetFreeAction();
       JsonArray arrayfree;
       JsonObject jfree;
       try {
           File filefree = new File("C:/Users/Simone/Desktop/effetti/getfreeAction.json");
           FileReader readfree = new FileReader(filefree.getAbsolutePath());

           arrayfree = Json.parse(readfree).asArray();
           for (i = 0; i < arrayfree.size(); i++) {
               jfree = arrayfree.get(i).asObject();
               singleeffect.setId(jfree.get("id").asInt());
               singleeffect.setDicepower(jfree.get("dice").asInt());
               singleeffect.setType(jfree.get("type").asString());
               effectlist.add(i, (GetFreeAction) singleeffect.clone());


       }
       }catch(IOException e){
           e.printStackTrace(); // TODO
       }
       return effectlist;
    }

    //parsing 'getdiscount' effects
    public List<GetDiscount> discountparse(){

        int i;
       List<GetDiscount> discountlist = new ArrayList<>();
       GetDiscount singlediscount = new GetDiscount();
       JsonArray arraydiscount;
       JsonObject jdiscount;
       Risorsa coin = new Risorsa();
       Risorsa wood = new Risorsa();
       Risorsa stone = new Risorsa();
       Risorsa servant = new Risorsa();


       try {
           File discfile = new File("C:/Users/Simone/Desktop/effetti/getDiscount.json");
           FileReader read = new FileReader(discfile.getAbsolutePath());

           arraydiscount = Json.parse(read).asArray();
           for(i=0; i<arraydiscount.size();i++){
               List<Risorsa> discounts = new ArrayList<>();
               jdiscount = arraydiscount.get(i).asObject();
               singlediscount.setId(jdiscount.get("id").asInt());
               singlediscount.setSelect(jdiscount.getBoolean("select", false));

               //RESOURCES
               coin.setTipo("Coins");
               coin.setQuantity(jdiscount.getInt("Coins",0));
               discounts.add(0, (Risorsa) coin.clone());
               wood.setTipo("Woods");
               wood.setQuantity(jdiscount.getInt("Woods",0));
               discounts.add(1, (Risorsa) wood.clone());
               stone.setTipo("Stones");
               stone.setQuantity(jdiscount.getInt("Stones", 0));
               discounts.add(2, (Risorsa) stone.clone());
               servant.setTipo("Servants");
               servant.setQuantity(jdiscount.getInt("Servants",0));
               discounts.add(3, (Risorsa) servant.clone());

               singlediscount.setDiscount(discounts);

               discountlist.add(i,singlediscount);

           }
       }catch(IOException e){
           e.printStackTrace(); // TODO
       }


       return discountlist;
        }



    public Board getBoard(){
        return board;
    }

    public GameStatus getstatus(){
        return stato;
    }

    public int getturn(){
        return turn;
    }

    public HashMap<String, AbstractPlayer> getPlayers(){
        return players;
    }

    public List<BonusTile> getbonustiles(){
        return bonustiles;
    }

    public List<PersonalBoard> getPersonalBoard(){
        return personalboard;
    }
}