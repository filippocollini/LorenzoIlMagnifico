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
    private List<PersonalBoard> personalboard;
    private int turn;
    private List<BonusTile> bonustiles;
    private GameStatus stato;
    private List<TerritoryDeck> greendeck;
    private List<BuildingDeck> yellowdeck;
    private List<CharacterDeck> bluedeck;
    private List<VentureDeck> violetdeck;
    private List<Risorsa> palaceFavors;



    public Game(HashMap<String, AbstractPlayer> abplayers, Stanza room) {
        this.players = creatingPlayers(abplayers,room.nPlayers());
        turn = 1;
        bonustiles = new ArrayList<>();
        this.stato = stato;
        this.board = Board.getInstance(players);
        greendeck = creatingGreenDeck(territoryParsing());
        yellowdeck = creatingYellowDeck(buildingParsing());
        bluedeck = creatingBlueDeck(characterParsing());
        violetdeck = creatingVioletDeck(ventureParsing());
        palaceFavors = palaceFavorparsing();
    }

    //setting initial game



    private Player[] creatingPlayers(HashMap<String,AbstractPlayer> abplayers,int nplayers){
        Player[] players = new Player[nplayers];
        List<String> previouscolors = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,AbstractPlayer> entry : abplayers.entrySet()){
            players[i] = new Player(entry.getKey(),randomcolor(previouscolors),board);
            i++;
        }
        return players;
    }


    private String randomcolor(List<String> previouscolor){

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



    public void fillGreenTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("Territory").getFloors().iterator();
        while(iter.hasNext()){
            board.getTower("Territory").getFloors().get(i).setCarta(greendeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillYellowTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("Building").getFloors().iterator();
        while(iter.hasNext()){
            board.getTower("Building").getFloors().get(i).setCarta(yellowdeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillBlueTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("Character").getFloors().iterator();
        while(iter.hasNext()){
            board.getTower("Character").getFloors().get(i).setCarta(bluedeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillVioletTower(int turn){
        int i = 0;
        Iterator iter = board.getTower("Venture").getFloors().iterator();
        while(iter.hasNext()){
            board.getTower("Venture").getFloors().get(i).setCarta(violetdeck.get(turn-1).drawfirstCard());
            i++;
        }
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
    private List<TerritoryCard> territoryParsing() {

        JsonArray jarraycard;
        JsonObject jcard;
        JsonArray jarrayimm;
        JsonArray jarrayper;

        int i,j,k;
        DevelopementCard singlecard = new TerritoryCard();
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

    public List<ExcommunicationTiles> creatingExcommunicationTiles(){
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


   /*public void addFMonPalace(Player player, String color){
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

    }*/

    public String askTower(){
        String type;
        System.out.println("Which tower do you want to occupy? Territory - Building - Venture - Character");
        Scanner scanner = new Scanner(System.in);
        type = scanner.nextLine();
        while(!(type.equals("Territory") || type.equals("Character") || type.equals("Venture") || type.equals("Building"))){
            System.out.println("Error on input : Which tower do you want to occupy? Territory - Building - Venture - Character");
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

    public FamilyMember controlboost(Player player ,FamilyMember member){
        for(EffectStrategy effect : player.getEffects().getStrategy()){
            if(effect.getClass().getSimpleName().equals("GetBoostandDiscount") || effect.getClass().getSimpleName().equals("GetBoostDice")) {
                Method method;
                try {
                    method = effect.getClass().getMethod("apply",  FamilyMember.class);
                    member = (FamilyMember) method.invoke(effect, member);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();//TODO
                }
            }
        }
        return member;
    }

    public Player addFMonTowerControl(Player player){
       String choice;
       String type;
       Tower towerchosen;
       int dice ;
       int size;
       boolean mps = false;
       int oldvalue;

       choice = askMember();
       type = askTower();
       towerchosen = player.board.getTower(type);
       if(choice.equals("Neutral")) {
       } else{
           for (CellTower cell : towerchosen.getFloors()) {
               if (cell.getFmOnIt().getColorplayer().equals(player.getColor()) && !(cell.getFmOnIt().getColor().equals("Neutral"))) {
                   System.out.println("You've a FM here yet! choose another action"); //TODO al client 'do another action'
                   return player;
               }
           }
       }
       oldvalue = player.getMember(choice).getValue();
       //controlla se può boostare il familymember e lo fa
       player.getMember(choice).setValue( controlboost(player , player.getMember(choice)).getValue());

       dice = askFloor(player.getMember(choice),towerchosen,player);
        //prima controllo se può comprare la carta altrimenti ritorno

            if (!controlpurchase(player,towerchosen.getFloors().get(dice).getCard(),false)) {
                player.getMember(choice).setValue(oldvalue);
                System.out.println("you cannot buy the card! PORACCIO!!!"); //TODO
                return player;
            }

        //poi vedo se il suo fm basta o si deve potenziare
        player.getMember(choice).setValue(isFMok(player.getMember(choice),dice,player,oldvalue).getValue());

        //poi faccio l'azione applicando lo sconto
        player = addFMonTowerAction(player, player.getMember(choice),dice, type,false);

    return player;
   }

    public Player addFMonTowerAction(Player player,FamilyMember member, int floor,String tower, boolean free){
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


                //effetti permanenti
                for(int id : player.board.getTower(tower).getFloors().get(i).getCard().getPermanenteffect())
                    player.getEffects().getStrategy()
                            .add(player.board.getTower(tower).getFloors().get(i).getCard().activateEffect(id));

                //mette la carta sulla pb
                player.getPB().addCard(player.board.getTower(tower).getFloors().get(i).getCard());


                //effetti immediati per ultimi così fa partire l'eventuale nuova azione gratis
                for(int id : player.board.getTower(tower).getFloors().get(i).getCard().getImmediateeffect()) {
                    //metodo getforeach che passa anche string
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
                //toglie la carta dalla board
                player.board.getTower(tower).getFloors().get(i).setCarta(null);

            }
            i++;
        }
        return player;
    }

    public FamilyMember isFMok (FamilyMember member, int floor, Player player,int oldvalue){
       if (member.getValue() < floor) {
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

    public int askFloor(FamilyMember member, Tower towerchosen, Player player){
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

    public Player buyCard(Player player,DevelopementCard card){
        int i = 0;
        List<Risorsa> costtopass = new ArrayList<>();
        if(card.getCardtype().equalsIgnoreCase("territory")){
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
                                costtopass.add(cost);
                            }
                        }
                    }else{
                        for(Risorsa cost : card.getCost1()){
                            if(!(cost.gettipo().equalsIgnoreCase("MPtospend")
                                    || cost.gettipo().equalsIgnoreCase("MPnecessary"))){
                                costtopass.add(cost);
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

    public DevelopementCard applydiscount(Player player, DevelopementCard card ,boolean free) {

        List<Risorsa> listcost = null;
        DevelopementCard disccard = card;
        int i;
        listcost = card.getCost1();

       //controlla se può scontare
       for(EffectStrategy effect : player.getEffects().getStrategy()) {
           if (effect.getClass().getSimpleName().equals("GetBoostandDiscount")) {
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
       if(free) {
           //controlla se GetFreeandDiscount
           for (EffectStrategy effect : player.getEffects().getStrategy()) {
               if (effect.getClass().getSimpleName().equals("GetFreeandDiscount")) {
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

    public boolean controlpurchase(Player player, DevelopementCard card,boolean free) {
        boolean mpn = false;
        boolean rsn = false;
        int size = 0;
        int i = 0;
        DevelopementCard newcard = applydiscount(player, card, free);
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

    public Player getimmediateBonus(Player player,List<Risorsa> reward, boolean negative){ //si passa la lista di risorse da prendere o spendere
        int newvalue;
        int i;
        int j = 0;
        Token[] token = player.board.getTokens(player.getColor());
        Risorsa single = new Risorsa();
        List<Risorsa> listfavor;
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

    public Player getimmediateBonus(Player player,Risorsa reward, boolean negative){ //si passa la lista di risorse da prendere o spendere
        int newvalue;
        int i;
        int j = 0;
        Token[] token = player.board.getTokens(player.getColor());

        List<Risorsa> listfavor;

            if (reward.gettipo().equals("PalaceFavor") && reward.getquantity()!=0) {
                listfavor = choosePalaceFavor(palaceFavors,reward.getquantity());
                player = getimmediateBonus(player,listfavor,false);
            }else if(reward.getquantity()!=0){

                if(negative)
                    reward.setQuantity(-reward.getquantity());

                if(reward.gettipo().equals("VictoryPoints")) { //fare le modifiche sulla Board
                    for(i=0;i<4;i++) {
                        if (token[i].getType().equals("Victory")) {
                            newvalue = token[i].getPosition() + reward.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            token[i].setPosition(newvalue);
                            player.board.setTokens(token);
                        }
                    }
                }else if(reward.gettipo().equals("FaithPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Faith")){
                            newvalue=token[i].getPosition()+reward.getquantity();
                            if(newvalue<0) {
                                System.out.println("non hai punti da spendere"); //TODO
                                return player;
                            }
                            token[i].setPosition(newvalue);
                            player.board.setTokens(token);
                        }
                    }

                }else if (reward.gettipo().equals("MilitaryPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Military")){
                            newvalue=token[i].getPosition()+reward.getquantity();
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
                        if (res.gettipo().equals(reward.gettipo())) {
                            newvalue = res.getquantity() + reward.getquantity();
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

        return player;
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