package it.polimi.ingsw.GameModelServer;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class Board{


    private List victoryPoints;
    private List<CellFaithPoints> faithPoints;
    private List militaryPoints;
    private List<Player> ordineTurno;
    private List production;
    private List harvest;
    private List<CellAction> market;
    private List<ExcommunicationTiles> carteScomunica;
    private List<CellAction> councilpalace;
    private List<Integer> dadi;
    private static Board instance;
    private Tower territoriesTower;
    private Tower buildingsTower;
    private Tower charactersTower;
    private Tower venturesTower;
    private List<Token[]> tokens;
    private Player[] players;
    private List<BoardObserver> observers = new ArrayList<>();


    private Board(Player[] players){
        territoriesTower = creategreenTower();
        buildingsTower = createyellowTower();
        charactersTower = createblueTower();
        venturesTower = createvioletTower();
        victoryPoints = new ArrayList();
        faithPoints = setFaithPointsTrack();
        militaryPoints = new ArrayList();
        market = createMarket(players.length);
        councilpalace = new ArrayList<>();
        harvest = new ArrayList();
        production = new ArrayList();
        tokens = inizializationTokens(players);
        this.players = players;

        //WARNING!!! per bonus da caricare da file intendiamo anche i malus nei dadi degli spazi raccolto e produzione?
        //metto tutti i metodi così si creano non appena si crea la board!!!

    }



    private List<Token[]> inizializationTokens(Player[] players) {
        int i;
        List<Token[]> tokens = new ArrayList<>();
        for(i=0;i<players.length;i++) {
            Token[] token = new Token[4];
            token[0] = new Token(players[i].getColor());
            token[0].setType("VictoryPoints");
            token[0].setPosition(0);
            token[1] = new Token(players[i].getColor());
            token[1].setType("MilitaryPoints");
            token[1].setPosition(0);
            token[2] = new Token(players[i].getColor());
            token[2].setType("FaithPoints");
            token[2].setPosition(0);
            token[3] = new Token(players[i].getColor());
            token[3].setType("Order");
            tokens.add(i, token);
        }
        notifyAllObservers();
        return tokens;

    }

    //TODO get player per poterli vedere da tutti
    public PersonalBoard getPBPlayer(String color){
        PersonalBoard colorpb = new PersonalBoard();
        int i;
        for(i=0;i<this.players.length;i++){
            if(this.players[i].getColor().equals(color))
                colorpb = this.players[i].getPB();
        }

        return colorpb;
    }

    public CellAction createPalace(){ //TODO sistema CellAction con List
            int i;

            i=0;
            for(CellAction space : this.councilpalace)
                i++;

            JsonObject jpalace;
            CellAction cell = new CellAction();
            Risorsa favor = new Risorsa();
            Risorsa coin = new Risorsa();
            List<Risorsa> reward = new ArrayList<>();
            try {
                File cellpalace = new File("C:/Users/Simone/Desktop/palace.json");
                FileReader readpalace = new FileReader(cellpalace.getAbsolutePath());
                jpalace = Json.parse(readpalace).asObject();
                cell.setDice(jpalace.getInt("dice", 1));

                favor.setTipo("PalaceFavor");
                favor.setQuantity(jpalace.getInt("PalaceFavor", 1));
                reward.add(0, (Risorsa) favor.clone());
                coin.setTipo("Coins");
                coin.setQuantity(jpalace.getInt("Coins", 1));
                reward.add(1, (Risorsa) coin.clone());

                cell.setBonus(reward);


            } catch (IOException e) {
                //TODO
            }
             this.councilpalace.add(i,cell);

        return this.councilpalace.get(i);

    }


    private List<CellAction> createMarket(int num){

        CellAction stand = new CellAction();
        List<CellAction> market = new ArrayList<>();
        List<CellAction> smallmarket = new ArrayList<>();
        JsonObject jstand;
        JsonArray arraystand;
        int i;
        List<Risorsa> ware;
        Risorsa coin = new Risorsa();
        Risorsa mp = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa palacefavor = new Risorsa();

        if(num==4) {
            try {
                File marketfile = new File("C:/Users/Simone/Desktop/market.json");
                FileReader readmarket = new FileReader(marketfile.getAbsolutePath());

                arraystand = Json.parse(readmarket).asArray();
                for (i = 0; i < arraystand.size(); i++) {
                    ware = new ArrayList<>();
                    jstand = arraystand.get(i).asObject();

                    coin.setTipo("Coins");
                    coin.setQuantity(jstand.getInt("Coins", 0));
                    ware.add(0, (Risorsa) coin.clone());
                    servant.setTipo("Servants");
                    servant.setQuantity(jstand.getInt("Servants", 0));
                    ware.add(1, (Risorsa) servant.clone());
                    mp.setTipo("MilitaryPoints");
                    mp.setQuantity(jstand.getInt("MilitaryPoints", 0));
                    ware.add(2, (Risorsa) mp.clone());
                    palacefavor.setTipo("PalaceFavor");
                    palacefavor.setQuantity(jstand.getInt("PalaceFavor", 0));
                    ware.add(3, (Risorsa) palacefavor.clone());

                    stand.setBonus(ware);
                    stand.setDice(jstand.getInt("dice", 1));
                    market.add(i, (CellAction) stand.clone());


                }
            } catch (IOException e) {
                e.printStackTrace();//TODO
            }

            return market;
        }else{
            try {
                File smarketfile = new File("C:/Users/Simone/Desktop/smallmarket.json");
                FileReader readsmarket = new FileReader(smarketfile.getAbsolutePath());

                arraystand = Json.parse(readsmarket).asArray();
                for (i = 0; i < arraystand.size(); i++) {
                    ware = new ArrayList<>();
                    jstand = arraystand.get(i).asObject();

                    coin.setTipo("Coins");
                    coin.setQuantity(jstand.getInt("Coins", 0));
                    ware.add(0, (Risorsa) coin.clone());
                    servant.setTipo("Servants");
                    servant.setQuantity(jstand.getInt("Servants", 0));
                    ware.add(1, (Risorsa) servant.clone());

                    stand.setBonus(ware);
                    stand.setDice(jstand.getInt("dice", 1));
                    smallmarket.add(i, (CellAction) stand.clone());


                }
            } catch (IOException e) {
                e.printStackTrace();//TODO
            }

            return smallmarket;
        }

    }

    private Tower creategreenTower(){
        Tower tower = new Tower();
        JsonObject jterritory;
        JsonArray arrayterritory;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File greenfile = new File("C:/Users/Simone/Desktop/greenTower.json");
            FileReader readgreen = new FileReader(greenfile.getAbsolutePath());
            arrayterritory = Json.parse(readgreen).asArray();
            for(i=0;i<arrayterritory.size();i++){

                jterritory = arrayterritory.get(i).asObject();
                bonus.setTipo(jterritory.getString("type","Woods"));
                bonus.setQuantity(jterritory.getInt("quantity",0));
                singlecell.setBonus((Risorsa) bonus.clone());
                singlecell.setDice(jterritory.get("dice").asInt());
                floors.add(i, (CellTower) singlecell.clone());//dal basso verso l'alto
            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        tower.setFloors(floors);

        return tower;
    }

    private Tower createblueTower(){
        Tower tower = new Tower();
        JsonObject jcharacter;
        JsonArray arraycharacter;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File bluefile = new File("C:/Users/Simone/Desktop/blueTower.json");
            FileReader readblue = new FileReader(bluefile.getAbsolutePath());
            arraycharacter = Json.parse(readblue).asArray();
            for(i=0;i<arraycharacter.size();i++){

                jcharacter = arraycharacter.get(i).asObject();
                bonus.setTipo(jcharacter.getString("type","Stones"));
                bonus.setQuantity(jcharacter.getInt("quantity",0));
                singlecell.setBonus((Risorsa) bonus.clone());//dall'alto verso il basso
                singlecell.setDice(jcharacter.get("dice").asInt());
                floors.add(i, (CellTower) singlecell.clone());
            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        tower.setFloors(floors);
        return tower;
    }

    private Tower createyellowTower(){
        Tower tower = new Tower();
        JsonObject jbuilding;
        JsonArray arraybuilding;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File yellowfile = new File("C:/Users/Simone/Desktop/yellowTower.json");
            FileReader readyellow = new FileReader(yellowfile.getAbsolutePath());
            arraybuilding = Json.parse(readyellow).asArray();
            for(i=0;i<arraybuilding.size();i++){

                jbuilding = arraybuilding.get(i).asObject();
                bonus.setTipo(jbuilding.getString("type","MilitaryPoints"));
                bonus.setQuantity(jbuilding.getInt("quantity",0));
                singlecell.setBonus((Risorsa) bonus.clone());//dall'alto verso il basso
                singlecell.setDice(jbuilding.get("dice").asInt());
                floors.add(i, (CellTower) singlecell.clone());
            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        tower.setFloors(floors);
        return tower;
    }

    private Tower createvioletTower(){
        Tower tower = new Tower();
        JsonObject jventure;
        JsonArray arrayventure;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File violetfile = new File("C:/Users/Simone/Desktop/violetTower.json");
            FileReader readviolet = new FileReader(violetfile.getAbsolutePath());
            arrayventure = Json.parse(readviolet).asArray();
            for(i=0;i<arrayventure.size();i++){

                jventure = arrayventure.get(i).asObject();
                bonus.setTipo(jventure.getString("type","Coins"));
                bonus.setQuantity(jventure.getInt("quantity",0));
                singlecell.setBonus((Risorsa) bonus.clone());//dall'alto verso il basso
                singlecell.setDice(jventure.get("dice").asInt());
                floors.add(i, (CellTower) singlecell.clone());
            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        tower.setFloors(floors);
        return tower;
    }


    public void addObserver(BoardObserver observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for(BoardObserver observer : observers){
            observer.update();
        }
    }

    /*public Tower setterritoriesTower(Deck cards){
        Tower tower = new Tower();
        for(CellTower floors : tower.getFloors()) {
            Card card = cards.drawfirstCard();
            tower.getFloors().get(i).setCarta(card);
        }

        return tower;
    }*/


    private List<CellFaithPoints> setFaithPointsTrack(){
        CellFaithPoints singlecell = new CellFaithPoints();
        List<CellFaithPoints> faithTrack = new ArrayList<>();
        JsonArray arrayfaith;
        JsonObject jfaith;


        int i;

        try{
            File faithfile = new File("C:Users/Simone/Desktop/faithTrack.json");
            FileReader readfaith = new FileReader(faithfile.getAbsolutePath());
            arrayfaith = Json.parse(readfaith).asArray();
            for(i=0;i<arrayfaith.size();i++){
                jfaith = arrayfaith.get(i).asObject();
                singlecell.setQuantity(jfaith.get("quantity").asInt());
                singlecell.setVictoryPoints(jfaith.get("victoryPoints").asInt());
                faithTrack.add(i, (CellFaithPoints) singlecell.clone());

            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }


    return faithTrack;
    }

    public Tower getBuildingsTower() {
        return buildingsTower;
    }

    public Tower getCharactersTower() {
        return charactersTower;
    }

    public Tower getTerritoriesTower() {
        return territoriesTower;
    }

    public Tower getVenturesTower() {
        return venturesTower;
    }

    public void getVari() {
        // TODO implement here
    }

    public List<CellFaithPoints> getFaithPoints() {
        return faithPoints;
    }

    public List getHarvest() {
        return harvest;
    }

    public List getProduction() {
        return production;
    }

    public List<CellAction> getMarket() {
        return market;
    }

    public List<CellAction> getCouncilpalace() {
        return councilpalace;
    }

    public List<ExcommunicationTiles> getCarteScomunica() {
        return carteScomunica;
    }

    public List<Integer> getDadi() {
        return dadi;
    }


    public Token[] getTokens(String color) {
        int i;
        Token[] token = new Token[4];
        for(i=0;i<this.tokens.size();i++){
            if(this.tokens.get(i)[0].getColor().equals(color)){
                token = this.tokens.get(i);
            }
        }
        return token;
    }

    public void setTokens(Token[] tokens){
        int i,j;
        for(i=0;i<this.tokens.size();i++){
            if(this.tokens.get(i)[0].getColor().equals(tokens[0].getColor())){ //Sto lavorando sui tokens del colore giusto
                for (j=0;j<this.tokens.get(i).length;j++)
                this.tokens.get(i)[j] = tokens[j];
            }
        }
        notifyAllObservers();

    }

    public void modifyOrdineTurno(){}


    public static Board getInstance(Player[] players) {
            if (instance == null)
                instance = new Board(players);
        return instance;
    }

}