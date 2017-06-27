package it.polimi.ingsw.GameModelServer;




import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.Stanza;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class Game {

    private Player[] players;
    private Board board;
    private List<PersonalBoard> personalboard;
    private int turn;
    private List<BonusTile> bonustiles;
    private GameStatus stato;


    public Game(HashMap<String, AbstractPlayer> abplayers, Stanza room) {
        this.players = creatingPlayers(abplayers,room.nPlayers());
        turn = 1;
        bonustiles = new ArrayList<>();
        this.stato = stato;
        this.board = Board.getInstance(room.nPlayers());
    }

    //setting initial game



    public Player[] creatingPlayers(HashMap<String,AbstractPlayer> abplayers,int nplayers){
        Player[] players = new Player[nplayers];
        List<String> previouscolors = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,AbstractPlayer> entry : abplayers.entrySet()){
            players[i] = new Player(entry.getKey(),randomcolor(previouscolors),board);
            i++;
        }
        return players;
    }


    public String randomcolor(List<String> previouscolor){

        String color;
        String[] colors = {"blue","green","yellow","red"};
        int index = new Random().nextInt(colors.length);
        color = colors[index];
        if(previouscolor.size() == 0) {
            previouscolor.add(color);
            return color;
        }else {
            for (String usedcolor : previouscolor) {
                if (usedcolor.equals(color)) {
                    return randomcolor(previouscolor);
                }
            }
            previouscolor.add(color);
        }
        return color;

    }



    public void fillGreenTower(List<TerritoryCard> cards){

    }
    /*public int getnumbersofPlayers(){
        int n=0;
        for(AbstractPlayer player : players.values()){
            n++;
        }
        return n;
    }*/

    public void fillYellowTower(List<BuildingCard> cards){
    }

    public void  fillBlueTower(List<CharacterCard> cards){}

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

    //Parsing palacefavor Bonus
    public static List<Risorsa> palaceFavorparsing(){
        JsonObject jpalace;

        Risorsa coin = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa woodst = new Risorsa();
        Risorsa mp = new Risorsa();
        Risorsa fp = new Risorsa();

        List<Risorsa> favors = new ArrayList<>();

        try{
            File file = new File("C:/Users/Simone/Desktop/palacefavorList.json");
            FileReader reader = new FileReader(file.getAbsolutePath());
            jpalace = Json.parse(reader).asObject();

                coin.setTipo("Coins");
                coin.setQuantity(jpalace.getInt("Coins",0));
                favors.add(0, (Risorsa) coin.clone());
                woodst.setTipo("WoodStone");
                woodst.setQuantity(jpalace.getInt("WoodStone",0));
                favors.add(1, (Risorsa) woodst.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jpalace.getInt("Servants",0));
                favors.add(2, (Risorsa) servant.clone());
                mp.setTipo("MilitaryPoints");
                mp.setQuantity(jpalace.getInt("MilitaryPoints",0));
                favors.add(3, (Risorsa) mp.clone());
                fp.setTipo("FaithPoints");
                fp.setQuantity(jpalace.getInt("FaithPoints",0));
                favors.add(4, (Risorsa) fp.clone());


        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return favors;
    }


    //parsing developement cards
    public static List<TerritoryCard> territoryParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        TerritoryCard singlecard = new TerritoryCard();
        List<TerritoryCard> cardList = new ArrayList<>();


        try {
            File fileterritory = new File("C:/Users/Simone/Desktop/territory.json");
            FileReader readingterritory = new FileReader(fileterritory.getAbsolutePath());

            jarraycard = Json.parse(readingterritory).asArray();

            for (i = 0; i < jarraycard.size(); i++) {

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

                //IMMEDIATE EFFECTS
                jarrayimm = jcard.get("immediateeffect").asArray();

                for(j=0; j<jarrayimm.size();j++){
                    effectimm.add(j,jarrayimm.get(j).asInt());
                }
                singlecard.setImmediateEffect(effectimm);

                //PERMANENT EFFECT CHOICE
                singlecard.setPermchoice(jcard.getBoolean("permchoice",false));

                //PERMANENT EFFECTS
                jarrayper = jcard.get("permanenteffect").asArray();
                for(k=0; k<jarrayper.size();k++){
                    effectper.add(k,jarrayper.get(k).asInt());
                }
                singlecard.setPermanentEffect(effectper);

                //CREATING TERRITORY CARD OBJECT
                cardList.add(i, (TerritoryCard) singlecard.clone());

            }


        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    public static List<BuildingCard> buildingParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        BuildingCard singlecard = new BuildingCard();
        List<BuildingCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();


        try {
            File filebuilding = new File("C:/Users/Simone/Desktop/building.json");
            FileReader readingbuilding = new FileReader(filebuilding.getAbsolutePath());

            jarraycard = Json.parse(readingbuilding).asArray();

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
                singlecard.setCost1(resourceslist);

                //IMMEDIATE EFFECTS
                jarrayimm = jcard.get("immediateeffect").asArray();

                for(j=0; j<jarrayimm.size();j++){
                    effectimm.add(j,jarrayimm.get(j).asInt());
                }
                singlecard.setImmediateEffect(effectimm);

                //PERMANENT EFFECT CHOICE
                singlecard.setPermchoice(jcard.getBoolean("permchoice",false));

                //PERMANENT EFFECTS
                jarrayper = jcard.get("permanenteffect").asArray();
                for(k=0; k<jarrayper.size();k++){
                    effectper.add(k,jarrayper.get(k).asInt());
                }
                singlecard.setPermanentEffect(effectper);

                //CREATING BUILDING CARD OBJECT
                cardList.add(i, (BuildingCard) singlecard.clone());

            }


        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    public static List<CharacterCard> characterParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        CharacterCard singlecard = new CharacterCard();
        List<CharacterCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();



        try {
            File filecharacter = new File("C:/Users/Simone/Desktop/character.json");
            FileReader readingcharacter = new FileReader(filecharacter.getAbsolutePath());

            jarraycard = Json.parse(readingcharacter).asArray();

            for (i = 0; i < jarraycard.size(); i++) {

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

                //COST
                coin.setTipo("Coins");
                coin.setQuantity(jcard.get("Coins").asInt());

                singlecard.setCost1(coin);

                //IMMEDIATE EFFECTS
                jarrayimm = jcard.get("immediateeffect").asArray();

                for(j=0; j<jarrayimm.size();j++){
                    effectimm.add(j,jarrayimm.get(j).asInt());
                }
                singlecard.setImmediateEffect(effectimm);

                //PERMANENT EFFECT CHOICE
                singlecard.setPermchoice(jcard.getBoolean("permchoice",false));

                //PERMANENT EFFECTS
                jarrayper = jcard.get("permanenteffect").asArray();
                for(k=0; k<jarrayper.size();k++){
                    effectper.add(k,jarrayper.get(k).asInt());
                }
                singlecard.setPermanentEffect(effectper);

                //CREATING CHARACTER CARD OBJECT
                cardList.add(i, (CharacterCard) singlecard.clone());


            }


        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    public static List<VentureCard> ventureParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        VentureCard singlecard = new VentureCard();
        List<VentureCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa MPnecessary = new Risorsa();
        Risorsa MPtospend = new Risorsa();


        try {
            File fileventure = new File("C:/Users/Simone/Desktop/venture.json");
            FileReader readingventure = new FileReader(fileventure.getAbsolutePath());

            jarraycard = Json.parse(readingventure).asArray();

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

                //PERMANENT EFFECT CHOICE
                singlecard.setPermchoice(jcard.getBoolean("permchoice",false));

                //PERMANENT EFFECTS
                jarrayper = jcard.get("permanenteffect").asArray();
                for(k=0; k<jarrayper.size();k++){
                    effectper.add(k,jarrayper.get(k).asInt());
                }
                singlecard.setPermanentEffect(effectper);

                //CREATING VENTURE CARD OBJECT
                cardList.add(i, (VentureCard) singlecard.clone());

            }


        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    //parsing 'getfreeaction' effects
   public List<GetFreeAction> freeactionparse(){
        int i;
        List <GetFreeAction> freeActionlist = new ArrayList<>();
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
               freeActionlist.add(i, (GetFreeAction) singleeffect.clone());


           }
       }catch(IOException e){
           e.printStackTrace(); // TODO
       }
       return freeActionlist;
   }

    //parsing 'getdiscount' effects
    public List<GetDiscountClass> discountparse(){

        int i;
       List<GetDiscountClass> discountlist = new ArrayList<>();
       GetDiscountClass singlediscount = new GetDiscountClass();
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

               singlediscount.setTypecard(jdiscount.get("type").asString());

               discountlist.add(i, (GetDiscountClass) singlediscount.clone());

           }
       }catch(IOException e){
           e.printStackTrace(); // TODO
       }


       return discountlist;
    }

    //'getResourcesSelling' effects parsing
    public List<GetResourcesSelling> sellingparse(){
        int i;
        GetResourcesSelling singleselling = new GetResourcesSelling();
        List<GetResourcesSelling> sellinglist = new ArrayList<>();
        JsonArray arrayselling;
        JsonObject jselling;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File sellingfile = new File("C:/Users/Simone/Desktop/effetti/getexresIfandSell.json");
            FileReader reader = new FileReader(sellingfile.getAbsolutePath());
            arrayselling = Json.parse(reader).asArray();

            for(i=0; i<arrayselling.size();i++) {
                List<Risorsa> selling = new ArrayList<>();
                List<Risorsa> reward = new ArrayList<>();
                jselling = arrayselling.get(i).asObject();
                singleselling.setId(jselling.get("id").asInt());
                singleselling.setDicepower(jselling.get("dado").asInt());

                //tospend
                coin.setTipo("Coinsto");
                coin.setQuantity(jselling.getInt("Coinsto", 0));
                selling.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woodsto");
                wood.setQuantity(jselling.getInt("Woodsto", 0));
                selling.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stonesto");
                stone.setQuantity(jselling.getInt("Stonesto", 0));
                selling.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servantsto");
                servant.setQuantity(jselling.getInt("Servantsto", 0));
                selling.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsto");
                military.setQuantity(jselling.getInt("MilitaryPointsto", 0));
                selling.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsto");
                faith.setQuantity(jselling.getInt("FaithPointsto", 0));
                selling.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsto");
                victory.setQuantity(jselling.getInt("VictoryPointsto", 0));
                selling.add(6, (Risorsa) victory.clone());
                singleselling.setTospend(selling);

                //reward
                coin.setTipo("Coinsre");
                coin.setQuantity(jselling.getInt("Coinsre", 0));
                reward.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woodsre");
                wood.setQuantity(jselling.getInt("Woodsre", 0));
                reward.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stonesre");
                stone.setQuantity(jselling.getInt("Stonesre", 0));
                reward.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servantsre");
                servant.setQuantity(jselling.getInt("Servantsre", 0));
                reward.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsre");
                military.setQuantity(jselling.getInt("MilitaryPointsre", 0));
                reward.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsre");
                faith.setQuantity(jselling.getInt("FaithPointsre", 0));
                reward.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsre");
                victory.setQuantity(jselling.getInt("VictoryPointsre", 0));
                reward.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavorre");
                favor.setQuantity(jselling.getInt("PalaceFavorre", 0));
                reward.add(7,(Risorsa) favor.clone());
                singleselling.setReward(reward);

                sellinglist.add(i, (GetResourcesSelling) singleselling.clone());
            }
        }catch(IOException e) {
            e.printStackTrace(); //TODO
        }
        return sellinglist;
    }

    //'getResourcesIf' effect parsing
    public List<GetResourcesIf> ifparsing(){

        int i;
        List<GetResourcesIf> iflist = new ArrayList<>();
        GetResourcesIf singleif = new GetResourcesIf();
        JsonArray arrayif;
        JsonObject jif;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File fileif = new File("C:/Users/Simone/Desktop/effetti/getexresif.json");
            FileReader ifread = new FileReader(fileif.getAbsolutePath());

            arrayif = Json.parse(ifread).asArray();

            for (i=0; i<arrayif.size();i++){
                List<Risorsa> ifresources = new ArrayList<>();
                jif= arrayif.get(i).asObject();
                singleif.setId(jif.get("id").asInt());
                singleif.setDicepower(jif.get("dado").asInt());

                //Resources
                coin.setTipo("Coins");
                coin.setQuantity(jif.getInt("Coins", 0));
                ifresources.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jif.getInt("Woods", 0));
                ifresources.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jif.getInt("Stones", 0));
                ifresources.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jif.getInt("Servants", 0));
                ifresources.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPoints");
                military.setQuantity(jif.getInt("MilitaryPoints", 0));
                ifresources.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPoints");
                faith.setQuantity(jif.getInt("FaithPoints", 0));
                ifresources.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPoints");
                victory.setQuantity(jif.getInt("VictoryPoints", 0));
                ifresources.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavor");
                favor.setQuantity(jif.getInt("PalaceFavor", 0));
                ifresources.add(7, (Risorsa) favor.clone());

                singleif.setResources(ifresources);
                iflist.add(i, (GetResourcesIf) singleif.clone());

            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return iflist;
    }

    //'getReources' effects parsing
    public List<GetResources> getResourcesparsing(){
        int i;
        GetResources res = new GetResources();
        List<GetResources> reslist = new ArrayList<>();
        JsonArray arrayres;
        JsonObject jres;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File resfile = new File("C:/Users/Simone/Desktop/effetti/getexres.json");
            FileReader readfile = new FileReader(resfile.getAbsolutePath());

            arrayres = Json.parse(readfile).asArray();
            for(i=0; i<arrayres.size();i++){
                List<Risorsa> resources= new ArrayList<>();
                jres = arrayres.get(i).asObject();
                res.setId(jres.get("id").asInt());

                //RESOURCES
                coin.setTipo("Coins");
                coin.setQuantity(jres.getInt("Coins", 0));
                resources.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jres.getInt("Woods", 0));
                resources.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jres.getInt("Stones", 0));
                resources.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jres.getInt("Servants", 0));
                resources.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPoints");
                military.setQuantity(jres.getInt("MilitaryPoints", 0));
                resources.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPoints");
                faith.setQuantity(jres.getInt("FaithPoints", 0));
                resources.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPoints");
                victory.setQuantity(jres.getInt("VictoryPoints", 0));
                resources.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavor");
                favor.setQuantity(jres.getInt("PalaceFavor", 0));
                resources.add(7, (Risorsa) favor.clone());

                res.setResources(resources);
                reslist.add(i, (GetResources) res.clone());
            }


        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
         return reslist;
    }

    //'GetBoostdice' effects parsing
    public List<GetBoostDice> getboostparsing(){
        int i;
        GetBoostDice boost = new GetBoostDice();
        List<GetBoostDice> boostlist = new ArrayList<>();
        JsonArray boostarray;
        JsonObject jboost;


        try{
            File fileboost = new File("C:/Users/Simone/Desktop/effetti/getboostdiceonAction.json");
            FileReader readboost = new FileReader(fileboost.getAbsolutePath());
            boostarray = Json.parse(readboost).asArray();

            for(i=0; i<boostarray.size();i++){
                jboost = boostarray.get(i).asObject();
                boost.setId(jboost.get("id").asInt());
                boost.setEffecttype(jboost.get("type").asString());
                boost.setDiceboost(jboost.get("dice").asInt());
                boostlist.add(i, (GetBoostDice) boost.clone());

            }


        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return boostlist;
    }

    //'GetForEach' effect parsing
    public List<GetForEach> forEachparsing(){

        int i,j;
        GetForEach effect = new GetForEach();
        List<GetForEach> effectlist = new ArrayList<>();
        JsonArray arrayeffect;
        JsonObject jeffect;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();
        Risorsa character = new Risorsa();
        Risorsa venture = new Risorsa();
        Risorsa territory = new Risorsa();
        Risorsa building = new Risorsa();

        try {
            File fileeffect = new File("C:/Users/Simone/Desktop/effetti/getForEach.json");
            FileReader readfile = new FileReader(fileeffect.getAbsolutePath());

            arrayeffect = Json.parse(readfile).asArray();
            for(i=0; i<arrayeffect.size();i++) {
                List<Risorsa> foreach = new ArrayList<>();
                List<Risorsa> getres = new ArrayList<>();
                jeffect = arrayeffect.get(i).asObject();
                effect.setId(jeffect.get("id").asInt());
                effect.setDice(jeffect.get("dado").asInt());

                //FOREACH
                coin.setTipo("CoinsFor");
                coin.setQuantity(jeffect.getInt("CoinsFor", 0));
                foreach.add(0, (Risorsa) coin.clone());
                wood.setTipo("WoodsFor");
                wood.setQuantity(jeffect.getInt("WoodsFor", 0));
                foreach.add(1, (Risorsa) wood.clone());
                stone.setTipo("StonesFor");
                stone.setQuantity(jeffect.getInt("StonesFor", 0));
                foreach.add(2, (Risorsa) stone.clone());
                servant.setTipo("ServantsFor");
                servant.setQuantity(jeffect.getInt("ServantsFor", 0));
                foreach.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsFor");
                military.setQuantity(jeffect.getInt("MilitaryPointsFor", 0));
                foreach.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsFor");
                faith.setQuantity(jeffect.getInt("FaithPointsFor", 0));
                foreach.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsFor");
                victory.setQuantity(jeffect.getInt("VictoryPointsFor", 0));
                foreach.add(6, (Risorsa) victory.clone());
                character.setTipo("CharacterCardsFor");
                character.setQuantity(jeffect.getInt("CharacterCardsFor", 0));
                foreach.add(7, (Risorsa) character.clone());
                venture.setTipo("VentureCardsFor");
                venture.setQuantity(jeffect.getInt("VentureCardsFor", 0));
                foreach.add(8, (Risorsa) venture.clone());
                territory.setTipo("TerritoryCardsFor");
                territory.setQuantity(jeffect.getInt("TerritoryCardsFor", 0));
                foreach.add(9, (Risorsa) territory.clone());
                building.setTipo("BuildingCardsFor");
                building.setQuantity(jeffect.getInt("BuildingCardsFor", 0));
                foreach.add(10, (Risorsa) building.clone());
                effect.setResourcesfor(foreach);

                //GETRESOURCES
                coin.setTipo("CoinsGet");
                coin.setQuantity(jeffect.getInt("CoinsGet", 0));
                getres.add(0, (Risorsa) coin.clone());
                wood.setTipo("WoodsGet");
                wood.setQuantity(jeffect.getInt("WoodsGet", 0));
                getres.add(1, (Risorsa) wood.clone());
                stone.setTipo("StonesGet");
                stone.setQuantity(jeffect.getInt("StonesGet", 0));
                getres.add(2, (Risorsa) stone.clone());
                servant.setTipo("ServantsGet");
                servant.setQuantity(jeffect.getInt("ServantsGet", 0));
                getres.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsGet");
                military.setQuantity(jeffect.getInt("MilitaryPointsGet", 0));
                getres.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsGet");
                faith.setQuantity(jeffect.getInt("FaithPointsGet", 0));
                getres.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsGet");
                victory.setQuantity(jeffect.getInt("VictoryPointsGet", 0));
                getres.add(6, (Risorsa) victory.clone());
                character.setTipo("CharacterCardsGet");
                character.setQuantity(jeffect.getInt("CharacterCardsGet", 0));
                getres.add(7, (Risorsa) character.clone());
                venture.setTipo("VentureCardsGet");
                venture.setQuantity(jeffect.getInt("VentureCardsGet", 0));
                getres.add(8, (Risorsa) venture.clone());
                territory.setTipo("TerritoryCardsGet");
                territory.setQuantity(jeffect.getInt("TerritoryCardsGet", 0));
                getres.add(9, (Risorsa) territory.clone());
                building.setTipo("BuildingCardsGet");
                building.setQuantity(jeffect.getInt("BuildingCardsGet", 0));
                getres.add(10, (Risorsa) building.clone());
                favor.setTipo("PalaceFavorGet");
                favor.setQuantity(jeffect.getInt("PalaceFavorGet", 0));
                getres.add(11, (Risorsa) favor.clone());
                effect.setResourcesget(getres);

                effectlist.add(i, (GetForEach) effect.clone());

            }


        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return effectlist;
    }

    //'GetEndGameVP' effects parsing
    public List<GetVPEnd> getVPparsing() {

        int i;
        GetVPEnd endeffect = new GetVPEnd();
        List<GetVPEnd> listendeffect = new ArrayList<>();
        JsonArray arrayendeffect;
        JsonObject jendeffect;

        try{
            File endfile = new File("C:/Users/Simone/Desktop/effetti/getVPendgame.json");
            FileReader readend = new FileReader(endfile.getAbsolutePath());

            arrayendeffect = Json.parse(readend).asArray();
            for(i=0; i<arrayendeffect.size();i++) {
                jendeffect = arrayendeffect.get(i).asObject();
                endeffect.setId(jendeffect.get("id").asInt());
                endeffect.setVP(jendeffect.get("VP").asInt());
                listendeffect.add(i, (GetVPEnd) endeffect.clone());

            }

        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return listendeffect;
    }

    //EXCOMMUNICATION PARSING
    public List<ExcommunicationReduction> reductionParsing(){

        int i;
        ExcommunicationReduction effect = new ExcommunicationReduction();
        List<ExcommunicationReduction> listeffect = new ArrayList<>();
        JsonArray arrayreduction;
        JsonObject jreduction;

        try{
            File reductionfile = new File("C:/Users/Simone/Desktop/effetti/scomunicaboost.json");
            FileReader readreduction = new FileReader(reductionfile.getAbsolutePath());

            arrayreduction = Json.parse(readreduction).asArray();
            for(i=0; i<arrayreduction.size();i++){
                jreduction = arrayreduction.get(i).asObject();
                effect.setPeriod(jreduction.get("period").asInt());
                effect.setId(jreduction.get("id").asInt());
                effect.setType(jreduction.get("type").asString());
                effect.setDice(jreduction.get("dice").asInt());
                listeffect.add(i, (ExcommunicationReduction) effect.clone());

                System.out.println(listeffect.get(i).getDice());
                System.out.println(listeffect.get(i).getId());
                System.out.println(listeffect.get(i).getPeriod());
                System.out.println(listeffect.get(i).getType());
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return listeffect;
    }

    public ExcommunicationCoverMarket coverMarketParsing(){
        ExcommunicationCoverMarket covering = new ExcommunicationCoverMarket();
        JsonObject jcovering;

        try{
            File file = new File("C:/Users/Simone/Desktop/effetti/scomunicaCoverMarket.json");
            FileReader readfile = new FileReader(file.getAbsolutePath());
            jcovering = Json.parse(readfile).asObject();
            covering.setPeriod(jcovering.get("period").asInt());
            covering.setId(jcovering.get("id").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return covering;
    }

    public ExcommunicationServants twoServantsParsing(){
        ExcommunicationServants twoserv = new ExcommunicationServants();
        JsonObject jservant;

        try{
            File fileserv = new File("C:/Users/Simone/Desktop/effetti/scomunicaTwoServants.json");
            FileReader readserv = new FileReader(fileserv.getAbsolutePath());
            jservant = Json.parse(readserv).asObject();
            twoserv.setId(jservant.get("id").asInt());
            twoserv.setPeriod(jservant.get("period").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return twoserv;
    }

    public ExcommunicationSkipAction skipActionParsing(){
        ExcommunicationSkipAction skip = new ExcommunicationSkipAction();
        JsonObject jskip;

        try{
            File fileskip = new File("C:/Users/Simone/Desktop/effetti/scomunicaSkipfirstAction.json");
            FileReader readskip = new FileReader(fileskip.getAbsolutePath());
            jskip = Json.parse(readskip).asObject();
            skip.setId(jskip.get("id").asInt());
            skip.setPeriod(jskip.get("period").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return skip;
    }

    public List<ExcommunicationLessResources> lessResourcesParsing(){
        ExcommunicationLessResources effectless = new ExcommunicationLessResources();
        List<ExcommunicationLessResources> listeffectless = new ArrayList<>();
        JsonArray arrayless;
        JsonObject jless;
        int i;

        try{
            File fileless = new File("C:/Users/Simone/Desktop/effetti/scomunicalessresources.json");
            FileReader readless = new FileReader(fileless.getAbsolutePath());

            arrayless = Json.parse(readless).asArray();
            for(i=0; i<arrayless.size();i++){
                jless = arrayless.get(i).asObject();
                effectless.setId(jless.get("id").asInt());
                effectless.setPeriod(jless.get("period").asInt());
                effectless.setType(jless.get("type").asString());
                listeffectless.add(i, (ExcommunicationLessResources) effectless.clone());
            }

        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        return listeffectless;
    }

    public List<ExcommunicationEndVP> notVPparsing(){
        ExcommunicationEndVP endvp = new ExcommunicationEndVP();
        List<ExcommunicationEndVP> listendvp = new ArrayList<>();
        int i;
        JsonObject jendvp;
        JsonArray arrayendvp;

        try{
            File filend = new File("C:/Users/Simone/Desktop/effetti/scomunicanoendVP.json");
            FileReader readend = new FileReader(filend.getAbsolutePath());

            arrayendvp = Json.parse(readend).asArray();
            for(i=0; i<arrayendvp.size();i++){
                jendvp = arrayendvp.get(i).asObject();
                endvp.setId(jendvp.get("id").asInt());
                endvp.setPeriod(jendvp.get("period").asInt());
                endvp.setType(jendvp.get("type").asString());

                listendvp.add(i, (ExcommunicationEndVP) endvp.clone());
            }

        }catch (IOException e){
            e.printStackTrace();//TODO
        }

        return listendvp;
    }

    public List<ExcommunicationLostVP> lostVPparsing(){
        ExcommunicationLostVP lostvp = new ExcommunicationLostVP();
        List<ExcommunicationLostVP> listlostvp = new ArrayList<>();
        JsonArray arraylost;
        JsonObject jlost;
        int i;
         try{
             File filelost = new File("C:/Users/Simone/Desktop/effetti/scomunicaendVPlost.json");
             FileReader readlost = new FileReader(filelost.getAbsolutePath());
             arraylost = Json.parse(readlost).asArray();
             for(i=0;i<arraylost.size(); i++){
                 jlost = arraylost.get(i).asObject();
                 lostvp.setId(jlost.get("id").asInt());
                 lostvp.setPeriod(jlost.get("period").asInt());
                 lostvp.setQuantity(jlost.get("quantity").asInt());
                 lostvp.setType(jlost.get("type").asString());
                 listlostvp.add(i, (ExcommunicationLostVP) lostvp.clone());
             }

         }catch(IOException e){
             e.printStackTrace();//TODO
         }


        return listlostvp;
    }

    public void addFMonPalace(Player player, String color){
       if(player.getMember(color).getValue()>=1) {
           int i;
           CellAction space;
           Risorsa reward = new Risorsa();
           space = board.createPalace();

           space.setFamilyMemberinCell(color);
           for(i=0;i<space.getBonus().size();i++){
               reward.setTipo(space.getBonus().get(i).gettipo());
               reward.setQuantity(space.getBonus().get(i).getquantity());
           }



       }else System.out.println("non puoi fare l'azione"); //TODO

    }
    //TODO da provare il metodo sopra
    public void getimmediateBonus(Player player,List<Risorsa> reward, Board board){ //si passa la lista di risorse da prendere
        int newvalue;
        int i;
        Token[] token = player.getToken();
        Risorsa single = new Risorsa();
        List<Risorsa> listfavor;
        for(Risorsa resource : reward) {
            if (resource.gettipo().equals("PalaceFavor") && resource.getquantity()!=0) {
                listfavor = choosePalaceFavor(palaceFavorparsing(),resource.getquantity()); //da sostituire il metodo con la listagià parsata dal game
                getimmediateBonus(player,listfavor,board);
            }else if(resource.getquantity()!=0){
                single.setTipo(resource.gettipo());
                single.setQuantity(resource.getquantity());

                if(single.gettipo().equals("VictoryPoints")) { //fare le modifiche sulla Board
                    for(i=0;i<3;i++) {
                        if (token[i].getType().equals("Victory")) {
                            newvalue = token[i].getPosition() + single.getquantity();
                            token[i].setPosition(newvalue);
                        }
                    }
                }else if(single.gettipo().equals("FaithPoints")){
                    for(i=0;i<3;i++){
                        if(token[i].getType().equals("Faith")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            token[i].setPosition(newvalue);
                        }
                    }

                }else if (single.gettipo().equals("MilitaryPoints")){
                    for(i=0;i<3;i++){
                        if(token[i].getType().equals("Military")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            token[i].setPosition(newvalue);
                        }
                    }
                }else {

                    for (Risorsa res : player.getPB().getresources()) {
                        if (res.gettipo().equals(single.gettipo())) {
                            newvalue = res.getquantity() + single.getquantity();
                            res.setQuantity(newvalue);
                        }
                    }
                }

            }
        }

    }

    public List<Risorsa> choosePalaceFavor(List<Risorsa> palaceBonus, int n) { //questa list<risorsa> è la lista parsata dei possibili bonus palazzo
        List<Risorsa> rewards = new ArrayList<>();
        Risorsa res = new Risorsa();
        String choice;
        int i;

        for(i=0;i<n;i++) {
            //TODO richiedere al client che tipo di risorsa vuole
            System.out.println("Choose your palace favor(type the name):\n" + "2 Coins\n" + "1+1 WoodStone\n" + "2 Servants\n" + "2 MilitaryPoints\n" + "1 FaithPoints\n");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();
            while(!(choice.equals("Coins") || choice.equals("WoodStone") || choice.equals("Servants") || choice.equals("MilitaryPoints") || choice.equals("FaithPoints"))){
                System.out.println("errore, inserire il corretto dato");
                System.out.println("Choose your palace favor(type the name):\n" + "2 Coins\n" + "1+1 WoodStone\n" + "2 Servants\n" + "2 MilitaryPoints\n" + "1 FaithPoints\n");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
            }

            if (i != 0) {
                for (Risorsa previouschoice : rewards) {
                    while ((choice.equals("WoodStone") && previouschoice.gettipo().equals("Woods")) || choice.equals(previouschoice.gettipo())) {
                        //TODO fai un'altra scelta da mandare al client
                        System.out.println("You've already chosen this type of favor, type another type");
                        Scanner sc = new Scanner(System.in);
                        choice = sc.nextLine();
                    }
                }
            } else{
                for (Risorsa singlereward : palaceBonus) {
                    if (choice.equals(singlereward.gettipo()) && choice.equals("WoodStone")) {
                        res.setTipo("Woods");
                        res.setQuantity(1);
                        rewards.add((Risorsa) res.clone());
                        res.setTipo("Stones");
                        res.setQuantity(1);
                        rewards.add((Risorsa) res.clone());

                    } else if (choice.equals(singlereward.gettipo())) {
                        rewards.add(singlereward);

                    }

                }
            }

        }


        return rewards;
    }

    //GETTERS
    public Board getBoard(){
        return board;
    }

    public GameStatus getstatus(){
        return stato;
    }

    public int getturn(){
        return turn;
    }

    public Player[] getPlayers(){
        return players;
    }

    public List<BonusTile> getbonustiles(){
        return bonustiles;
    }

    public List<PersonalBoard> getPersonalBoard(){
        return personalboard;
    }


}