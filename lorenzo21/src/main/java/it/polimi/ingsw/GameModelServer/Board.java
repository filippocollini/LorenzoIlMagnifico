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



    private List<CellFaithPoints> faithPoints;

    private List<Player> ordineTurno;
    private List<CellAction> production;
    private List<CellAction> harvest;
    private List<CellAction> market;
    private List<ExcommunicationTiles> carteScomunica;
    private List<CellAction> councilpalace;
    private List<Dices> dices;
    private static Board instance;
    private Tower territoriesTower;
    private Tower buildingsTower;
    private Tower charactersTower;
    private Tower venturesTower;
    private List<Token[]> tokens;

    private List<BoardObserver> observers;


    private Board(int nplayers){
        territoriesTower = creategreenTower();
        buildingsTower = createyellowTower();
        charactersTower = createblueTower();
        venturesTower = createvioletTower();

        faithPoints = setFaithPointsTrack();
        observers = new ArrayList<>();
        market = createMarket(nplayers);
        councilpalace = new ArrayList<>();
        harvest = new ArrayList<>();
        production = new ArrayList<>();
        tokens = inizializationTokens(nplayers);

        dices = creatingDices();


    }



    private List<Token[]> inizializationTokens(int nplayers) {
        int i;
        List<Token[]> tokens = new ArrayList<>();
        List<String> previouscolor = new ArrayList<>();
        for(i=0;i<nplayers;i++) {
            String color = Game.randomcolor(previouscolor);
            previouscolor.add(color);
            Token[] token = new Token[4];
            token[0] = new Token(color);
            token[0].setType("VictoryPoints");
            token[0].setPosition(0);
            token[1] = new Token(color);
            token[1].setType("MilitaryPoints");
            token[1].setPosition(0);
            token[2] = new Token(color);
            token[2].setType("FaithPoints");
            token[2].setPosition(0);
            token[3] = new Token(color);
            token[3].setType("Order");
            tokens.add(i, token);
        }
        notifyAllObservers();
        return tokens;

    }



    public CellAction createCellPalace(){


            JsonObject jpalace;
            CellAction cell = new CellAction();
            Risorsa favor = new Risorsa();
            Risorsa coin = new Risorsa();
            List<Risorsa> reward = new ArrayList<>();
            try {
                File cellpalace = new File("lorenzo21/src/main/resources/palace.json");
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


        return cell;

    }

    public CellAction createCellHarvestorPoduction(){
        CellAction cell = new CellAction();
        cell.setDice(0);
        return cell;
    }

    public List<Dices> creatingDices(){
        List<Dices> dices = new ArrayList<>();
        Dices orange = new Dices("Orange");
        Dices black = new Dices("Black");
        Dices white = new Dices("White");

        dices.add(orange);
        dices.add(black);
        dices.add(white);

        return dices;
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
                File marketfile = new File("lorenzo21/src/main/resources/market.json");
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
                    stand.setType(jstand.get("type").asString());
                    stand.setDice(jstand.getInt("dice", 1));
                    market.add(i, (CellAction) stand.clone());


                }
            } catch (IOException e) {
                e.printStackTrace();//TODO
            }

            return market;
        }else{
            try {
                File smarketfile = new File("lorenzo21/src/main/resources/smallMarket.json");
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
                    stand.setType(jstand.get("type").asString());
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
        Tower tower = new Tower("territory");
        JsonObject jterritory;
        JsonArray arrayterritory;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File greenfile = new File("lorenzo21/src/main/resources/towersbonus/greenTower.json");
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
        Tower tower = new Tower("characters");
        JsonObject jcharacter;
        JsonArray arraycharacter;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File bluefile = new File("lorenzo21/src/main/resources/towersbonus/blueTower.json");
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
        Tower tower = new Tower("buildings");
        JsonObject jbuilding;
        JsonArray arraybuilding;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File yellowfile = new File("lorenzo21/src/main/resources/towersbonus/yellowTower.json");
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
        Tower tower = new Tower("ventures");
        JsonObject jventure;
        JsonArray arrayventure;
        CellTower singlecell = new CellTower();
        List<CellTower> floors = new ArrayList<>();
        Risorsa bonus = new Risorsa();
        int i;

        try{
            File violetfile = new File("lorenzo21/src/main/resources/towersbonus/violetTower.json");
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
            File faithfile = new File("lorenzo21/src/main/resources/faithTrack.json");
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

    public Tower getTower(String type){
        Tower tower;
        if(type.equals("territory"))
            tower = territoriesTower;
        else if(type.equals("characters"))
            tower = charactersTower;
        else if(type.equals("ventures"))
            tower = venturesTower;
        else
            tower = buildingsTower;
        return tower;
    }

    public List<CellFaithPoints> getFaithPoints() {
        return faithPoints;
    }

    public List<CellAction> getHarvest() {
        return harvest;
    }

    public List<CellAction> getProduction() {
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

    public List<Dices> getDices() {
        return dices;
    }




    public Token[] getTokens(String colorplayer) {
        int i;
        Token[] token = new Token[4];
        for(i=0;i<this.tokens.size();i++){
            if(this.tokens.get(i)[0].getColor().equals(colorplayer)){
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

    public void setDices(List<Dices> dices){
        this.dices = dices;
        System.out.println(dices.get(0).getValue());
        System.out.println(dices.get(1).getValue());
        System.out.println(dices.get(2).getValue());

        notifyAllObservers();
    }

    public void modifyOrdineTurno(){}


    public static Board getInstance(int nplayers) {
            if (instance == null)
                instance = new Board(nplayers);
        return instance;
    }

}