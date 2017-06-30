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
        Iterator iter = board.getTerritoriesTower().getFloors().iterator();
        while(iter.hasNext()){
            board.getTerritoriesTower().getFloors().get(i).setCarta(greendeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillYellowTower(int turn){
        int i = 0;
        Iterator iter = board.getBuildingsTower().getFloors().iterator();
        while(iter.hasNext()){
            board.getBuildingsTower().getFloors().get(i).setCarta(yellowdeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillBlueTower(int turn){
        int i = 0;
        Iterator iter = board.getCharactersTower().getFloors().iterator();
        while(iter.hasNext()){
            board.getCharactersTower().getFloors().get(i).setCarta(bluedeck.get(turn-1).drawfirstCard());
            i++;
        }
    }

    public void fillVioletTower(int turn){
        int i = 0;
        Iterator iter = board.getVenturesTower().getFloors().iterator();
        while(iter.hasNext()){
            board.getVenturesTower().getFloors().get(i).setCarta(violetdeck.get(turn-1).drawfirstCard());
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

    private List<BuildingCard> buildingParsing() {

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

    private List<CharacterCard> characterParsing() {

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

    private List<VentureCard> ventureParsing() {

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


   /* public void addFMonPalace(Player player, String color){
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
    //TODO da provare il metodo sopra
    public void getimmediateBonus(Player player,List<Risorsa> reward, Board board){ //si passa la lista di risorse da prendere
        int newvalue;
        int i;
        Token[] token = board.getTokens(player.getColor());
        Risorsa single = new Risorsa();
        List<Risorsa> listfavor;
        for(Risorsa resource : reward) {
            if (resource.gettipo().equals("PalaceFavor") && resource.getquantity()!=0) {
                listfavor = choosePalaceFavor(palaceFavors,resource.getquantity());
                getimmediateBonus(player,listfavor,board);
            }else if(resource.getquantity()!=0){
                single.setTipo(resource.gettipo());
                single.setQuantity(resource.getquantity());

                if(single.gettipo().equals("VictoryPoints")) { //fare le modifiche sulla Board
                    for(i=0;i<4;i++) {
                        if (token[i].getType().equals("Victory")) {
                            newvalue = token[i].getPosition() + single.getquantity();
                            token[i].setPosition(newvalue);
                            board.setTokens(token);
                        }
                    }
                }else if(single.gettipo().equals("FaithPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Faith")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            token[i].setPosition(newvalue);
                            board.setTokens(token);
                        }
                    }

                }else if (single.gettipo().equals("MilitaryPoints")){
                    for(i=0;i<4;i++){
                        if(token[i].getType().equals("Military")){
                            newvalue=token[i].getPosition()+single.getquantity();
                            token[i].setPosition(newvalue);
                            board.setTokens(token);
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