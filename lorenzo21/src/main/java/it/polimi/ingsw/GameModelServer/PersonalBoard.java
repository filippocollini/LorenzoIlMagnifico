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
        if(card.getCardtype().equals("cards/territory") ){
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

        if(territories.get(i).getUnlockedcell())
             territories.get(i).setCard(card);
        // (else) TODO ritorno errore 'non puoi avere altre carte, devi sbloccare celle con MP'

        //TODO error nullpointerException se si guarda una cella vuota
        //TODO try catch nel main quando si addano carte
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
        if(type.equals("TerritoryCards"))
            return territories;
        else if(type.equals("BuildingCards"))
            return buildings;
        else if (type.equals("VentureCards"))
            return ventures;
        else if(type.equals("CharacterCards"))
            return characters;
        return null; //TODO avviso dato passato errato
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



}