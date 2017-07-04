package it.polimi.ingsw.GameModelServer;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExcommunicationTiles extends Card implements Cloneable{

    /**
     * Default constructor
     */
    public ExcommunicationTiles() {
        reduction = reductionParsing();
        covering = coverMarketParsing();
        twoserv = twoServantsParsing();
        skipping = skipActionParsing();
        lessress = lessResourcesParsing();
        notVP = notVPparsing();
        losingVP = lostVPparsing();
    }

    @Override
    public EffectStrategy activateEffect(int id) {
        return null;
    }

    private int id;
    private int periodo;
    private int effect;
    private List<ExcommunicationReduction> reduction;
    private ExcommunicationCoverMarket covering;
    private ExcommunicationServants twoserv;
    private ExcommunicationSkipAction skipping;
    private List<ExcommunicationLessResources> lessress;
    private List<ExcommunicationEndVP> notVP;
    private List<ExcommunicationLostVP> losingVP;
    Player player;


    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPeriod(int periodo) {
        this.periodo = periodo;
    }

    public void setPlayer(Player player) {//si fa un ciclo, il player si setta solo se si becca la scomunica e poi si
        this.player = player;               // sovrascrive con gli altri player che devono essere scomunicati
    }

    public int getPeriod(){
        return periodo;
    }

    public int getId() {
        return id;
    }

    public int getEffect(){
        return effect;
    }

    //EXCOMMUNICATION PARSING
    public List<ExcommunicationReduction> reductionParsing(){

        int i;
        ExcommunicationReduction effect = new ExcommunicationReduction();
        List<ExcommunicationReduction> listeffect = new ArrayList<>();
        JsonArray arrayreduction;
        JsonObject jreduction;

        try{
            File reductionfile = new File("C:/Users/Simone/Desktop/effetti/scomunicaboost.json");
            FileReader readreduction = new FileReader(reductionfile.getAbsolutePath());

            arrayreduction = Json.parse(readreduction).asArray();
            for(i=0; i<arrayreduction.size();i++){
                jreduction = arrayreduction.get(i).asObject();
                effect.setPeriod(jreduction.get("period").asInt());
                effect.setId(jreduction.get("id").asInt());
                effect.setType(jreduction.get("type").asString());
                effect.setDice(jreduction.get("dice").asInt());
                listeffect.add(i, (ExcommunicationReduction) effect.clone());

            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return listeffect;
    }

    public ExcommunicationCoverMarket coverMarketParsing(){
        ExcommunicationCoverMarket covering = new ExcommunicationCoverMarket();
        JsonObject jcovering;

        try{
            File file = new File("C:/Users/Simone/Desktop/effetti/scomunicaCoverMarket.json");
            FileReader readfile = new FileReader(file.getAbsolutePath());
            jcovering = Json.parse(readfile).asObject();
            covering.setPeriod(jcovering.get("period").asInt());
            covering.setId(jcovering.get("id").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return covering;
    }

    public ExcommunicationServants twoServantsParsing(){
        ExcommunicationServants twoserv = new ExcommunicationServants();
        JsonObject jservant;

        try{
            File fileserv = new File("C:/Users/Simone/Desktop/effetti/scomunicaTwoServants.json");
            FileReader readserv = new FileReader(fileserv.getAbsolutePath());
            jservant = Json.parse(readserv).asObject();
            twoserv.setId(jservant.get("id").asInt());
            twoserv.setPeriod(jservant.get("period").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return twoserv;
    }

    public ExcommunicationSkipAction skipActionParsing(){
        ExcommunicationSkipAction skip = new ExcommunicationSkipAction();
        JsonObject jskip;

        try{
            File fileskip = new File("C:/Users/Simone/Desktop/effetti/scomunicaSkipfirstAction.json");
            FileReader readskip = new FileReader(fileskip.getAbsolutePath());
            jskip = Json.parse(readskip).asObject();
            skip.setId(jskip.get("id").asInt());
            skip.setPeriod(jskip.get("period").asInt());
        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return skip;
    }

    public List<ExcommunicationLessResources> lessResourcesParsing(){
        ExcommunicationLessResources effectless = new ExcommunicationLessResources();
        List<ExcommunicationLessResources> listeffectless = new ArrayList<>();
        JsonArray arrayless;
        JsonObject jless;
        int i;

        try{
            File fileless = new File("C:/Users/Simone/Desktop/effetti/scomunicalessresources.json");
            FileReader readless = new FileReader(fileless.getAbsolutePath());

            arrayless = Json.parse(readless).asArray();
            for(i=0; i<arrayless.size();i++){
                jless = arrayless.get(i).asObject();
                effectless.setId(jless.get("id").asInt());
                effectless.setPeriod(jless.get("period").asInt());
                effectless.setType(jless.get("type").asString());
                listeffectless.add(i, (ExcommunicationLessResources) effectless.clone());
            }

        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        return listeffectless;
    }

    public List<ExcommunicationEndVP> notVPparsing(){
        ExcommunicationEndVP endvp = new ExcommunicationEndVP();
        List<ExcommunicationEndVP> listendvp = new ArrayList<>();
        int i;
        JsonObject jendvp;
        JsonArray arrayendvp;

        try{
            File filend = new File("C:/Users/Simone/Desktop/effetti/scomunicanoendVP.json");
            FileReader readend = new FileReader(filend.getAbsolutePath());

            arrayendvp = Json.parse(readend).asArray();
            for(i=0; i<arrayendvp.size();i++){
                jendvp = arrayendvp.get(i).asObject();
                endvp.setId(jendvp.get("id").asInt());
                endvp.setPeriod(jendvp.get("period").asInt());
                endvp.setType(jendvp.get("type").asString());

                listendvp.add(i, (ExcommunicationEndVP) endvp.clone());
            }

        }catch (IOException e){
            e.printStackTrace();//TODO
        }

        return listendvp;
    }

    public List<ExcommunicationLostVP> lostVPparsing(){
        ExcommunicationLostVP lostvp = new ExcommunicationLostVP();
        List<ExcommunicationLostVP> listlostvp = new ArrayList<>();
        JsonArray arraylost;
        JsonObject jlost;
        int i;
        try{
            File filelost = new File("C:/Users/Simone/Desktop/effetti/scomunicaendVPlost.json");
            FileReader readlost = new FileReader(filelost.getAbsolutePath());
            arraylost = Json.parse(readlost).asArray();
            for(i=0;i<arraylost.size(); i++){
                jlost = arraylost.get(i).asObject();
                lostvp.setId(jlost.get("id").asInt());
                lostvp.setPeriod(jlost.get("period").asInt());
                lostvp.setQuantity(jlost.get("quantity").asInt());
                lostvp.setType(jlost.get("type").asString());
                listlostvp.add(i, (ExcommunicationLostVP) lostvp.clone());
            }

        }catch(IOException e){
            e.printStackTrace();//TODO
        }


        return listlostvp;
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //TODO
        }
        return null;
    }



}