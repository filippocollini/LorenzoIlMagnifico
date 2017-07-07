package it.polimi.ingsw.GameModelServer;




import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.Stanza;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * 
 */
public class Game implements Serializable {

    private Player[] players;
    private Board board;
    public static final String SUCCESS="success";
    public static final String FAIL="fail";
    public static final String EXCOMM="excomm";
    public static final String SAMETOWER="sametower";
    private int turn;
    private List<BonusTile> bonustiles;
    private GameStatus stato;
    private List<TerritoryDeck> greendeck;
    private List<BuildingDeck> yellowdeck;
    private List<CharacterDeck> bluedeck;
    private List<VentureDeck> violetdeck;
    private static List<Risorsa> palaceFavors;
    private List<ExcommunicationDeck> excommunicationdeck;
    private List<Player> order;



    public Game(HashMap<String, AbstractPlayer> abplayers, Stanza room) {
        this.board = Board.getInstance(room.nPlayers());

        this.players = creatingPlayers(abplayers,room.nPlayers());
        turn = 1;
        bonustiles = new ArrayList<>();
        this.stato = stato;

        greendeck = creatingGreenDeck(territoryParsing());
        yellowdeck = creatingYellowDeck(buildingParsing());
        bluedeck = creatingBlueDeck(characterParsing());
        violetdeck = creatingVioletDeck(ventureParsing());
        palaceFavors = palaceFavorparsing();
        excommunicationdeck = creatingExcommunicationDeck(creatingExcommunicationTiles());
        order = setOrderFirstTurn();
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

    public void setLeaderCard(){}

    public List<Player> setOrderFirstTurn(){
        List<Player> turn = new ArrayList<>();
        turn.addAll(Arrays.asList(players));

        Collections.shuffle(turn);
        /*int i = 0;
        for(Player player : players) {
            for(Token token :board.getTokens(player.getColor())){
                if(token.getType().equalsIgnoreCase("Order")){
                    board.getTokens(player.getColor())[i].setPosition();
                }

            }
        }*/
        return turn;
    }

    public void setBonustiles(List<Player> players){}

    public void setResources(List<Player> players){

    }

    //Parsing palacefavor Bonus
    private List<Risorsa> palaceFavorparsing(){
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
            e.printStackTrace(); //TODO
        }

        return favors;
    }


    //parsing developement cards
    private List<TerritoryCard> territoryParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new TerritoryCard();
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

    private List<BuildingCard> buildingParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new BuildingCard();
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
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    private List<CharacterCard> characterParsing() {

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
            e.printStackTrace(); // TODO
        }
        return cardList;
    }

    private List<VentureCard> ventureParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new VentureCard();
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

    private List<ExcommunicationTiles> creatingExcommunicationTiles(){
        List<ExcommunicationTiles> tilesList = new ArrayList<>();
        ExcommunicationTiles tile = new ExcommunicationTiles();
        int i;

        for(i=0; i<21;i++) {
            if(i<7) {
                tile.setId(11+i);
                tile.setPeriod(1);
                tilesList.add(i, (ExcommunicationTiles) tile.clone());
            }else if(i<14){
                tile.setId(14+i);
                tile.setPeriod(2);
                tilesList.add(i, (ExcommunicationTiles) tile.clone());
            }else {
                tile.setId(17 + i);
                tile.setPeriod(3);
                tilesList.add(i,(ExcommunicationTiles) tile.clone());
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

    public Player addFMonHarvest(Player player){
        String type = "harvest";
        String color = askMember();
        int oldvalue;
        oldvalue = player.getMember(color).getValue();

        for(CellAction cell : board.getHarvest()){ //controllo se ha già altri fm qui
            if(cell.getcolorMember().equals(player.getColor()) && !cell.getMember().getColor().equalsIgnoreCase("Neutral")){
                System.out.println("You've another FM here!"); //TODO
                return player;
            }
        }

        player.getMember(color).setValue(controlboost(player,player.getMember(color),type).getValue());

        CellAction space;
        space = board.createCellHarvestorPoduction();
        space.setType(type);

        if(board.getHarvest().size() == 1 && players.length<3){
            System.out.println("there is no space here"); //TODO
            player.getMember(color).setValue(oldvalue);
            return player;
        }else if(board.getHarvest().size()>=1){
            space.setDice(-3);
            player.getMember(color).setValue(player.getMember(color).getValue()+space.getDice());
        }
        if(player.getMember(color).getValue()>1){
            for(CellPB cellPB : player.getPB().getterritories()){
                for(EffectStrategy effect : player.getEffects().getStrategy()){
                    for(int id : cellPB.getCard().getPermanenteffect()){
                        if(effect.getId() == id){
                            Method method;
                            try {
                                method = effect.getClass().getMethod("apply", Player.class,String.class);
                                player = (Player) method.invoke(effect,player,color);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }

            // TODO get bonus personale dalla tile
        }else {
            System.out.println("you can't do the action because the power is too low"); //TODO
            player.getMember(color).setValue(oldvalue);
            return player;
        }

        space.setFamilyMemberinCell(player.getMember(color));
        board.getHarvest().add(space);

        return player;
    }

    public Player addFMonProduction(Player player){
        String type = "production";
        String color = askMember();
        Player control;
        int oldvalue;
        oldvalue = player.getMember(color).getValue();
        List<Risorsa> gained = new ArrayList<>();

        for(CellAction cell : board.getProduction()){ //controllo se ha già altri fm qui
            if(cell.getcolorMember().equals(player.getColor()) && !cell.getMember().getColor().equalsIgnoreCase("Neutral")){
                System.out.println("You've another FM here!"); //TODO
                return player;
            }
        }

        player.getMember(color).setValue(controlboost(player,player.getMember(color),type).getValue());

        CellAction space;
        space = board.createCellHarvestorPoduction();
        space.setType(type);

        if(board.getProduction().size() == 1 && players.length<3){
            System.out.println("there is no space here"); //TODO
            player.getMember(color).setValue(oldvalue);
            return player;
        }else if(board.getHarvest().size()>=1){
            space.setDice(-3);
            player.getMember(color).setValue(player.getMember(color).getValue()+space.getDice());
        }

        int choice;
        int k;
        int j = 0;
        int i = 0;
        boolean spent = false;
        if(player.getMember(color).getValue()>1){
            for(CellPB cellPB : player.getPB().getbuildings()){
                for(EffectStrategy effect : player.getEffects().getStrategy()){
                    choice = effect.getId();
                    if(cellPB.getCard().getPermchoice()){
                        System.out.println("Which effect do you want to activate?"); //TODO
                        for(k=0;k<cellPB.getCard().getPermanenteffect().size();k++)
                            System.out.println(cellPB.getCard().getPermanenteffect().get(k));
                        Scanner scan = new Scanner(System.in);
                        choice = scan.nextInt();
                    }
                    for(int id : cellPB.getCard().getPermanenteffect()){

                        if(effect.getId() == id && choice == id){
                            Method method;
                            Method method2;
                            try {
                                method = effect.getClass().getMethod("apply", Player.class,String.class);
                                control = (Player) method.invoke(effect,player,color);
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
                                    gained = (List<Risorsa>) method2.invoke(effect, gained, player, color);
                                }
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
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

            // TODO get bonus personale dalla tile
        }else {
            System.out.println("you can't do the action because the power is too low"); //TODO
            player.getMember(color).setValue(oldvalue);
            return player;
        }

        space.setFamilyMemberinCell(player.getMember(color));
        board.getHarvest().add(space);

        return player;
    }

    public Player addFMonPalace(Player player){
       String color = askMember();
        if(player.getMember(color).getValue()>=1) {

           CellAction space;

           space = board.createCellPalace();

           space.setFamilyMemberinCell(player.getMember(color));

           player = getimmediateBonus(player,space.getBonus(),false);

           board.getCouncilpalace().add(space);
        }else System.out.println("non puoi fare l'azione"); //TODO

       return player;
    }

    public String addFMonMarket(Player player, String member, String request){
        if (player.getEffects().getStrategy()!=null) {
            for (EffectStrategy effect : player.getEffects().getStrategy()) {
                if (effect.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationCoverMarket")) {
                    System.out.println("YOU'VE BEEN EXCOMMUNICATED! Go away from Magnifico's Market");
                    return EXCOMM;
                }
            }
        }
            if(player.getMember(member).getValue()>1) {
                for (CellAction cell : board.getMarket()) {
                    if (cell.getType().equalsIgnoreCase(request)){
                        player = getimmediateBonus(player,cell.getBonus(),false);
                    }
                }
                return SUCCESS;

            }else
                System.out.println("non puoi fare l'azione"); //TODO

        return FAIL;
    }

    /*public String askCellMarket(){
        int size = board.getMarket().size();
        String choice;
        if(size==2){
            System.out.println("What do you want to pick from Market?" +
                    "5 Coins(C) - 5 Servants(S)"); //TODO
        }else
            System.out.println("What do you want to pick from Market?" +
                    "5 Coins(C) - 5 Servants(S) - 3MP and 2 Coins(MPC) - 2PalaceFavors(PF) ");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextLine();
        while((size>=2 && !(choice.equalsIgnoreCase("C") || choice.equalsIgnoreCase("S")))||
                (size==4 && !(choice.equalsIgnoreCase("MPC") || choice.equalsIgnoreCase("PF")))){
            System.out.println("Error in input");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine();
        }
        return choice;
    }*/

    public static String askTower(){
        String type;
        System.out.println("Which tower do you want to occupy? territory - buildings - ventures - characters");
        Scanner scanner = new Scanner(System.in);
        type = scanner.nextLine();
        while(!(type.equalsIgnoreCase("territory") || type.equalsIgnoreCase("characters")
                || type.equalsIgnoreCase("ventures") || type.equalsIgnoreCase("buildings"))){
            System.out.println("Error on input : Which tower do you want to occupy? territory - buildings - ventures - characters");
            Scanner scanning = new Scanner(System.in);
            type = scanning.nextLine();
        }
        return type;
    }

    public String askMember() {
        String choice;
        System.out.println("Which FM do you want to use? White - Black - Orange - Neutral"); //TODO
        Scanner scan = new Scanner(System.in);
        choice = scan.nextLine();
        while(!(choice.equals("White") || choice.equals("Black") || choice.equals("Orange") || choice.equals("Neutral"))) {
            System.out.println("Error on input : Which FM do you want to use? White - Black - Orange - Neutral"); //TODO
            Scanner scans = new Scanner(System.in);
            choice = scans.nextLine();
        }
        return choice;
    }

    public static FamilyMember controlboost(Player player, FamilyMember member, String type){
        if (player.getEffects().getStrategy()!=null) {
            for(EffectStrategy effect : player.getEffects().getStrategy()){
                if(effect.getClass().getSimpleName().equalsIgnoreCase("GetBoostandDiscount")
                        || effect.getClass().getSimpleName().equalsIgnoreCase("GetBoostDice")
                        || effect.getClass().getSimpleName().equalsIgnoreCase("ExcommunicationReduction")) {
                    Method method;
                    try {
                        method = effect.getClass().getMethod("apply",  FamilyMember.class,String.class);
                        member = (FamilyMember) method.invoke(effect, member,type);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();//TODO
                    }
                }
            }
        }

        return member;
    }

    public String addFMonTowerControl(Player player, String member, String tower, int floor){
       Tower towerchosen;
       int dice ;
       int size;
       boolean mps = false;
       int oldvalue;

       towerchosen = player.board.getTower(tower);
       if(member.equals("Neutral")) {
       } else{
           for (CellTower cell : towerchosen.getFloors()) {
               if (cell.getFmOnIt()!=null){
                   if (cell.getFmOnIt().getColorplayer().equals(player.getColor()) && !(cell.getFmOnIt().getColor().equals("Neutral"))) {
                       System.out.println("You've a FM here yet! choose another action"); //TODO al client 'do another action'
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

            if (!controlpurchase(player,towerchosen.getFloors().get(dice).getCard(),false)) {
                player.getMember(member).setValue(oldvalue);
                System.out.println("you cannot buy the card! PORACCIO!!!"); //TODO
                return FAIL;
            }

        //poi vedo se il suo fm basta o si deve potenziare
        player.getMember(member).setValue(isFMok(player.getMember(member),dice,player,oldvalue).getValue());

        //poi faccio l'azione applicando lo sconto
        player = addFMonTowerAction(player, player.getMember(member),dice, tower,false);

    return SUCCESS;
   }

    public static Player addFMonTowerAction(Player player, FamilyMember member, int floor, String tower, boolean free){
        //do action
        int i = 0;
        DevelopementCard card;

        for(CellTower cell : player.board.getTower(tower).getFloors()) {
            if(cell.getDice() == floor){
                Method method;
                //applica sconto
                card = applydiscount(player,player.board.getTower(tower).getFloors().get(floor).getCard(),free);

                //poi la compra
                player = buyCard(player,card);

                player = getimmediateBonus(player,player.board.getTower(tower).getFloors().get(i).getResourceBonus(),false);
                player.board.getTower(tower).getFloors().get(i).setfMIsPresent(true);
                player.board.getTower(tower).getFloors().get(i).setFmOnIt(member);
                //TODO 3 monete

                //effetti permanenti
                for(int id : player.board.getTower(tower).getFloors().get(i).getCard().getPermanenteffect())
                    if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)==null){//TODO
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
                                e.printStackTrace();//TODO
                            }
                        } else { //metodi che richiedono il solo player
                            if (player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                    .getClass().getSimpleName().equalsIgnoreCase("GetFreeandDiscount")) {
                                player.getEffects().getStrategy()
                                        .add(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id));
                            }
                            try {
                                method = player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id)
                                        .getClass().getMethod("apply", Player.class);
                                player = (Player) method.invoke(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id), player);
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                e.printStackTrace();//TODO
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
        return player;
    }

    public static FamilyMember isFMok(FamilyMember member, int floor, Player player, int oldvalue){
       if (member.getValue() < floor) {
           System.out.println(member.getValue());
           System.out.println("Your FM Power is too low!");


           System.out.println("Do You want to power up it spending servants? Y - any other letter to say NO");
           Scanner action = new Scanner(System.in);
           if (action.nextLine().equalsIgnoreCase("Y")) {
               int servant;
               servant = floor - member.getValue();
               member.setValue( player.spendservants(member, servant).getValue());

           } else {
               member.setValue(oldvalue);
               return null; //TODO
           }
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

        List<Risorsa> listcost = null;
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
                            e.printStackTrace();
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
                               e.printStackTrace();

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

    public static boolean controlpurchase(Player player, DevelopementCard card, boolean free) {
        boolean mpn = false;
        boolean rsn = false;
        int size = 0;
        int i = 0;
        DevelopementCard newcard = applydiscount(player, card, free);
        if(newcard.getCardtype().equals("territory")){
           return true;
        }
        while (newcard.getCost1().get(i).getquantity() != 0 &&
                !(newcard.getCost1().get(i).gettipo().equalsIgnoreCase("MPnecessary") ||
                        (newcard.getCost1().get(i).gettipo().equalsIgnoreCase("MPtospend")))) {
            size++;
            i++;
        }
        if(newcard.getCardtype().equalsIgnoreCase("territory"))
            return true;

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
            if (resource.gettipo().equals("PalaceFavor") && resource.getquantity()!=0) {
                listfavor = choosePalaceFavor(palaceFavors,resource.getquantity());
                player = getimmediateBonus(player,listfavor,false);
            }else if(resource.getquantity()!=0){
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
                        e.printStackTrace();
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

}