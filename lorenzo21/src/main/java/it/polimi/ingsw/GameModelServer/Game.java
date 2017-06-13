package it.polimi.ingsw.GameModelServer;




import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import it.polimi.ingsw.ServerController.AbstractPlayer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Game {

    private HashMap<String, AbstractPlayer> players;
    private Board board;
    private List<PersonalBoard> personalboard;
    private int turn;
    private List<BonusTile> bonustiles;
    private GameStatus stato;


    public Game(HashMap<String, AbstractPlayer> players) {
        this.players = players;
        turn = 1;
        bonustiles = new ArrayList<BonusTile>();
        this.stato = stato;
    }

    //setting initial game

    public void setBoard() {
        this.board = Board.getInstance();
    }

    public void setPersonalboard(List<PersonalBoard> personalboard,List<Player> players){
        int i;
        for (i = 0; i<players.size(); i++){
            personalboard.add(i, new PersonalBoard());
            players.get(i).setPB(personalboard.get(i));
        }

    }

    public void setOrder(){}

    public void setLeaderCard(){}

    public void setDevelopementCard(Board board){}

    public void setBonustiles(List<Player> players){}

    public void setResources(List<Player> players){

    }


    //developement card parsing

    public List<CardFactory> parseCard(){

        int i,j;
        int k, l;

        JsonArray arraycard;
        JsonObject singleobject;
        JsonArray cost1;
        JsonArray cost2;
        JsonObject res1;
        JsonObject res2;
        DevelopementCard singlecard = new DevelopementCard();
        List<Risorsa> singleresource = new ArrayList<Risorsa>();
        Risorsa singres = new Risorsa();
        List<DevelopementCard> sincar = new ArrayList<DevelopementCard>(); //sincar is a list containing cards

        try{
            File file = new File("C:/Users/Simone/Desktop/carte.json");
            FileReader reading = new FileReader(file.getAbsolutePath());

            arraycard = Json.parse(reading).asArray();

            for(i=0; i<arraycard.size(); i++){

                singleobject = arraycard.get(i).asObject();

                singlecard.setname(singleobject.get("nome").asString());
                singlecard.setNumber(singleobject.get("number").asInt());

                singlecard.setPeriod(singleobject.get("period").asInt());
                singlecard.setCardtype(singleobject.get("type").asString());

                cost1 = singleobject.get("cost").asArray();
                for(j=0; j<cost1.size()-1; j++){
                    res1 = cost1.get(j).asObject();
                    singres.setTipo(res1.get("type").asString());
                    singres.setQuantity(res1.get("quantity").asInt());
                    singleresource.add(j,singres);
                }
                singlecard.setCost1(singleresource);

                cost2 = singleobject.get("cost2").asArray();
                for(k=0; k<cost2.size()-1; k++){
                    res2 = cost2.get(k).asObject();
                    singres.setTipo(res2.get("type").asString());
                    singres.setQuantity(res2.get("quantity").asInt());
                    singleresource.add(k,singres);
                }
                singlecard.setCost2(singleresource);
                singlecard.setImmediateEffect(singleobject.get("immediateeffect").asInt());
                singlecard.setPermanentEffect(singleobject.get("permanenteffect").asInt());
                sincar.add(i, (DevelopementCard) singlecard.clone());
            }



        } catch (IOException e) {
            // TODO ritornare al client l'errore
            e.printStackTrace(); //
        }


        return null;
    }

    //leader card parsing
    public List<LeaderCard> parseLeader(){
     JsonArray leaderarray;
     JsonObject leadercard;
     JsonObject req;
     JsonArray requiresarray;
     LeaderCard leader = new LeaderCard();
     Risorsa require = new Risorsa();
     List<Risorsa> requireslist = new ArrayList<Risorsa>();
     List<LeaderCard> leaderlist = new ArrayList<LeaderCard>();
     int i,j;
     try{
         File file = new File("C:\\Users\\Simone\\Desktop\\leader.json");
         FileReader read = new FileReader(file.getAbsolutePath());

         leaderarray = Json.parse(read).asArray();
         for(i = 0; i < leaderarray.size(); i++){

             leadercard = leaderarray.get(i).asObject();
             leader.setName(leadercard.get("nome").asString());
             leader.setEffetto(leadercard.get("effetto").asInt());

             requiresarray = leadercard.get("requisiti").asArray();
             for(j=0; j < requiresarray.size(); j++){
                 req = requiresarray.get(j).asObject();
                 // TODO continua da qui

             }

         }

     } catch (IOException e) {
         e.printStackTrace();
     }


     return leaderlist;
    }


    public Board getBoard(){
        return board;
    }

    public GameStatus getstatus(){
        return stato;
    }

    public int getturn(){
        return turn;
    }

    public HashMap<String, AbstractPlayer> getPlayers(){
        return players;
    }

    public List<BonusTile> getbonustiles(){
        return bonustiles;
    }

    public List<PersonalBoard> getPersonalBoard(){
        return personalboard;
    }
}