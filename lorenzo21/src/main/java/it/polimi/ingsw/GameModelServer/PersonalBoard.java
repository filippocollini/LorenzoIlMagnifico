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
public class PersonalBoard {

    private List<CellPB> territories;
    private List<CellPB> buildings;
    private List<CellPB> characters;
    private List<CellPB> ventures;
    private BonusTile bonustile;
    private List<Risorsa> risorse;



    public PersonalBoard() {
        territories = greenPBparsing();
        buildings = new ArrayList<>();
        characters = bluePBparsing();
        ventures = new ArrayList<>();
        bonustile = new BonusTile();
        risorse = new ArrayList<>();


    }


    public void setBonustile(BonusTile bonustile) {
        this.bonustile = bonustile;
    }

    public static List<CellPB> greenPBparsing(){

        CellPB cell = new CellPB();
        List<CellPB> listcell = new ArrayList<>();
        JsonArray arraycell;
        JsonObject jcell;
        int i;

        try{
            File filegreenPb = new File("lorenzo21/src/main/resources/greenPB.json");
            FileReader read = new FileReader(filegreenPb.getAbsolutePath());

            arraycell = Json.parse(read).asArray();
            for(i=0;i<arraycell.size();i++){
                jcell = arraycell.get(i).asObject();
                cell.setIndex(jcell.get("index").asInt());
                cell.setUnlockedcell(jcell.getBoolean("unlocked",false));
                cell.setMpNecessary(jcell.getInt("MPnecessary",0));
                cell.setVictoryPoints(jcell.getInt("VictoryPoints",0));

                listcell.add(i, (CellPB) cell.clone());
            }

        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return listcell;
    }

    public static List<CellPB> bluePBparsing(){

        CellPB cell = new CellPB();
        List<CellPB> listcell = new ArrayList<>();
        JsonArray array;
        JsonObject jcell;
        int i;

        try{
            File filebluePb = new File("lorenzo21/src/main/resources/bluePb.json");
            FileReader read = new FileReader(filebluePb.getAbsolutePath());

            array = Json.parse(read).asArray();
            for(i=0;i<array.size();i++){
                jcell = array.get(i).asObject();
                cell.setIndex(jcell.get("index").asInt());
                cell.setVictoryPoints(jcell.getInt("VictoryPoints",0));

                listcell.add(i,(CellPB) cell.clone());

            }
        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

    return listcell;
    }

   public void addCard(DevelopementCard card){
        if(card.getCardtype().equals("territory") ){
           addTCard(card);
       }else if(card.getCardtype().equals("buildings")){
            addBCard(card);
       }else if(card.getCardtype().equals("ventures")){
           addVCard(card);
       }else
           addCCard(card);
   }

    public void addTCard(DevelopementCard card){

        int i = 0;

        while(territories.get(i).getCard() != null && territories.get(i).getIndex()<6) //ricerca prima cella libera
            i++;

             territories.get(i).setCard(card);
        //TODO non so se ci saranno errori per nullpointerexception
        // se si punta una cella vuota
    }

    public void addBCard(DevelopementCard card){
        int number;
        CellPB cell = new CellPB();
        number = buildings.size();
        if(number<6){
            cell.setCard(card);
            buildings.add(number,cell);
        }else
            System.out.println("non puoi avere altre carte");


        //TODO per le buildingcard non serve il try catch quando si addano carte
    }

    public void addVCard(DevelopementCard card){
        int number;
        CellPB cell = new CellPB();
        number = ventures.size();
        if(number<6){
            cell.setCard(card);
            ventures.add(number,cell);
        }else
            System.out.println("non puoi avere altre carte");

        //TODO per le venturecard non serve il try catch quando si addano carte
    }

    public void addCCard(DevelopementCard card){

        int i = 0;

            while(characters.get(i).getCard() != null) //ricerca prima cella libera
                i++;

         characters.get(i).setCard(card);
        //TODO quando si addano carte (dal main) fare un try catch

        //TODO nullpointerException se si punta una cella vuota
    }



    public List<CellPB> getterritories() {
        return territories;
    }

    public List<CellPB> getbuildings(){
        return buildings;
    }

    public List<CellPB> getventures(){
        return ventures;
    }

    public List<CellPB> getcharacters(){
        return characters;
    }

    public List<CellPB> gettypecards(String type){
        if(type.equals("territory"))
            return territories;
        else if(type.equals("buildings"))
            return buildings;
        else if (type.equals("ventures"))
            return ventures;
        else if(type.equals("characters"))
            return characters;
        return null;
    }

    public BonusTile gettesserabonus(){
        return bonustile;
    }

    public List<Risorsa> getresources(){
        return risorse;
    }

    public Risorsa getsingleresource(String type){
        Risorsa resource = new Risorsa();
        for(Risorsa single : risorse){
            if (single.gettipo().equals(type))
                resource=single;
        }
        return resource;
    }

    public StringBuilder showPB(){
        StringBuilder showpb = new StringBuilder();

        showpb.append("GREENCARDS");
        for(CellPB cell : territories){
            if(cell.getUnlockedcell()){
                showpb.append("In the cell number ");
                showpb.append(cell.getIndex());
                showpb.append(" you have : ");
                showpb.append("\n");
                showpb.append("Card name : ");
                showpb.append(cell.getCard().getName());
                showpb.append("\n");
                showpb.append("End game VP gained :");
                showpb.append(cell.getVictoryPoints());

            }else {
                showpb.append("Cell is locked");
                showpb.append("\n");
                showpb.append("You need ");
                showpb.append(cell.getMpNecessary());
                showpb.append(" Military Points to unlock this cell\n");
            }
        }
        showpb.append("\n");
        showpb.append("YELLOWCARDS");
        for(CellPB cell : buildings) {
            showpb.append("In the cell number ");
            showpb.append(cell.getIndex());
            showpb.append(" you have : ");
            showpb.append("\n");
            showpb.append("Card name : ");
            showpb.append(cell.getCard().getName());
            showpb.append("\n");

        }
        showpb.append("\n");

        showpb.append("BLUECARDS");
        for(CellPB cell : characters) {
            showpb.append("In the cell number ");
            showpb.append(cell.getIndex());
            showpb.append(" you have : ");
            showpb.append("\n");
            showpb.append("Card name : ");
            showpb.append(cell.getCard().getName());
            showpb.append("\n");
            showpb.append("End game VP gained :");
            showpb.append(cell.getVictoryPoints());
        }
        showpb.append("\n");


        showpb.append("VIOLETCARDS");
        for(CellPB cell : ventures) {
            showpb.append("In the cell number ");
            showpb.append(cell.getIndex());
            showpb.append(" you have : ");
            showpb.append("\n");
            showpb.append("Card name : ");
            showpb.append(cell.getCard().getName());
            showpb.append("\n");

        }
        showpb.append("\n");

        //RESOURCES
        showpb.append("Resources : ");
        for(Risorsa res : risorse){
            showpb.append("You have ");
            showpb.append(res.getquantity());
            showpb.append(" ");
            showpb.append(res.gettipo());
            showpb.append("            \n");
        }
        showpb.append("\n");

        return showpb;
    }

}