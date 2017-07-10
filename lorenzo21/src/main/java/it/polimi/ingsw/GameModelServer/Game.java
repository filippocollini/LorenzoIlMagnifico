package it.polimi.ingsw.GameModelServer;




import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.Exceptions.FileMalformedException;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.Stanza;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class Game implements Serializable {

    private static final Logger LOG = Logger.getLogger(Game.class.getName());


    private static Player[] players;
    private static Board board;
    public static final String SUCCESS="success";
    public static final String FAIL="fail";
    public static final String EXCOMM="excomm";
    public static final String SAMETOWER="sametower";
    public static final String CHOOSEANOTHERFM="another fm";
    public static final String FMPRESENT="fm already present";
    public static final String NOTENOUGHRESOURCES="not enough resources";
    public static int PALACE;
    private List<BonusTile> bonustilesnormal;
    private transient List<BonusTile> bonustileleader;
    private transient List<TerritoryDeck> greendeck;
    private transient List<BuildingDeck> yellowdeck;
    private transient List<CharacterDeck> bluedeck;
    private transient List<VentureDeck> violetdeck;
    private static List<Risorsa> palaceFavors;
    private transient List<ExcommunicationDeck> excommunicationdeck;
    private List<Player> order;
    private transient List<LeaderCard> leaderdeck;
    private int productionChoices = -1;



    public Game(HashMap<String, AbstractPlayer> abplayers, Stanza room) throws FileMalformedException {
        this.board = Board.getInstance(room.nPlayers());

        this.players = creatingPlayers(abplayers,room.nPlayers());
        bonustilesnormal = new ArrayList<>();
        bonustileleader = new ArrayList<>();
        greendeck = creatingGreenDeck(territoryParsing());
        yellowdeck = creatingYellowDeck(buildingParsing());
        bluedeck = creatingBlueDeck(characterParsing());
        violetdeck = creatingVioletDeck(ventureParsing());
        palaceFavors = palaceFavorparsing();
        excommunicationdeck = creatingExcommunicationDeck(creatingExcommunicationTiles());
        order = setOrderFirstTurn();
        leaderdeck = creatingLeaderDeck();
        settinginitialResources();
        fillExcommunicationTiles();

    }

    //setting initial game

    private Player[] creatingPlayers(HashMap<String,AbstractPlayer> abplayers,int nplayers){
        Player[] players = new Player[nplayers];
        List<String> previouscolors = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,AbstractPlayer> entry : abplayers.entrySet()){
            String color = randomcolor(previouscolors);
            previouscolors.add(color);
            players[i] = new Player(entry.getKey(),color,board);
            i++;
        }
        return players;
    }

    public List<Dices> rollDices(List<Dices> dices){
        int i = 0;
        for(Dices dice : dices){
            Random random = new Random();
            dices.get(i).setValue(random.nextInt(6)+1);
            System.out.println(dices.get(i).getValue());
            i++;
        }

        return dices;
    }

    public void settinginitialResources(){
        int i = 0;

        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa coin = new Risorsa();
        for(Player player : players){
            wood.setTipo("Woods");
            wood.setQuantity(2);
            players[i].getPB().getresources().add((Risorsa) wood.clone());
            stone.setTipo("Stones");
            stone.setQuantity(2);
            players[i].getPB().getresources().add((Risorsa) stone.clone());
            servant.setTipo("Servants");
            servant.setQuantity(3);
            players[i].getPB().getresources().add((Risorsa) servant.clone());
            int index = 0;
            for(Player ord : order){

                if(ord.getUsername().equalsIgnoreCase(player.getUsername())){
                    coin.setTipo("Coins");
                    coin.setQuantity(5+index);
                    players[i].getPB().getresources().add((Risorsa) coin.clone());

                }
                index++;
            }
            i++;
        }
    }

    public static String randomcolor(List<String> previouscolor){

        String color;
        String[] colors = {"blue","green","yellow","red"};
        int index = new Random().nextInt(colors.length);
        color = colors[index];
        if(previouscolor.size() == 0) {

            return color;
        }else {
            for (String usedcolor : previouscolor) {
                if (usedcolor.equals(color)) {
                    return randomcolor(previouscolor);
                }
            }

        }
        return color;

    }

    public void fillTowers(int turn){
        fillGreenTower(turn);
        fillBlueTower(turn);
        fillYellowTower(turn);
        fillVioletTower(turn);
    }

    private void fillGreenTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("territory").getFloors().iterator();
        System.out.println(board.getTower("territory").getFloors().size());
        while(iter.hasNext()){
            CellTower cell = (CellTower) iter.next();
            cell.setCarta(greendeck.get(turn-1).drawfirstCard());
            board.getTower("territory").getFloors().get(i).setCarta(cell.getCard());
            i++;
        }
    }

    private void fillYellowTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("buildings").getFloors().iterator();
        while(iter.hasNext()){
            CellTower cell = (CellTower) iter.next();
            cell.setCarta(yellowdeck.get(turn-1).
                    drawfirstCard());
            board.getTower("buildings").getFloors().get(i).setCarta(cell.getCard());
            i++;
        }
    }

    private void fillBlueTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("characters").getFloors().iterator();
        while(iter.hasNext()){
            CellTower cell = (CellTower) iter.next();
            cell.setCarta(bluedeck.get(turn-1).
                    drawfirstCard());
            board.getTower("characters").getFloors().get(i).setCarta(cell.getCard());
            i++;
        }
    }

    private void fillVioletTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("ventures").getFloors().iterator();
        while(iter.hasNext()){
            CellTower cell = (CellTower) iter.next();
            cell.setCarta(violetdeck.get(turn-1).
                    drawfirstCard());
            board.getTower("ventures").getFloors().get(i).setCarta(cell.getCard());
            i++;
        }
    }

    private void fillExcommunicationTiles(){
        int i;
        for(i=0; i<3;i++){
            board.getExcommTiles().add(i,excommunicationdeck.get(i).drawfirstCard());
        }

    }

    public void setLeaderCard(){}

    public void resetBoard(){
      for(CellAction cell : board.getCouncilpalace()){  //SVUOTA PALAZZO
          board.getCouncilpalace().remove(cell);
      }
      int i = 0;
      for(CellAction cell : board.getMarket()){  //SVUOTA MARKET
          board.getMarket().get(i).unsetfMisOn();
          i++;
      }
      i = 0;
        int k = 0;
      for(Player p : players){ //SETTA A FALSE I FAMILIARI USATI E A 0 QUELLO NEUTRO
          for(FamilyMember member : p.getMembers()){
              players[i].getMembers().get(k).setFmUsed(false);
              if("Neutral".equalsIgnoreCase(member.getColor())){
                  players[i].getMember("Neutral").setValue(0);
                  for(LeaderCard card : p.getcarteLeader()){
                      if("SigismondoMalatesta".equalsIgnoreCase(card.getClass().getSimpleName())){
                          FamilyMember helper;
                          Method method;
                          try {
                              method = card.getClass().getMethod("boostmember", FamilyMember.class);
                              helper = (FamilyMember) method.invoke(card,member);
                              players[i].getMember("Neutral").setValue(helper.getValue());
                          } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                              e.printStackTrace();
                          }
                      }
                  }
              }
              k++;
          }
      }

      for(CellAction cell : board.getProduction()) {
          board.getProduction().remove(cell);
      }

      for(CellAction cell : board.getHarvest()){
            board.getHarvest().remove(cell);
      }
      //SVUOTA TORRI dai familiari
        int l = 0;
        int n = 0;
        for(Tower single : board.getTowers()){
          for(CellTower cell : single.getFloors()){
                board.getTowers().get(n).getFloors().get(l).setFmOnIt(null);
              board.getTowers().get(n).getFloors().get(l).setfMIsPresent(false);
              l++;
          }
          n++;
        }

    }


    public List<Player> setOrderFirstTurn(){
        List<Player> turn = new ArrayList<>();
        turn.addAll(Arrays.asList(players));


        Collections.shuffle(turn);


        return turn;
    }

    public void setBonustiles(List<Player> players){}

    public  List<Player> reOrder(List<Player> old,Board board){
        List<Player> neworder = new ArrayList<>();


        boolean present = false;
        int i = 0;
        if(board
                .getCouncilpalace().size()==0){
            return old;
        }
        for(CellAction cell
                : board.getCouncilpalace()){

            for(Player mem : neworder){
                if(mem!=null) {
                    if (board.getCouncilpalace()
                            .get(i)
                            .getMember()
                            .getColorplayer()
                            .equalsIgnoreCase(mem
                                    .getColor()))
                        present = true;
                }

            }
            if(!present) {
                for(Player p : players){
                    if(p.getColor().equalsIgnoreCase(cell.getMember().getColorplayer()))
                        neworder.add(p);
                }
            }

            i++;
        }


        if(neworder.size() == 0){
            return old;
        }

       for(Player older : old) { //setto il nuovo ordine con tutti i giocatori
           boolean pre = false;

           for (Player pla : neworder) {
               if(pla!=null) {
                   if (pla.getColor().equalsIgnoreCase(older.getColor())) {
                       pre = true;
                   }
               }
           }
           if(!pre){
               neworder.add(older);
           }
       }
        return neworder;

    }

    //Parsing palacefavor Bonus
    private List<Risorsa> palaceFavorparsing() throws FileMalformedException {
        JsonObject jpalace;

        Risorsa coin = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa woodst = new Risorsa();
        Risorsa mp = new Risorsa();
        Risorsa fp = new Risorsa();

        List<Risorsa> favors = new ArrayList<>();

        try{
            File file = new File("lorenzo21/src/main/resources/palacefavorList.json");
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
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");        }

        return favors;
    }


    //parsing developement cards
    private List<TerritoryCard> territoryParsing() throws FileMalformedException {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = null;
        try {
            singlecard = new TerritoryCard();
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse territory cards' file", e);

        }
        List<TerritoryCard> cardList = new ArrayList<>();


        try {
            File fileterritory = new File("lorenzo21/src/main/resources/cards/territory.json");
            FileReader readingterritory = new FileReader(fileterritory.getAbsolutePath());

            jarraycard = Json.parse(readingterritory).asArray();

            for (i = 0; i < jarraycard.size(); i++) {

                List<Integer> effectper = new ArrayList<>();
                List<Integer> effectimm = new ArrayList<>();

                jcard = jarraycard.get(i).asObject();
                //NAME
                try{
                    singlecard.setname(jcard.get("nome").asString());
                }catch(NullPointerException e){
                    LOG.log(Level.SEVERE, "Error with the file", e);
                }

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
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");        }
        return cardList;
    }

    private List<BuildingCard> buildingParsing() throws FileMalformedException {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = null;
        try {
            singlecard = new BuildingCard();
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse building cards' file", e);
        }
        List<BuildingCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();


        try {
            File filebuilding = new File("lorenzo21/src/main/resources/cards/building.json");
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
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");        }
        return cardList;
    }

    private List<CharacterCard> characterParsing() throws FileMalformedException {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new CharacterCard();
        List<CharacterCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        List<Risorsa> cost = new ArrayList<>();



        try {
            File filecharacter = new File("lorenzo21/src/main/resources/cards/character.json");
            FileReader readingcharacter = new FileReader(filecharacter.getAbsolutePath());

            jarraycard = Json.parse(readingcharacter).asArray();

            for (i = 0; i < jarraycard.size(); i++) {

                List<Integer> effectper = new ArrayList<>();
                List<Integer> effectimm = new ArrayList<>();

                jcard = jarraycard.get(i).asObject();
                //NAME
                try{
                    singlecard.setname(jcard.get("nome").asString());
                }catch (NullPointerException e){
                    LOG.log(Level.SEVERE, "Cannot parse the file", e);
                }



                //NUMBER
                singlecard.setNumber(jcard.get("number").asInt());

                //CARDTYPE
                singlecard.setCardtype(jcard.get("type").asString());

                //PERIOD
                singlecard.setPeriod(jcard.get("period").asInt());

                //COST
                coin.setTipo("Coins");
                coin.setQuantity(jcard.get("Coins").asInt());
                cost.add((Risorsa) coin.clone());

                singlecard.setCost1(cost);

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
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");        }
        return cardList;
    }

    private List<VentureCard> ventureParsing() throws FileMalformedException {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = null;
        try {
            singlecard = new VentureCard();
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse venture cards' file", e);
        }
        List<VentureCard> cardList = new ArrayList<>();
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa MPnecessary = new Risorsa();
        Risorsa MPtospend = new Risorsa();


        try {
            File fileventure = new File("lorenzo21/src/main/resources/cards/venture.json");
            FileReader readingventure = new FileReader(fileventure.getAbsolutePath());

            jarraycard = Json.parse(readingventure).asArray();

            for (i = 0; i < jarraycard.size(); i++) {
                List<Risorsa> resourceslist = new ArrayList<>();
                List<Integer> effectper = new ArrayList<>();
                List<Integer> effectimm = new ArrayList<>();

                jcard = jarraycard.get(i).asObject();
                //NAME
                try{
                    singlecard.setname(jcard.get("nome").asString());
                }catch(NullPointerException e){
                    LOG.log(Level.SEVERE, "Cannot parse the file", e);
                }


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
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");        }
        return cardList;
    }

    private List<ExcommunicationTiles> creatingExcommunicationTiles(){
        List<ExcommunicationTiles> tilesList = new ArrayList<>();
        ExcommunicationTiles tile = null;
        try {
            tile = new ExcommunicationTiles();
        } catch (FileMalformedException e) {
            LOG.log(Level.CONFIG, "Cannot parse the excommunication tiles' file", e);
        }
        int i;

        for(i=0; i<21;i++) {
            try {
                if(i<7) {
                    tile.setId(11 + i);
                    tile.setPeriod(1);
                    tilesList.add(i, (ExcommunicationTiles) tile.clone());
                }else if (i < 14) {
                    tile.setId(14 + i);
                    tile.setPeriod(2);
                    tilesList.add(i, (ExcommunicationTiles) tile.clone());
                } else {
                    tile.setId(17 + i);
                    tile.setPeriod(3);
                    tilesList.add(i, (ExcommunicationTiles) tile.clone());
                }
            }catch(NullPointerException e){
                LOG.log(Level.SEVERE, "Cannot parse the file", e);
            }
        }


        return tilesList;
    }

    private List<TerritoryDeck> creatingGreenDeck(List<TerritoryCard> greencards){
        List<TerritoryDeck> decks = new ArrayList<>();

        TerritoryDeck deck1 = new TerritoryDeck(greencards,1);
        TerritoryDeck deck2 = new TerritoryDeck(greencards,2);
        TerritoryDeck deck3 = new TerritoryDeck(greencards,3);
        decks.add(0,deck1);
        decks.add(1,deck2);
        decks.add(2,deck3);
        return decks;
    }

    private List<BuildingDeck> creatingYellowDeck(List<BuildingCard> yellowcards){
        List<BuildingDeck> decks = new ArrayList<>();

        BuildingDeck deck1 = new BuildingDeck(yellowcards,1);
        BuildingDeck deck2 = new BuildingDeck(yellowcards,2);
        BuildingDeck deck3 = new BuildingDeck(yellowcards,3);
        decks.add(0,deck1);
        decks.add(1,deck2);
        decks.add(2,deck3);
        return decks;
    }

    private List<CharacterDeck> creatingBlueDeck(List<CharacterCard> bluecards){
        List<CharacterDeck> decks = new ArrayList<>();

        CharacterDeck deck1 = new CharacterDeck(bluecards,1);
        CharacterDeck deck2 = new CharacterDeck(bluecards,2);
        CharacterDeck deck3 = new CharacterDeck(bluecards, 3);
        decks.add(0,deck1);
        decks.add(1,deck2);
        decks.add(2,deck3);
        return decks;
    }

    private List<ExcommunicationDeck> creatingExcommunicationDeck(List<ExcommunicationTiles> tiles){
        List<ExcommunicationDeck> decks = new ArrayList<>();

        ExcommunicationDeck deck1 = new ExcommunicationDeck(tiles,1);
        ExcommunicationDeck deck2 = new ExcommunicationDeck(tiles,2);
        ExcommunicationDeck deck3 = new ExcommunicationDeck(tiles,3);
        decks.add(0,deck1);
        decks.add(1,deck2);
        decks.add(2,deck3);

        return decks;
    }

    private List<VentureDeck> creatingVioletDeck(List<VentureCard> violetcards){
        List<VentureDeck> decks = new ArrayList<>();

        VentureDeck deck1 = new VentureDeck(violetcards,1);
        VentureDeck deck2 = new VentureDeck(violetcards,2);
        VentureDeck deck3 = new VentureDeck(violetcards,3);
        decks.add(0,deck1);
        decks.add(1,deck2);
        decks.add(2,deck3);
        return decks;
    }

    public String addFMonHarvest(Player player, String member){
        String type = "harvest";
        int oldvalue;
        oldvalue = player.getMember(member).getValue();

        for(CellAction cell : board.getHarvest()){ //controllo se ha già altri fm qui
            if(cell.getcolorMember().equals(player.getColor()) && !cell.getMember().getColor().equalsIgnoreCase("Neutral")){
                System.out.println("You've another FM here!");
                return FMPRESENT;
            }
        }


        CellAction space;
        space = board.createCellHarvestorPoduction();
        space.setType(type);
        boolean freedom = false;

        for(LeaderCard card : player.getcarteLeader()){
            if("LudovicoAriosto".equalsIgnoreCase(card.getClass().getSimpleName()) && card.isActive()){
                freedom = true;
            }
        }

        if(!freedom) {
            if (board.getHarvest().size() == 1 && players.length < 3) {
                player.getMember(member).setValue(oldvalue);
                return FAIL;
            } else if (board.getHarvest().size() >= 1) {
                space.setDice(-3);
                player.getMember(member).setValue(player.getMember(member).getValue() + space.getDice());
            }
        }

        player.getMember(member).setValue(controlboost(player,player.getMember(member),type).getValue());

        if(player.getMember(member).getValue()>1){
            for(CellPB cellPB : player.getPB().getterritories()){
                for(EffectStrategy effect : player.getEffects().getStrategy()){
                    for(int id : cellPB.getCard().getPermanenteffect()){
                        if(effect.getId() == id){
                            Method method;
                            try {
                                method = effect.getClass().getMethod("apply", Player.class,String.class);
                                player = (Player) method.invoke(effect,player,member);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                LOG.log(Level.SEVERE, "Method not found", e);
                            }

                        }
                    }
                }
            }

            //get bonus from tile
            int l = 0;
            Token[] tokens = player.board.getTokens(player.getColor());
            int n = 0;
            for(Risorsa bonus : player.getBonustile().getBonus2()){
                for(Risorsa res : player.getPB().getresources()){
                    if(bonus.gettipo().equalsIgnoreCase(res.gettipo())){
                        player.getPB().getresources().get(l).setQuantity(res.getquantity()+bonus.getquantity());
                    }
                    l++;
                }

                for(Token token : tokens){
                    if(token.getType().equalsIgnoreCase(bonus.gettipo())){
                        tokens[n].setPosition(token.getPosition()+bonus.getquantity());
                    }
                    n++;
                }
            }
            player.board.setTokens(tokens);
        }else {
            player.getMember(member).setValue(oldvalue);
            int result = 1-player.getMember(member).getValue();
            return String.valueOf(result);
        }

        space.setFamilyMemberinCell(player.getMember(member));
        board.getHarvest().add(space);

        int l = 0;
        for(Player p : players){
            if(p.getUsername().equalsIgnoreCase(player.getUsername())){
                players[l] = player;
            }
            l++;
        }

        return SUCCESS;
    }

    public String addFMonProduction(Player player, String member, List<Integer> choices){
        String type = "production";
        Player control;
        int oldvalue;
        oldvalue = player.getMember(member).getValue();
        List<Risorsa> gained = new ArrayList<>();

        for(CellAction cell : board.getProduction()){ //controllo se ha già altri fm qui
            if(cell.getcolorMember().equals(player.getColor()) && !"Neutral".equalsIgnoreCase(cell.getMember().getColor())){
                System.out.println("You've another FM here!");
                return SAMETOWER;
            }
        }



        CellAction space;
        space = board.createCellHarvestorPoduction();
        space.setType(type);
        boolean freedom = false;

        for(LeaderCard card : player.getcarteLeader()){
            if("LudovicoAriosto".equalsIgnoreCase(card.getClass().getSimpleName()) && card.isActive()){
                freedom = true;
            }
        }

        if(!freedom) {
            if (board.getProduction().size() == 1 && players.length < 3) {
                player.getMember(member).setValue(oldvalue);
                return FAIL;
            } else if (board.getProduction().size() >= 1) {
                space.setDice(-3);
                player.getMember(member).setValue(player.getMember(member).getValue() + space.getDice());
            }
        }

        player.getMember(member).setValue(controlboost(player,player.getMember(member),type).getValue());

        int choice;
        int k;
        int j = 0;
        int i = 0;
        boolean spent = false;
        String request="";
        if(player.getMember(member).getValue()>1){
            for(CellPB cellPB : player.getPB().getbuildings()){
                for(EffectStrategy effect : player.getEffects().getStrategy()){
                    choice = effect.getId();
                    if(cellPB.getCard().getPermchoice()){
                        if(choices.isEmpty()){
                            for(k=0;k<cellPB.getCard().getPermanenteffect().size();k++) {
                                request = request + cellPB.getCard().getPermanenteffect().get(k) + " ";

                            }
                            productionChoices = -1;
                            return request;
                        }else{
                            productionChoices++;
                            choice = choices.get(productionChoices);
                        }

                    }
                    for(int id : cellPB.getCard().getPermanenteffect()){

                        if(effect.getId() == id && choice == id){
                            Method method;
                            Method method2;
                            try {
                                method = effect.getClass().getMethod("apply", Player.class,String.class);
                                control = (Player) method.invoke(effect,player,member);
                                for(Risorsa controlres : control.getPB().getresources()){
                                    for(Risorsa resplay : player.getPB().getresources()){
                                        if(!(controlres.getquantity() == resplay.getquantity()))
                                            spent = true;
                                    }
                                }
                                for(Token controltoken : control.getToken()){
                                    for(Token tokenplay : player.getToken()){
                                        if(!(controltoken.getPosition() == tokenplay.getPosition()))
                                            spent = true;
                                    }
                                }
                                player = control;

                                if(spent) {
                                    method2 = effect.getClass().getMethod("apply", List.class, Player.class, String.class);
                                    gained = (List<Risorsa>) method2.invoke(effect, gained, player, member);
                                }
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                LOG.log(Level.SEVERE, "Method not found", e);
                            }

                        }
                    }
                }
            }

            for(Risorsa gain : gained){
                for(Risorsa res : player.getPB().getresources()) {
                    if (gain.gettipo().equalsIgnoreCase(res.gettipo().concat("re"))) {
                        player.getPB().getresources().get(j)
                                .setQuantity(player.getPB().getresources().get(j).getquantity() + gain.getquantity());
                    }
                    j++;
                }
                Token[] tokens = player.board.getTokens(player.getColor());
                for(Token token : tokens){
                    if(gain.gettipo().equalsIgnoreCase(token.getType().concat("re"))){
                        tokens[i].setPosition(tokens[i].getPosition() + gain.getquantity());
                    }
                    i++;
                }
                player.board.setTokens(tokens);
            }

            //get bonus dalla tile
            int l = 0;
            Token[] tokens = player.board.getTokens(player.getColor());
            int n = 0;
            for(Risorsa bonus : player.getBonustile().getBonus1()){
                for(Risorsa res : player.getPB().getresources()){
                    if(bonus.gettipo().equalsIgnoreCase(res.gettipo())){
                        player.getPB().getresources().get(l).setQuantity(res.getquantity()+bonus.getquantity());
                    }
                    l++;
                }

                for(Token token : tokens){
                    if(token.getType().equalsIgnoreCase(bonus.gettipo())){
                        tokens[n].setPosition(token.getPosition()+bonus.getquantity());
                    }
                    n++;
                }
            }
            player.board.setTokens(tokens);
        }else {
            int result = 1-player.getMember(member).getValue();
            player.getMember(member).setValue(oldvalue);
            return String.valueOf(result);
        }

        space.setFamilyMemberinCell(player.getMember(member));
        board.getHarvest().add(space);

        int l = 0;
        for(Player p : players){
            if(p.getUsername().equalsIgnoreCase(player.getUsername())){
                players[l] = player;
            }
            l++;
        }

        return SUCCESS;
    }

    public String addFMonPalace(Player player, String member, String favor){ //TODO rivedere per sicurezza

        if(player.getMember(member).getValue()>=1) {

            CellAction space;

            space = board.createCellPalace();
            int n = 0;
            for (Player p : players) {
                if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                    space.setFamilyMemberinCell(players[n].getMember(member));
                }
                n++;
            }


            board.getCouncilpalace().add(space);

            for (Risorsa resource : space.getBonus()) {
                if ("PalaceFavor".equalsIgnoreCase(resource.gettipo()) && resource.getquantity() != 0) {
                    List<Risorsa> listfavor = choosePalaceFavor(player, favor);
                    player = getimmediateBonus(player, listfavor, false);
                }
                if(!"ghost".equalsIgnoreCase(member)) {
                    player = getimmediateBonus(player, space.getBonus(), false);
                }



                int k = 0;
                for (Player p : players) {
                    if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                        players[k] = player;
                    }
                    k++;
                }

            }

        }else
            return FAIL;
       return SUCCESS;
    }

    public String addFMonMarket(Player player, String member, String request){
        int i = 0;

        if (player.getEffects().getStrategy()!=null) {
            for (EffectStrategy effect : player.getEffects().getStrategy()) {
                if ("ExcommunicationCoverMarket".equalsIgnoreCase(effect.getClass().getSimpleName())) {
                    System.out.println("YOU'VE BEEN EXCOMMUNICATED! Go away from Magnifico's Market");
                    return EXCOMM;
                }
            }
        }
        if(player.getMember(member).getValue()>1) {
            for (CellAction cell : board.getMarket()) {
                if (cell.getType().equalsIgnoreCase(request)){
                    if(cell.isfMOn()){
                        for(LeaderCard card : player.getcarteLeader()){
                            if("LudovicoAriosto".equalsIgnoreCase(card.getClass().getSimpleName()) && card.isActive()) {
                                player = getimmediateBonus(player, cell.getBonus(), false);
                            }else
                                return FAIL;
                        }
                    }else{

                        player = getimmediateBonus(player, cell.getBonus(), false);

                        board.getMarket().get(i).setFamilyMemberinCell(player.getMember(member));
                    }
                }
                i++;
            }
            int k = 0;
            for(Player p : players){
                if(p.getUsername().equalsIgnoreCase(player.getUsername())){
                    players[k] = player;
                }
                k++;
            }
            return SUCCESS;
        }else
                System.out.println("Action cannot be made");

        return FAIL;
    }

    public static FamilyMember controlboost(Player player, FamilyMember member, String type){
        if (player.getEffects().getStrategy()!=null) {
            for(EffectStrategy effect : player.getEffects().getStrategy()){
                if("GetBoostandDiscount".equalsIgnoreCase(effect.getClass().getSimpleName())
                        || "GetBoostDice".equalsIgnoreCase(effect.getClass().getSimpleName())
                        || "ExcommunicationReduction".equalsIgnoreCase(effect.getClass().getSimpleName())) {
                    Method method;
                    try {
                        method = effect.getClass().getMethod("apply",  FamilyMember.class,String.class);
                        member = (FamilyMember) method.invoke(effect, member,type);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        LOG.log(Level.SEVERE, "Method not found", e);
                    }
                }
            }
        }

        return member;
    }

    public String addFMonTowerControl(Player player, String member, String tower, int floor, boolean free){
       Tower towerchosen;
       int dice ;
       boolean threecoins = false;
       int oldvalue;

        FamilyMember ghostmember;


       towerchosen = player.board.getTower(tower);
       if(!member.equals("Neutral")) {
           for (CellTower cell : towerchosen.getFloors()) {
               if (cell.getFmOnIt()!=null){
                   if (cell.getFmOnIt().getColorplayer().equals(player.getColor()) && !(cell.getFmOnIt().getColor().equals("Neutral"))) {
                       System.out.println("You've a FM here yet! choose another action");
                       return SAMETOWER;
                   }
               }
           }
       }
       oldvalue = player.getMember(member).getValue();
       //controlla se può boostare il familymember e lo fa //il type è il tipo della torre
       player.getMember(member).setValue( controlboost(player , player.getMember(member),tower).getValue());

        dice = floor;
            //prima controllo se può comprare la carta altrimenti ritorno

        for(CellTower cell : towerchosen.getFloors()){
            if(cell.isfMPresent())
                threecoins = true;
        }
        for(LeaderCard card : player.getcarteLeader()){
            if(card.getClass().getSimpleName().equalsIgnoreCase("FilippoBrunelleschi") && card.isActive()){
                threecoins = false;
            }
        }

        if (!controlpurchase(player,towerchosen.getFloors().get(dice).getCard(),false,threecoins)) {
            player.getMember(member).setValue(oldvalue);
            System.out.println("you cannot buy the card!");
            return NOTENOUGHRESOURCES;
        }

        //poi vedo se il suo fm basta o si deve potenziare
        try{
            if (isFMok(player.getMember(member),dice,player,oldvalue)!=null){
                player.getMember(member).setValue(isFMok(player.getMember(member),dice,player,oldvalue).getValue());
            }else{
                int result = 1-player.getMember(member).getValue();
                return String.valueOf(result);
            }
        }catch(NullPointerException e){
            LOG.log(Level.SEVERE, "Cannot parse the file", e);
        }



        boolean cellfree = false;
        //controllo che se la carta è territorio il player deve avere celle disponibili
        if(tower.equalsIgnoreCase("territory")){
            for(CellPB cell : player.getPB().getterritories()){
                if((cell.getCard() == null) && cell.getUnlockedcell()){
                    cellfree = true;
                }
            }
            if(!cellfree){
                return FAIL;
            }
        }

        String control;
        //poi faccio l'azione applicando lo sconto
        if (free == true){
            ghostmember = new FamilyMember("ghost","ghost");
            control = addFMonTowerAction(player, ghostmember, floor, tower, free, threecoins);
        }else
            control = addFMonTowerAction(player, player.getMember(member),dice, tower,false,threecoins);
        if (control!=SUCCESS)
            return control;

    return SUCCESS;
   }

    public String addFMonTowerAction(Player player, FamilyMember member, int floor, String tower, boolean free,boolean threecoins){
        //do action
        int i = 0;
        boolean bonus = true;
        DevelopementCard card;

        for(CellTower cell : player.board.getTower(tower).getFloors()) {
            if(cell.getDice() == floor){
                Method method;
                //applica sconto
                card = applydiscount(player,player.board.getTower(tower).getFloors().get(floor).getCard(),free);

                //poi la compra
                player = buyCard(player,card);

                if(!player.getEffects().getStrategy().isEmpty()){
                    for(EffectStrategy effect : player.getEffects().getStrategy()){
                        if(effect.getClass().getSimpleName().equalsIgnoreCase("RemoveBonusTower")){
                            bonus = false;
                        }
                    }
                }
                if(bonus) {
                    player = getimmediateBonus(player, player.board.getTower(tower).getFloors().get(i).getResourceBonus(), false);
                }

                if(!(member.getColor().equalsIgnoreCase("ghost"))) {
                    player.board.getTower(tower).getFloors().get(i).setfMIsPresent(true);
                    player.board.getTower(tower).getFloors().get(i).setFmOnIt(member);
                }
                if(threecoins){
                    player.getPB().getsingleresource("Coins")
                            .setQuantity(player.getPB().getsingleresource("Coins").getquantity()-3);
                }

                //effetti permanenti
                for(int id : player.board.getTower(tower).getFloors().get(i).getCard().getPermanenteffect())
                    if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)!=null){//TODO
                        System.out.println(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id));
                        player.getEffects().getStrategy()
                                .add(player.board
                                        .getTower(tower)
                                .getFloors()
                                .get(i)
                                .getCard()
                                .activateEffect(id));
                    }

                //mette la carta sulla pb
                player.getPB().addCard(player.board.getTower(tower).getFloors().get(i).getCard());

                FamilyMember ghostmember = new FamilyMember("ghost", "ghost");
                if(GetFreeAction.DicePower!=0) {
                    ghostmember.setValue(GetFreeAction.DicePower);
                    GetFreeAction.DicePower = 0;
                }else if(GetFreeandDiscount.power!=0){
                    ghostmember.setValue(GetFreeandDiscount.power);
                    GetFreeandDiscount.power = 0;
                }

                //effetti immediati per ultimi così fa partire l'eventuale nuova azione gratis
                for(int id : player.board.getTower(tower).getFloors().get(i).getCard().getImmediateeffect()) {
                    //metodo getforeach che passa anche string
                    if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)!=null) {
                        System.out.println(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id));
                        if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                .getClass().getSimpleName().equalsIgnoreCase("GetForEach")) {
                            try {
                                method = player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                        .getClass().getMethod("apply", Player.class, String.class);
                                player = (Player) method.invoke(player.board.getTower(tower).getFloors().get(i)
                                        .getCard().activateEffect(id), player, member.getColor());
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                LOG.log(Level.SEVERE, "Method not found", e);
                            }
                        } else { //metodi che richiedono il solo player
                            if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                    .getClass().getSimpleName().equalsIgnoreCase("GetFreeandDiscount")) {
                                player.getEffects().getStrategy()
                                        .add(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id));
                            }
                            //TODO controllare se l'effetto è un getfreeAction(o un getfreeandDiscount) e farsi passare dal client
                            // il piano che vuole occupare (SISTEMA ANCHE I METODI APPLY DELLE DUE CLASSI)
                            try {
                                method = player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                        .getClass().getMethod("apply", Player.class);
                                player = (Player) method.invoke(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id), player);
                                if (GetFreeAction.harvestOrProduction==1) {
                                    ghostmember.setValue(GetFreeAction.DicePower);
                                    GetFreeAction.DicePower = 0;
                                    addFMonHarvest(player, ghostmember.getColor());
                                }
                                else if (GetFreeAction.harvestOrProduction==2){
                                    ghostmember.setValue(GetFreeAction.DicePower);
                                    GetFreeAction.DicePower = 0;
                                    addFMonProduction(player, ghostmember.getColor(), new ArrayList<Integer>());
                                }
                                else if (GetFreeAction.towerFreeAction.equalsIgnoreCase("color") ||
                                         GetFreeAction.towerFreeAction.equalsIgnoreCase("territory") ||
                                         GetFreeAction.towerFreeAction.equalsIgnoreCase("ventures"))
                                        return GetFreeAction.towerFreeAction;
                                else if( GetFreeandDiscount.towerFreeAction.equalsIgnoreCase("buildings") ||
                                         GetFreeandDiscount.towerFreeAction.equalsIgnoreCase("characters")
                                        )
                                    return GetFreeandDiscount.towerFreeAction;

                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                LOG.log(Level.SEVERE, "Method not found", e);
                            }

                        }
                    }
                    /*
                     */

                }
                //toglie la carta dalla board
                player.board.getTower(tower).getFloors().get(i).setCarta(null);

            }
            i++;
        }
        int k = 0;
        for(Player p : players){
            if(p.getUsername().equalsIgnoreCase(player.getUsername())){
                players[k] = player;
            }
            k++;
        }
        return SUCCESS;
    }

    public static FamilyMember isFMok(FamilyMember member, int floor, Player player, int oldvalue){
       if (member.getValue() < floor) {
           System.out.println(member.getValue());
           System.out.println("Your FM Power is too low!");
           return null;
       }
        return member;
   }

    public static int askFloor(FamilyMember member, Tower towerchosen, Player player){
        boolean freecell = false;
        int floor = 0;
       while(!freecell) {
           System.out.println("Where do you want to put your FM? 1 - 3 - 5 - 7");
           Scanner scandice = new Scanner(System.in);
           floor = scandice.nextInt();

           while (!(floor == 1 || floor == 3 || floor == 5 || floor == 7)) {
               System.out.println("Error on input: Where do you want to put your FM? 1 - 3 - 5 - 7");
               Scanner scansdice = new Scanner(System.in);
               floor = scansdice.nextInt();
           }

           for (CellTower cell : towerchosen.getFloors()) { //TODO e se il giocatore avesse Ludovico Ariosto?
               if (cell.getDice() == floor && cell.isfMPresent()) {
                   System.out.println("There is another FM in this floor");
               } else
                   freecell = true;

           }


       }
       return floor;
   }

    public static Player buyCard(Player player, DevelopementCard card){
        int i = 0;
        List<Risorsa> costtopass = new ArrayList<>();
        if(card.getCardtype().equalsIgnoreCase("cards/territory")){
            return player;
        }else{
            if(card.getChoice()){
                    System.out.println("Do you want to pay with resources or MP? R - MP"); //TODO
                    Scanner scan = new Scanner(System.in);
                    String choice = scan.nextLine();
                    while(!(choice.equalsIgnoreCase("R") || choice.equalsIgnoreCase("MP"))){
                        System.out.println("Error on input : Do you want to pay with resources or MP? R - MP"); //TODO
                        Scanner scans = new Scanner(System.in);
                        choice = scans.nextLine();
                    }
                    if(choice.equalsIgnoreCase("MP")){
                        for(Risorsa cost : card.getCost1()){
                            if(cost.gettipo().equalsIgnoreCase("MPtospend")){
                                cost.setTipo("MilitaryPoints");
                                costtopass.add((Risorsa) cost.clone());
                            }
                        }
                    }else{
                        for(Risorsa cost : card.getCost1()){
                            if(!(cost.gettipo().equalsIgnoreCase("MPtospend")
                                    || cost.gettipo().equalsIgnoreCase("MPnecessary"))){
                                costtopass.add((Risorsa) cost.clone());
                            }
                        }
                    }
                    player = getimmediateBonus(player,costtopass,true);
            }else{
                costtopass = card.getCost1();
                for(Risorsa single : costtopass){
                    if(single.gettipo().equalsIgnoreCase("MPtospend")){
                        costtopass.get(i).setTipo("MilitaryPoints");
                    }
                    i++;
                }
                player = getimmediateBonus(player,costtopass,true);
            }

        }
    return player;
    }

    public static DevelopementCard applydiscount(Player player, DevelopementCard card, boolean free) {

        //SCONTO MONETE PICO
        for(LeaderCard leadcard : player.getcarteLeader()){
            if(leadcard.getClass().getSimpleName().equalsIgnoreCase("PicodellaMirandola") && leadcard.isActive()){
                Method method;
                try {
                    method = leadcard.getClass().getMethod("coinsdiscount", List.class);
                    card.setCost1((List<Risorsa>) method.invoke(leadcard,card.getCost1()));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }


        List<Risorsa> listcost;
        DevelopementCard disccard = card;
        int i;
        if (card.getCardtype().equals("territory"))
            return card;
        else
            listcost = card.getCost1();



       //controlla se può scontare
        if (player.getEffects().getStrategy()!=null) {
            for(EffectStrategy effect : player.getEffects().getStrategy()) {
                if (effect.getClass().getSimpleName().equalsIgnoreCase("GetBoostandDiscount")) {
                    if(effect.getTypeCard().equals(card.getCardtype())) {
                        try {
                            Method method = effect.getClass().getMethod("apply", List.class);
                            listcost = (List<Risorsa>) method.invoke(effect, listcost);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            LOG.log(Level.SEVERE, "Method not found", e);
                        }
                    }
                }
            }
        }

       if(free) {
           //controlla se GetFreeandDiscount
           if (player.getEffects().getStrategy()!=null) {
               for (EffectStrategy effect : player.getEffects().getStrategy()) {
                   if (effect.getClass().getSimpleName().equalsIgnoreCase("GetFreeandDiscount")) {
                       if(effect.getTypeCard().equals(card.getCardtype())) {
                           try {
                               Method method = effect.getClass().getMethod("apply", List.class);
                               listcost = (List<Risorsa>) method.invoke(effect, listcost);
                           } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                               LOG.log(Level.SEVERE, "Method not found", e);

                           }
                       }


                   }
               }
           }

       }
       //modifica il costo della carta

        for(Risorsa playerres : player.getPB().getresources()){
           i=0;
            for(Risorsa discount : listcost){
                if(playerres.gettipo().equals(discount.gettipo()))
                    if(playerres.getquantity() - discount.getquantity() >= 0)
                        disccard.getCost1().get(i).setQuantity(playerres.getquantity() - discount.getquantity());

                i++;

            }
        }

       return disccard; //se la disccard viene modificata lo sconto è applicabile
    }

    public static boolean controlpurchase(Player player, DevelopementCard card, boolean free,boolean threecoins) {
        boolean mpn = false;
        boolean rsn = false;
        int size = 0;
        int i = 0;
        int costsize;

        if(threecoins){
            player.getPB().getsingleresource("Coins").setQuantity(player.getPB().getsingleresource("Coins").getquantity()-3);
            if(player.getPB().getsingleresource("Coins").getquantity()<0)
                return false;
        }

        DevelopementCard newcard = applydiscount(player, card, free);
        if(newcard.getCardtype().equalsIgnoreCase("territory")){
           return true;
        }
        costsize = newcard.getCost1().size();
        while (costsize>0 && newcard.getCost1().get(i).getquantity() != 0){
            if(!(newcard.getCost1().get(i).gettipo().equalsIgnoreCase("MPnecessary") ||
                        (newcard.getCost1().get(i).gettipo().equalsIgnoreCase("MPtospend")))) {
                size++;
            }
            i++;
            costsize--;
        }


        if (newcard.getCardtype().equalsIgnoreCase("ventures")) {
            for (Risorsa mp : newcard.getCost1()) {
                if (mp.gettipo().equalsIgnoreCase("MPnecessary")) {
                    for (Token tokenmp : player.board.getTokens(player.getColor())) {
                        if (tokenmp.getType().equalsIgnoreCase("MilitaryPoints")) {
                            if (mp.getquantity() != 0 && mp.getquantity() <= tokenmp.getPosition())
                                mpn = true;
                        }
                    }
                }
            }
        } //controlla solo le risorse
        for (Risorsa cost : newcard.getCost1()) {
            if (!(cost.gettipo().equalsIgnoreCase("MPtospend") || cost.gettipo().equalsIgnoreCase("MPnecessary"))) {
                for (Risorsa res : player.getPB().getresources()) {
                    if (cost.gettipo().equals(res.gettipo())) {
                        if (cost.getquantity() != 0 && cost.getquantity() <= res.getquantity()) {
                            size--;
                            if (size == 0)
                                rsn = true;
                        }
                    }
                }
            }
        }

        return (mpn || rsn);
    }

    public static Player getimmediateBonus(Player player, List<Risorsa> reward, boolean negative){ //si passa la lista di risorse da prendere o spendere
        int newvalue;
        int i;
        int j = 0;
        Token[] token = player.board.getTokens(player.getColor());
        Risorsa single = new Risorsa();
        List<Risorsa> listfavor;


        reward = controlExcommunicationLessRes(player,reward,negative);

        for(Risorsa resource : reward) {

            if(resource.gettipo().equalsIgnoreCase("PalaceFavor")){
                PALACE = resource.getquantity();
            }

            if(resource.getquantity()!=0){
                single.setTipo(resource.gettipo());
                single.setQuantity(resource.getquantity());
                if(negative)
                    single.setQuantity(-single.getquantity());

                if(single.gettipo().equals("VictoryPoints")) { //fare le modifiche sulla Board
                    for(i=0;i<4;i++) {
                        if (token[i].getType().equals("Victory")) {
                            newvalue = token[i].getPosition() + single.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            token[i].setPosition(newvalue);
                            player.board.setTokens(token);
                        }
                    }
                }else if(single.gettipo().equals("FaithPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Faith")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            token[i].setPosition(newvalue);
                            player.board.setTokens(token);
                        }
                    }

                }else if (single.gettipo().equals("MilitaryPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Military")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            token[i].setPosition(newvalue);
                            player.board.setTokens(token);
                        }
                    }
                }else {

                    for (Risorsa res : player.getPB().getresources()) {
                        if (res.gettipo().equals(single.gettipo())) {
                            newvalue = res.getquantity() + single.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            player.getPB().getresources().get(j).setQuantity(newvalue);
                        }
                        j++;
                    }
                }

            }
        }
        return player;
    }

    public static Player getimmediateBonus(Player player, Risorsa reward, boolean negative){ //si passa la lista di risorse da prendere o spendere
        List<Risorsa> listreward = new ArrayList<>();
        listreward.add(reward);

        player = getimmediateBonus(player,listreward,negative);

        return player;
    }

    public List<Risorsa> choosePalaceFavor(Player player, String choice) { //questa list<risorsa> è la lista parsata dei possibili bonus palazzo
        Risorsa res = new Risorsa();
        List<Risorsa> rewards = new ArrayList<>();
        for (Risorsa singlereward : palaceFavors) {
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
        int i = 0;

        for(Player p : players) {
            if(p.getUsername().equals(player.getUsername()))
                players[i] = getimmediateBonus(player, rewards, false);
            i++;
        }

        return rewards;
    }

    public static List<Risorsa> choosePalaceFavor(List<Risorsa> palaceBonus, int n) { //questa list<risorsa> è la lista parsata dei possibili bonus palazzo
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

    public static List<Risorsa> controlExcommunicationLessRes(Player player, List<Risorsa> reward, boolean negative){
        if(negative){
            return reward;
        }
        if(player.getEffects().getStrategy()!=null){
            for(EffectStrategy effect : player.getEffects().getStrategy()) {
                if (effect.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationLessResources")){
                    Method method;
                    try {
                        method = effect.getClass().getMethod("apply",List.class);
                        reward = (List<Risorsa>) method.invoke(effect,reward);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        LOG.log(Level.SEVERE, "Method not found", e);
                    }
                }
            }
        }

        return reward;
    }



    //GETTERS
    public Board getBoard(){
        return board;
    }


    public Player[] getPlayers(){
        return players;
    }

    public static Player getPlayer(String colorplayer){
        int i = 0;
        Player rightplayer = null;
        for(Player p : players){
            if(p.getColor().equalsIgnoreCase(colorplayer)){
                rightplayer = players[i];
            }
            i++;
        }
        return rightplayer;
    }

    public List<BonusTile> getbonustilesnormal(){
        return bonustilesnormal;
    }

    public List<BonusTile> getBonustileleader() {
        return bonustileleader;
    }

    public PersonalBoard getPersonalBoard(Player player){
       return player.getPB();
    }

    public BuildingDeck getYellowdeck(int turn) {
        return yellowdeck.get(turn-1);
    }

    public CharacterDeck getBluedeck(int turn) {
        return bluedeck.get(turn-1);
    }

    public VentureDeck getVioletdeck(int turn) {
        return violetdeck.get(turn-1);
    }

    public TerritoryDeck getGreendeck(int turn) {
        return greendeck.get(turn-1);
    }

    public ExcommunicationDeck getExcommunicationdeck(int turn) {
        return excommunicationdeck.get(turn-1);
    }

    public static List<Risorsa> getPalaceFavors() {
        return palaceFavors;
    }

    public Player herethePlayer(String username){
        int i = 0;
        for(Player player : players){
            if(player.getUsername().equalsIgnoreCase(username)){
                return players[i];
            }
            i++;
        }
        return null;
    }

    public List<LeaderCard> creatingLeaderDeck(){
        List<LeaderCard> cardList = new ArrayList<>();
        LeaderCard fs = new FrancescoSforza();
        LeaderCard la = new LudovicoAriosto();
        LeaderCard fb = new FilippoBrunelleschi();
        LeaderCard sm = new SigismondoMalatesta();
        LeaderCard gs = new GirolamoSavonarola();
        LeaderCard mb = new MichelangeloBuonarroti();
        LeaderCard gbn = new GiovannidalleBandeNere();
        LeaderCard ldv = new LeonardodaVinci();
        LeaderCard sb = new SandroBotticelli();
        LeaderCard lim = new LudovicoilMoro();
        LeaderCard lb = new LucreziaBorgia();
        LeaderCard fdm = new FedericodaMontefeltro();
        LeaderCard boss = new LorenzodeMedici();
        LeaderCard sq = new SistoIV();
        LeaderCard cb = new CesareBorgia();
        LeaderCard sr = new SantaRita();
        LeaderCard cdm = new CosimodeMedici();
        LeaderCard bc = new BartolomeoColleoni();
        LeaderCard ltg = new LudovicoIIIGonzaga();
        LeaderCard pdm = new PicodellaMirandola();
        cardList.add(fs);
        cardList.add(la);
        cardList.add(fb);
        cardList.add(sm);
        cardList.add(gbn);
        cardList.add(gs);
        cardList.add(mb);
        cardList.add(ldv);
        cardList.add(sb);
        cardList.add(lim);
        cardList.add(lb);
        cardList.add(fdm);
        cardList.add(boss);
        cardList.add(sq);
        cardList.add(cb);
        cardList.add(sr);
        cardList.add(cdm);
        cardList.add(bc);
        cardList.add(ltg);
        cardList.add(pdm);

        return cardList;

    }

    public List<BonusTile> bonusTilesParsing(String gametype) throws FileMalformedException {
        List<BonusTile> listtiles = new ArrayList<>();
        JsonArray arraytile;
        JsonObject jtile;
        int i;
        BonusTile singletile = new BonusTile();
        try{
            if(gametype.equalsIgnoreCase("normal")) {
                File file = new File("src/main/resources/bonustiles/normaltiles.json");
                FileReader read = new FileReader(file.getAbsolutePath());
                arraytile = Json.parse(read).asArray();
            }else {
                File file = new File("src/main/resources/bonustiles/leadertiles.json");
                FileReader read = new FileReader(file.getAbsolutePath());
                arraytile = Json.parse(read).asArray();
            }
            for(i = 0; i<arraytile.size();i++){

                Risorsa bonus  = new Risorsa();
                List<Risorsa> listbonus = new ArrayList<>();
                List<Risorsa> listbonus2 = new ArrayList<>();

                jtile = arraytile.get(i).asObject();

                //Parsing production bonus
                singletile.setType1(jtile.get("type1").asString());
                bonus.setTipo("MilitaryPoints");
                bonus.setQuantity(jtile.getInt("MilitaryPoints1",0));
                listbonus.add((Risorsa) bonus.clone());
                bonus.setTipo("Servants");
                bonus.setQuantity(jtile.getInt("Servants1",0));
                listbonus.add((Risorsa) bonus.clone());
                bonus.setTipo("Coins");
                bonus.setQuantity(jtile.getInt("Coins1",0));
                listbonus.add((Risorsa) bonus.clone());
                singletile.setBonus1(listbonus);


                //Parsing harvest bonus
                singletile.setType2(jtile.get("type2").asString());
                bonus.setTipo("MilitaryPoints");
                bonus.setQuantity(jtile.getInt("MilitaryPoints2",0));
                listbonus2.add((Risorsa) bonus.clone());
                bonus.setTipo("Servants");
                bonus.setQuantity(jtile.getInt("Servants2",0));
                listbonus2.add((Risorsa) bonus.clone());
                bonus.setTipo("Coins");
                bonus.setQuantity(jtile.getInt("Coins2",0));
                listbonus2.add((Risorsa) bonus.clone());
                bonus.setTipo("Woods");
                bonus.setQuantity(jtile.getInt("Woods2",0));
                listbonus2.add((Risorsa) bonus.clone());
                bonus.setTipo("Stones");
                bonus.setQuantity(jtile.getInt("Stones2",0));
                listbonus2.add((Risorsa) bonus.clone());
                singletile.setBonus2(listbonus2);

                listtiles.add((BonusTile) singletile.clone());

                }


        }catch (IOException e){
            LOG.log(Level.CONFIG, "Cannot parse the file", e);
            throw new FileMalformedException("Error in parsing");
        }


        return listtiles;
    }

    public Player chooseBonusTile(Player player , String gametype){
        int choose;
        //show bonustile
        if(gametype.equalsIgnoreCase("normal")) {
            System.out.println("Which bonus tile to you want to use?" +
                    "1-{production,MilitaryPoints:1,Coins:2 && harvest,Woods:1,Stones:1,Servants:1}\n" +
                    "2-{production,MilitaryPoints:1,Coins:2 && harvest,Woods:1,Stones:1,Servants:1}\n" +
                    "3-{production,MilitaryPoints:1,Coins:2 && harvest,Woods:1,Stones:1,Servants:1}\n" +
                    "4-{production,MilitaryPoints:1,Coins:2 && harvest,Woods:1,Stones:1,Servants:1}");
        }else{
            System.out.println("Which bonus tile to you want to use?" +
                    "1-{production,Servants:1,Coins:2 && harvest,Woods:1,Stones:1,MilitaryPoints:1}\n" +
                    "2-{production,MilitaryPoints:2,Coins:1 && harvest,Woods:1,Stones:1,Servants:1}\n" +
                    "3-{production,Servants:1,MilitaryPoints:2 && harvest,Woods:1,Stones:1,Coins:1}\n" +
                    "4-{production,Servants:2,Coins:1 && harvest,Woods:1,Stones:1,MilitaryPoints2:1}");
        }
        Scanner scan = new Scanner(System.in);
        choose = scan.nextInt();
        if(gametype.equalsIgnoreCase("normal")) {
            while(bonustilesnormal.get(choose-1).isChosen()){
                System.out.println("error. bonus tile chosen");
                Scanner scans = new Scanner(System.in);
                choose = scans.nextInt();
            }
            for (BonusTile bt : bonustilesnormal){
                bonustilesnormal.get(choose-1).setChosen(true);
                player.setBonustile(bonustilesnormal.get(choose-1));
            }
        }else{
            while(bonustileleader.get(choose-1).isChosen()){
                System.out.println("error. bonus tile chosen");
                Scanner scans = new Scanner(System.in);
                choose = scans.nextInt();
            }
            for (BonusTile bt : bonustileleader){
                bonustileleader.get(choose-1).setChosen(true);
                player.setBonustile(bonustileleader.get(choose-1));
            }

        }
        return player;

    }


    public void vaticanReport(int turn){
        int i = 0;
        for(Player p : players){
            for(Token token : p.getToken()){
                if(token.getType().equalsIgnoreCase("FaithPoints")){
                    if(token.getPosition()<(2+turn)){
                        players[i].getEffects().getStrategy()
                                .add(board.getExcommTiles().get(turn-1)
                                        .activateEffect(board.getExcommTiles().get(turn-1).getId()));
                    }else{
                        for(CellFaithPoints cell : players[i].board.getFaithPoints()){
                            if(cell.getQuantity() == token.getPosition()){
                                int k = 0;
                                for(Token tok : players[i].getToken()){
                                    if(tok.getType().equalsIgnoreCase("VictoryPoints")){
                                        players[i].getToken()[k].
                                                setPosition(players[i].getToken()[k].getPosition()+cell.getQuantity());
                                        for(LeaderCard card : players[i].getcarteLeader()){
                                            if(card.getClass().getSimpleName()
                                                    .equalsIgnoreCase("SistoIV") && card.isActive()){
                                                players[i].getToken()[k].
                                                        setPosition(players[i].getToken()[k].getPosition()+5);
                                            }
                                        }
                                    }else if(tok.getType().equalsIgnoreCase("FaithPoints")){
                                        players[i].getToken()[k].setPosition(0);
                                    }

                                    k++;
                                }
                            }
                        }
                    }

                }
            }
            i++;
        }

    }

    public void endGameCounting(){
        int i = 0;

        for(Player p : players){
            if(p.getEffects().getStrategy().size()!=0) {
                for (EffectStrategy excomm : p.getEffects().getStrategy()) { //SCOMUNICHE
                    if (excomm.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationEndVP")
                            || excomm.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationLostVP")) {
                        Method method;
                        try {
                            method = excomm.getClass().getMethod("apply", Player.class);
                            players[i] = (Player) method.invoke(excomm, p);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            LOG.log(Level.SEVERE, "Cannot parse the file", e);
                        }
                    }
                }
            }

                Token[] tokens = players[i].board.getTokens(players[i].getColor());
                int numterritories = 0; //GAIN VP FROM TERRITORIES
                for(CellPB cell : p.getPB().getterritories()) {
                    if(cell.getCard()!=null)
                        numterritories++;
                }
                int k = 0;
                for(Token token : tokens){
                    if(token.getType().equalsIgnoreCase("VictoryPoints")){
                        tokens[k].setPosition(token.getPosition()+p.getPB()
                                .getterritories().get(numterritories).getVictoryPoints());
                    }
                    k++;
                }
                int numcharacters = 0; //GAIN VP FROM CHARACTERS
                for(CellPB cell : p.getPB().getcharacters()) {
                    if(cell.getCard()!=null)
                        numcharacters++;
                }
                k=0;
                for(Token token : tokens){
                    if(token.getType().equalsIgnoreCase("VictoryPoints")){
                        tokens[k].setPosition(token.getPosition()+p.getPB()
                                .getcharacters().get(numcharacters).getVictoryPoints());
                    }
                    k++;
                }

                players[i].board.setTokens(tokens); //SETTO I TOKENS DELLA BOARD CHE FANNO PARTIRE L'OBSERVER

                //GAIN VP FROM VENTURES
            if(p.getEffects().getStrategy().size()!=0) {
                for (EffectStrategy effect : p.getEffects().getStrategy()) {
                    if (effect.getClass().getSimpleName().equalsIgnoreCase("GetVPEnd")) {
                        Method method;
                        try {
                            method = effect.getClass().getMethod("apply", Player.class);
                            players[i] = (Player) method.invoke(effect, players[i]);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            LOG.log(Level.SEVERE, "Cannot parse the file", e);
                        }
                    }
                }
            }


            i++;
        }


    }

    public boolean controlrequiresLeadercard(Player player, LeaderCard card){ //CONTROLLO SE PUO' ATTIVARLA
        int sizerequireslist = card.getRequires().size();

        for(Risorsa require : card.getRequires()){
            for(Risorsa res : player.getPB().getresources()){
                if(require.gettipo().equalsIgnoreCase(res.gettipo())){
                    if(res.getquantity()>=require.getquantity()){
                        sizerequireslist--;
                    }
                }
            }
            for(Token token : player.getToken()){
                if(require.gettipo().equalsIgnoreCase(token.getType())){
                    if(token.getPosition()>=require.getquantity()){
                        sizerequireslist--;
                    }
                }
            }
            int quantity = 0;

            if(require.gettipo().equalsIgnoreCase("samedevelopment")) {
                String[] types = {"territory", "characters", "buildings", "ventures"};
                for (String req : types) {
                    quantity = 0;
                    for (CellPB cell : player.getPB().gettypecards(req)) {
                        if (cell.getCard() != null) {
                            quantity++;
                        }
                    }
                    if (quantity >= require.getquantity()) {
                        sizerequireslist--;
                    }
                    if(sizerequireslist==0){
                        return true;
                    }
                }
            }
            for (CellPB cell : player.getPB().gettypecards(require.gettipo())) {
                if (cell.getCard() != null) {
                    quantity++;
                }
            }
                if (quantity >= require.getquantity()) {
                    sizerequireslist--;
            }

        }
        if(sizerequireslist!=0){
            return false;
        }
        return true;
    }

    public String activeLeaderCard(Player player, String name){
        int i = 0;
        int k = 0;
        boolean present = false;
        LeaderCard card = null;
        for(LeaderCard lead : player.getcarteLeader()){
            if(lead.getClass().getSimpleName().equalsIgnoreCase(name)) {
                present = true;
                card = lead;
            }
        }

        if(present) {
            if (controlrequiresLeadercard(player, card)) {
                for (Player p : players) {
                    if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                        for (LeaderCard lead : p.getcarteLeader()) {
                            if (lead.getName().equalsIgnoreCase(card.getName())) {
                                players[i].getcarteLeader().get(k).setActive(true);

                                if ((lead.getClass().getSimpleName().equalsIgnoreCase("LudovicoilMoro"))
                                        || (lead.getClass().getSimpleName().equalsIgnoreCase("LucreziaBorgia"))
                                        || (lead.getClass().getSimpleName().equalsIgnoreCase("SigismondoMalatesta"))) {
                                    for (FamilyMember member : player.getMembers()) {
                                        Method method;
                                        FamilyMember helper;
                                        try {
                                            method = lead.getClass().getMethod("boostmember", FamilyMember.class);
                                            helper = (FamilyMember) method.invoke(lead, member);
                                            players[i].getMember(member.getColor()).setValue(helper.getValue());
                                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                } else if (lead.getClass().getSimpleName().equalsIgnoreCase("CesareBorgia")) {
                                    players[i].unlockGreenCell(25);
                                }
                            }
                        }
                        k++;
                    }
                    i++;
                }
            } else
                return FAIL;
        }else
            return FAIL;
        return SUCCESS;
    }

    public String discardLeaderCard(Player player,String name,String favor){
        boolean present = false;
        LeaderCard card = null;
        for(LeaderCard lead : player.getcarteLeader()){
            if(lead.getClass().getSimpleName().equalsIgnoreCase(name)) {
                present = true;
                card = lead;
            }
        }
        if(present){

            List<Risorsa> listfavor = choosePalaceFavor(player, favor);
            player = getimmediateBonus(player, listfavor, false);

            int k = 0;
            int i = 0;
            for (Player p : players) {
                if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                    players[k] = player;
                    for(LeaderCard leaderCard : players[k].getcarteLeader()){
                        if(leaderCard.getName().equalsIgnoreCase(card.getName())){
                            players[k].getcarteLeader().remove(leaderCard);
                        }
                        i++;
                    }
                }
                k++;
            }

        }else
            return FAIL;
        return SUCCESS;
    }

    /*public String activeinaRow(Player player){
        for(LeaderCard card : player.getcarteLeader()){
            if(card.isActive()){
                if(card.getClass().getSimpleName().equalsIgnoreCase("FedericodaMontefeltro")){
                    return "member";

                }else
                if(card.getClass().getSimpleName().equalsIgnoreCase("LorenzodeMedici")){

                }else
                if(card.getClass().getSimpleName().equalsIgnoreCase("FrancescoSforza") ||
                        card.getClass().getSimpleName().equalsIgnoreCase("LeonardodaVinci")){

                }else
                if(card.getClass().getSimpleName().equalsIgnoreCase("LudovicoIIIGonzaga")){

                    return "favor";
                }else {
                    Method method;
                    try {
                        method = card.getClass().getMethod("onceInaRow", Player.class);
                        method.invoke(card, player);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        int k = 0;
        for (Player p : players) {
            if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
                players[k] = player;
            }
        }
    }*/

    public StringBuilder showOrder(){
        StringBuilder showorder = new StringBuilder();
        int i = 0;
        showorder.append("Order turn\n");
        for(Player p : order){
            showorder.append(i+1);
            showorder.append(" ");
            showorder.append(p.getUsername());
            showorder.append("\n");
            i++;
        }


        return showorder;
    }

    public StringBuilder showotherPlayers(){
        StringBuilder showothers = new StringBuilder();

        for(Player player : players) {
            showothers.append(player.getPB().showPB());
            showothers.append("\n");
            showothers.append(player.getBonustile().showBonusTile());
            showothers.append("\n");
            for (LeaderCard card : player.getcarteLeader()) {
                if (card.isActive()) {
                    showothers.append(card.getName());
                    showothers.append("\n");
                }
            }
        }

        return showothers;
    }

    public StringBuilder showPossibleActions(){
        StringBuilder showactions = new StringBuilder();

        showactions.append("fm on tower\n");
        showactions.append("fm on market\n");
        showactions.append("fm on harvest\n");
        showactions.append("fm on production\n");
        showactions.append("fm on palace\n");
        showactions.append("discard leader card to get a PF\n");
        showactions.append("showPlayergoods\n");
        showactions.append("showboard\n");
        showactions.append("showotherPlayers\n");
        showactions.append("showOrder\n");
        showactions.append("fm power up\n");


        return showactions;
    }

    public String spendservants(Player player, String member , int servants){ //servant==punti da aggiungere al dado
        int oldvalue;
        int oldservants;
        int coeff = 1;
        int i = 0;

        for(EffectStrategy effect : player.getEffects().getStrategy()){
            if(effect.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationServants")){
                coeff = 2;
            }
        }

        oldservants = player.getPB().getsingleresource("Servants").getquantity();
        if(oldservants>(coeff*servants)){
            oldvalue = player.getMember(member).getValue();
            player.getMember(member).setValue(oldvalue + (servants));
            player.getPB().getsingleresource("Servants").setQuantity(oldservants - (coeff*servants));

            for(Player p : players){
                if(p.getUsername().equalsIgnoreCase(players[i].getUsername())){
                    players[i] = player;
                }
            }
        }else {
            return FAIL;
        }
        return SUCCESS;

    }

    public void draftbonustiles(){

    }

}