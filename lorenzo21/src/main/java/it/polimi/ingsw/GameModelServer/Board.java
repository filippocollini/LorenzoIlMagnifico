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


    private List<CellAction> production;
    private List<CellAction> harvest;
    private List<CellAction> market;
    private List<ExcommunicationTiles> excommtiles;
    private List<CellAction> councilpalace;
    private List<Dices> dices;
    private static Board instance = null;
    private Tower territoriesTower;
    private Tower buildingsTower;
    private Tower charactersTower;
    private Tower venturesTower;
    private List<Token[]> tokens;
    private List<Tower> towers;
    private List<BoardObserver> observers;


    private Board(int nplayers){
        territoriesTower = creategreenTower();
        buildingsTower = createyellowTower();
        charactersTower = createblueTower();
        venturesTower = createvioletTower();
        excommtiles = new ArrayList<>();
        faithPoints = setFaithPointsTrack();
        observers = new ArrayList<>();
        market = createMarket(nplayers);
        councilpalace = new ArrayList<>();
        harvest = new ArrayList<>();
        production = new ArrayList<>();
        tokens = inizializationTokens(nplayers);
        notifyAllObservers();
        towers = addingtowers();
        dices = creatingDices();


    }

    public List<Tower> addingtowers(){
        List<Tower> tower = new ArrayList<>();
        tower.add(territoriesTower);
        tower.add(charactersTower);
        tower.add(buildingsTower);
        tower.add(venturesTower);

        return tower;
    }

    private List<Token[]> inizializationTokens(int nplayers) {
        int i;
        List<Token[]> tokens = new ArrayList<>();
        List<String> previouscolor = new ArrayList<>();
        for(i=0;i<nplayers;i++) {
            String color = Game.randomcolor(previouscolor);
            previouscolor.add(color);
            Token[] token = new Token[3];
            token[0] = new Token(color);

            token[0].setType("VictoryPoints");
            token[0].setPosition(0);
            token[1] = new Token(color);
            token[1].setType("MilitaryPoints");
            token[1].setPosition(0);
            token[2] = new Token(color);
            token[2].setType("FaithPoints");
            token[2].setPosition(0);
            /*token[3] = new Token(color);
            token[3].setType("Order");*/
            tokens.add(i, token);



        }

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
                e.printStackTrace();
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

    public List<ExcommunicationTiles> getExcommTiles() {
        return excommtiles;
    }

    public List<Dices> getDices() {
        return dices;
    }




    public Token[] getTokens(String colorplayer) {
        int i;
        Token[] token = new Token[3];
        for(i=0;i<this.tokens.size();i++){
            if(this.tokens.get(i)[0].getColor().equals(colorplayer)){

                return this.tokens.get(i);
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

    public List<Tower> getTowers() {
        return towers;
    }

    public static Board getInstance(int nplayers) {
            if (instance == null)
                instance = new Board(nplayers);
        return instance;
    }

    public StringBuilder showBoard(){
        StringBuilder showboard = new StringBuilder();

        //TOWERS
        for(Tower tower : towers) {
            showboard.append(tower.getType());
            showboard.append("\n");
            for (CellTower floor : tower.getFloors()) {
                showboard.append("Floor : ");
                showboard.append(floor.getDice());
                showboard.append("\n");
                showboard.append("Cell Bonus : ");
                showboard.append(floor.getResourceBonus().getquantity());
                showboard.append(" ");
                showboard.append(floor.getResourceBonus().gettipo());
                showboard.append("\n");
                if(floor.isfMPresent()) {
                    showboard.append("There is the ");
                    showboard.append(floor.getFmOnIt().getColor());
                    showboard.append(" member of the player ");
                    showboard.append(floor.getFmOnIt().getColorplayer());
                }else
                    showboard.append("Free Cell");
                showboard.append("\n");
                showboard.append("Card\n");
                showboard.append("Name : ");
                showboard.append(floor.getCard().getName());
                showboard.append("\n");
                if (floor.getCard().getChoice()) {
                    showboard.append("You can pay with the Military Points or with the resources\n");
                }
                showboard.append("Cost : ");
                for (Risorsa cost : floor.getCard().getCost1()) {
                    showboard.append(cost.getquantity());
                    showboard.append(" ");
                    showboard.append(cost.gettipo());
                    showboard.append("       \n");
                }
                showboard.append("\n");
                showboard.append("Immediate Effect id : ");
                for (int id : floor.getCard().getImmediateeffect()) {
                    showboard.append(id);
                    showboard.append(" ; ");
                }
                showboard.append("\n");
                if (floor.getCard().getPermchoice()) {
                    showboard.append("You can choose to activate one of these effects\n");
                }
                showboard.append("Permanent Effect id : ");
                for (int id : floor.getCard().getPermanenteffect()) {
                    showboard.append(id);
                    showboard.append(" ; ");
                }

            }
        }
        showboard.append("\n");
        showboard.append("\n");

        //PALACE
        if(councilpalace.size()!=0) {
            for (CellAction cell : councilpalace) {
                showboard.append("Bonus : ");
                for(Risorsa bonus : cell.getBonus()) {
                    showboard.append(bonus.getquantity());
                    showboard.append(" ");
                    showboard.append(bonus.gettipo());
                    showboard.append("       \n");
                }
                showboard.append("\n");
                showboard.append("Dice : ");
                showboard.append(cell.getDice());
                showboard.append("\n");
                showboard.append("FM present : ");
                showboard.append(cell.getMember().getColorplayer());
                showboard.append("\n");
            }
        }else{
            showboard.append("CouncilPalace empty\n");
        }
        showboard.append("\n");

        //TOKENS
        for(Token[] token : tokens){
            showboard.append(token[0].getColor());
            showboard.append(" player position on Board\n");
            for(Token single : token){
                showboard.append(single.getType());
                showboard.append(" : ");
                showboard.append(single.getPosition());
                showboard.append("\n");
            }
        }
        showboard.append("\n");

        //PRODUCTION
        if(production.size()!=0) {
            for(CellAction cell : production){
                showboard.append("Malus on dice : ");
                showboard.append(cell.getDice());
                showboard.append("\n");
                showboard.append("There is the ");
                showboard.append(cell.getMember().getColor());
                showboard.append(" of the ");
                showboard.append(cell.getMember().getColorplayer());
                showboard.append(" player\n");

            }
        }
        showboard.append("\n");

        //HARVEST
        if(harvest.size()!=0) {
            for(CellAction cell : harvest){
                showboard.append("Malus on dice : ");
                showboard.append(cell.getDice());
                showboard.append("\n");
                showboard.append("There is the ");
                showboard.append(cell.getMember().getColor());
                showboard.append(" of the ");
                showboard.append(cell.getMember().getColorplayer());
                showboard.append(" player\n");
            }
        }
        showboard.append("\n");

        //MARKET
        for(CellAction cell : market){
            for(Risorsa bonus : cell.getBonus()) {
                showboard.append("Bonus : ");
                showboard.append(bonus.getquantity());
                showboard.append(" ");
                showboard.append(bonus.gettipo());
                showboard.append("        \n");
            }
            showboard.append("\n");
            if(cell.isfMOn()) {
                showboard.append("There is the ");
                showboard.append(cell.getMember().getColor());
                showboard.append(" of the ");
                showboard.append(cell.getMember().getColorplayer());
                showboard.append(" player\n");
            }else
                showboard.append("Free Cell");

        }
        showboard.append("\n");

        //DICES
        for(Dices dice : dices){
            showboard.append(dice.getColor());
            showboard.append(" dice has value : ");
            showboard.append(dice.getValue());
        }
        showboard.append("\n");

        //EXCOMMUNICATIONS
        for(ExcommunicationTiles tile : excommtiles){
            showboard.append("Period of the Excommunication : ");
            showboard.append(tile.getPeriod());
            showboard.append("\n");
            showboard.append("The excommunication is : ");
            showboard.append(tile.getId());
        }
        showboard.append("\n");

        return showboard;
    }

}