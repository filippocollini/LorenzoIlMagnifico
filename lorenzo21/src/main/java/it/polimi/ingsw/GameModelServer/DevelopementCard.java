package it.polimi.ingsw.GameModelServer;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DevelopementCard extends Card implements Cloneable{


    protected String name;
    protected int number;
    protected int period;
    protected String cardtype;
    protected boolean permchoice;

    private List<Risorsa> listcost;
    private boolean costchoice;
    protected List<Integer> immediateeffect;
    protected List<Integer> permanenteffect;
    protected List<GetResources> getres;
    protected List<GetFreeAction> freeaction;
    protected List<GetFreeandDiscount> discountandfree;
    protected List<GetResourcesSelling> selling;
    protected List<GetResourcesIf> resif;
    protected List<GetBoostDice> boost;
    protected List<GetForEach> resfor;
    protected List<GetVPEnd> getvp;


    protected List<GetBoostandDiscount> discountandboost;

    public DevelopementCard() {
        immediateeffect = new ArrayList<>();
        permanenteffect = new ArrayList<>();

        listcost = new ArrayList<>();
        getres = getResourcesparsing();
        freeaction = freeactionparse();
        discountandboost = boostdiscountparse();
        discountandfree = freediscountparse();
        selling = sellingparse();
        resif = ifparsing();
        boost = getboostparsing();
        resfor = forEachparsing();
        getvp = getVPparsing();
    }

    @Override
    public EffectStrategy activateEffect(int id) {
        EffectStrategy righteffect = null;
        if(id == 0)
            return righteffect;
        for(GetResources effect : getres){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetFreeAction effect : freeaction){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetFreeandDiscount effect : discountandfree){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostandDiscount effect : discountandboost){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for (GetResourcesSelling effect : selling){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetResourcesIf effect : resif){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetBoostDice effect : boost){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetForEach effect : resfor){
            if(effect.getId() == id)
                righteffect = effect;
        }
        for(GetVPEnd effect : getvp){
            if(effect.getId() == id)
                righteffect = effect;
        }
        return righteffect;
    }


    public String getName(){
        return name;
    }


    public List<Risorsa> getCost1(){
        return listcost;
    }



    public void setCost1(List<Risorsa> cost1){
        this.listcost = cost1;
    }

    public void setChoice(boolean costchoice) {
        this.costchoice = costchoice;
    }

    public boolean getChoice(){
        return costchoice;
    }

    public int getNumber(){
        return number;
    }

    public int getPeriod(){
        return period;
    }

    public String getCardtype(){
        return cardtype;
    }

    public boolean getPermchoice(){
        return permchoice;
    }

    public List<Integer> getImmediateeffect(){
        return immediateeffect;
    }

    public List<Integer> getPermanenteffect(){
        return permanenteffect;
    }

    public List<GetBoostDice> getBoost() {
        return boost;
    }

    public List<GetFreeandDiscount> getDiscountandFree() {
        return discountandfree;
    }

    public List<GetBoostandDiscount> getDiscountandboost() {
        return discountandboost;
    }

    public List<GetForEach> getResfor() {
        return resfor;
    }

    public List<GetFreeAction> getFreeaction() {
        return freeaction;
    }

    public List<GetResources> getGetres() {
        return getres;
    }

    public List<GetResourcesIf> getResif() {
        return resif;
    }

    public List<GetResourcesSelling> getSelling() {
        return selling;
    }

    public List<GetVPEnd> getGetvp() {
        return getvp;
    }

    //TODO effetto non puoi ricevere risorse dagli spazi azione sulle torri


    public void setname(String name){
        this.name = name;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setPeriod(int period){
        this.period = period;
    }

    public void setCardtype(String cardtype){
        this.cardtype = cardtype;
    }

    public void setPermchoice(boolean permchoice) {
        this.permchoice = permchoice;
    }

    public void setImmediateEffect(List<Integer> immediateeffect){
        this.immediateeffect = immediateeffect;
    }

    public void setPermanentEffect(List<Integer> permanenteffect){
        this.permanenteffect = permanenteffect;
    }

    //parsing 'getfreeaction' effects
    public List<GetFreeAction> freeactionparse(){
        int i;
        List <GetFreeAction> freeActionlist = new ArrayList<>();
        EffectStrategy singleeffect = new GetFreeAction();
        JsonArray arrayfree;
        JsonObject jfree;
        try {
            File filefree = new File("lorenzo21/src/main/resources/effects/getfreeAction.json");
            FileReader readfree = new FileReader(filefree.getAbsolutePath());

            arrayfree = Json.parse(readfree).asArray();
            for (i = 0; i < arrayfree.size(); i++) {
                jfree = arrayfree.get(i).asObject();
                singleeffect.setId(jfree.get("id").asInt());
                singleeffect.setDicepower(jfree.get("dice").asInt());
                singleeffect.setTypecard(jfree.get("type").asString());
                freeActionlist.add(i, (GetFreeAction) singleeffect.clone());


            }
        }catch(IOException e){
            e.printStackTrace(); // TODO
        }
        return freeActionlist;
    }

    //parsing 'getboostanddiscount' effects
    public List<GetBoostandDiscount> boostdiscountparse(){

        int i;
        List<GetBoostandDiscount> discountboostlist = new ArrayList<>();
        EffectStrategy singlediscountboost = new GetBoostandDiscount();
        JsonArray arraydiscount;
        JsonObject jdiscount;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();



        try {
            File discfile = new File("lorenzo21/src/main/resources/effects/getBoostandDiscount.json");
            FileReader read = new FileReader(discfile.getAbsolutePath());

            arraydiscount = Json.parse(read).asArray();
            for(i=0; i<arraydiscount.size();i++){
                List<Risorsa> discounts = new ArrayList<>();
                jdiscount = arraydiscount.get(i).asObject();
                singlediscountboost.setId(jdiscount.get("id").asInt());
                singlediscountboost.setSelect(jdiscount.getBoolean("choice", false));
                singlediscountboost.setDiceboost(jdiscount.getInt("dice",2));

                //RESOURCES
                coin.setTipo("Coins");
                coin.setQuantity(jdiscount.getInt("Coins",0));
                discounts.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jdiscount.getInt("Woods",0));
                discounts.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jdiscount.getInt("Stones", 0));
                discounts.add(2, (Risorsa) stone.clone());


                singlediscountboost.setDiscount(discounts);

                singlediscountboost.setTypecard(jdiscount.get("type").asString());

                discountboostlist.add(i, (GetBoostandDiscount) singlediscountboost.clone());

            }
        }catch(IOException e){
            e.printStackTrace(); // TODO
        }


        return discountboostlist;
    }

    //parsing 'getfreeanddiscount' effects
    public List<GetFreeandDiscount> freediscountparse(){
        int i;
        List<GetFreeandDiscount> discountfreelist = new ArrayList<>();
        EffectStrategy singlediscountfree = new GetFreeandDiscount();
        JsonArray arraydiscount;
        JsonObject jdiscount;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();



        try {
            File discfile = new File("lorenzo21/src/main/resources/effects/getFreeandDiscount.json");
            FileReader read = new FileReader(discfile.getAbsolutePath());

            arraydiscount = Json.parse(read).asArray();
            for(i=0; i<arraydiscount.size();i++){
                List<Risorsa> discounts = new ArrayList<>();
                jdiscount = arraydiscount.get(i).asObject();
                singlediscountfree.setId(jdiscount.get("id").asInt());

                singlediscountfree.setDicepower(jdiscount.getInt("dice",2));

                //RESOURCES
                coin.setTipo("Coins");
                coin.setQuantity(jdiscount.getInt("Coins",0));
                discounts.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jdiscount.getInt("Woods",0));
                discounts.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jdiscount.getInt("Stones", 0));
                discounts.add(2, (Risorsa) stone.clone());


                singlediscountfree.setResource(discounts);

                singlediscountfree.setTypecard(jdiscount.get("type").asString());

                discountfreelist.add(i, (GetFreeandDiscount) singlediscountfree.clone());

            }
        }catch(IOException e){
            e.printStackTrace(); // TODO
        }


        return discountfreelist;
    }

    //'getResourcesSelling' effects parsing
    public List<GetResourcesSelling> sellingparse(){
        int i;
        EffectStrategy singleselling = new GetResourcesSelling();
        List<GetResourcesSelling> sellinglist = new ArrayList<>();
        JsonArray arrayselling;
        JsonObject jselling;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File sellingfile = new File("lorenzo21/src/main/resources/effects/getexresIfandSell.json");
            FileReader reader = new FileReader(sellingfile.getAbsolutePath());
            arrayselling = Json.parse(reader).asArray();

            for(i=0; i<arrayselling.size();i++) {
                List<Risorsa> selling = new ArrayList<>();
                List<Risorsa> reward = new ArrayList<>();
                jselling = arrayselling.get(i).asObject();
                singleselling.setId(jselling.get("id").asInt());
                singleselling.setDicepower(jselling.get("dado").asInt());

                //tospend
                coin.setTipo("Coinsto");
                coin.setQuantity(jselling.getInt("Coinsto", 0));
                selling.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woodsto");
                wood.setQuantity(jselling.getInt("Woodsto", 0));
                selling.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stonesto");
                stone.setQuantity(jselling.getInt("Stonesto", 0));
                selling.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servantsto");
                servant.setQuantity(jselling.getInt("Servantsto", 0));
                selling.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsto");
                military.setQuantity(jselling.getInt("MilitaryPointsto", 0));
                selling.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsto");
                faith.setQuantity(jselling.getInt("FaithPointsto", 0));
                selling.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsto");
                victory.setQuantity(jselling.getInt("VictoryPointsto", 0));
                selling.add(6, (Risorsa) victory.clone());
                singleselling.setTospend(selling);

                //reward
                coin.setTipo("Coinsre");
                coin.setQuantity(jselling.getInt("Coinsre", 0));
                reward.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woodsre");
                wood.setQuantity(jselling.getInt("Woodsre", 0));
                reward.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stonesre");
                stone.setQuantity(jselling.getInt("Stonesre", 0));
                reward.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servantsre");
                servant.setQuantity(jselling.getInt("Servantsre", 0));
                reward.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPointsre");
                military.setQuantity(jselling.getInt("MilitaryPointsre", 0));
                reward.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPointsre");
                faith.setQuantity(jselling.getInt("FaithPointsre", 0));
                reward.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPointsre");
                victory.setQuantity(jselling.getInt("VictoryPointsre", 0));
                reward.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavorre");
                favor.setQuantity(jselling.getInt("PalaceFavorre", 0));
                reward.add(7,(Risorsa) favor.clone());
                singleselling.setReward(reward);

                sellinglist.add(i, (GetResourcesSelling) singleselling.clone());
            }
        }catch(IOException e) {
            e.printStackTrace(); //TODO
        }
        return sellinglist;
    }

    //'getResourcesIf' effect parsing
    public List<GetResourcesIf> ifparsing(){

        int i;
        List<GetResourcesIf> iflist = new ArrayList<>();
        EffectStrategy singleif = new GetResourcesIf();
        JsonArray arrayif;
        JsonObject jif;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File fileif = new File("lorenzo21/src/main/resources/effects/getexresif.json");
            FileReader ifread = new FileReader(fileif.getAbsolutePath());

            arrayif = Json.parse(ifread).asArray();

            for (i=0; i<arrayif.size();i++){
                List<Risorsa> ifresources = new ArrayList<>();
                jif= arrayif.get(i).asObject();
                singleif.setId(jif.get("id").asInt());
                singleif.setDicepower(jif.get("dado").asInt());

                //Resources
                coin.setTipo("Coins");
                coin.setQuantity(jif.getInt("Coins", 0));
                ifresources.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jif.getInt("Woods", 0));
                ifresources.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jif.getInt("Stones", 0));
                ifresources.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jif.getInt("Servants", 0));
                ifresources.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPoints");
                military.setQuantity(jif.getInt("MilitaryPoints", 0));
                ifresources.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPoints");
                faith.setQuantity(jif.getInt("FaithPoints", 0));
                ifresources.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPoints");
                victory.setQuantity(jif.getInt("VictoryPoints", 0));
                ifresources.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavor");
                favor.setQuantity(jif.getInt("PalaceFavor", 0));
                ifresources.add(7, (Risorsa) favor.clone());

                singleif.setResource(ifresources);
                iflist.add(i, (GetResourcesIf) singleif.clone());

            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return iflist;
    }

    //'getReources' effects parsing
    public List<GetResources> getResourcesparsing(){
        int i;
        EffectStrategy res = new GetResources();
        List<GetResources> reslist = new ArrayList<>();
        JsonArray arrayres;
        JsonObject jres;
        Risorsa coin = new Risorsa();
        Risorsa wood = new Risorsa();
        Risorsa stone = new Risorsa();
        Risorsa servant = new Risorsa();
        Risorsa military = new Risorsa();
        Risorsa faith = new Risorsa();
        Risorsa victory = new Risorsa();
        Risorsa favor = new Risorsa();

        try{
            File resfile = new File("lorenzo21/src/main/resources/effects/getexres.json");
            FileReader readfile = new FileReader(resfile.getAbsolutePath());

            arrayres = Json.parse(readfile).asArray();
            for(i=0; i<arrayres.size();i++){
                List<Risorsa> resources= new ArrayList<>();
                jres = arrayres.get(i).asObject();
                res.setId(jres.get("id").asInt());

                //RESOURCES
                coin.setTipo("Coins");
                coin.setQuantity(jres.getInt("Coins", 0));
                resources.add(0, (Risorsa) coin.clone());
                wood.setTipo("Woods");
                wood.setQuantity(jres.getInt("Woods", 0));
                resources.add(1, (Risorsa) wood.clone());
                stone.setTipo("Stones");
                stone.setQuantity(jres.getInt("Stones", 0));
                resources.add(2, (Risorsa) stone.clone());
                servant.setTipo("Servants");
                servant.setQuantity(jres.getInt("Servants", 0));
                resources.add(3, (Risorsa) servant.clone());
                military.setTipo("MilitaryPoints");
                military.setQuantity(jres.getInt("MilitaryPoints", 0));
                resources.add(4, (Risorsa) military.clone());
                faith.setTipo("FaithPoints");
                faith.setQuantity(jres.getInt("FaithPoints", 0));
                resources.add(5, (Risorsa) faith.clone());
                victory.setTipo("VictoryPoints");
                victory.setQuantity(jres.getInt("VictoryPoints", 0));
                resources.add(6, (Risorsa) victory.clone());
                favor.setTipo("PalaceFavor");
                favor.setQuantity(jres.getInt("PalaceFavor", 0));
                resources.add(7, (Risorsa) favor.clone());

                res.setResource(resources);
                reslist.add(i, (GetResources) res.clone());
            }


        }catch(IOException e){
            e.printStackTrace(); //TODO
        }
        return reslist;
    }

    //'GetBoostdice' effects parsing
    public List<GetBoostDice> getboostparsing(){
        int i;
        EffectStrategy boost = new GetBoostDice();
        List<GetBoostDice> boostlist = new ArrayList<>();
        JsonArray boostarray;
        JsonObject jboost;


        try{
            File fileboost = new File("lorenzo21/src/main/resources/effects/getboostdiceonAction.json");
            FileReader readboost = new FileReader(fileboost.getAbsolutePath());
            boostarray = Json.parse(readboost).asArray();

            for(i=0; i<boostarray.size();i++){
                jboost = boostarray.get(i).asObject();
                boost.setId(jboost.get("id").asInt());
                boost.setTypecard(jboost.get("type").asString());
                boost.setDiceboost(jboost.get("dice").asInt());
                boostlist.add(i, (GetBoostDice) boost.clone());

            }


        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return boostlist;
    }

    //'GetForEach' effect parsing
    public List<GetForEach> forEachparsing(){

        int i,j;
        EffectStrategy effect = new GetForEach();
        List<GetForEach> effectlist = new ArrayList<>();
        JsonArray arrayeffect;
        JsonObject jeffect;
        Risorsa coin = new Risorsa();

        Risorsa military = new Risorsa();

        Risorsa victory = new Risorsa();

        Risorsa character = new Risorsa();
        Risorsa venture = new Risorsa();
        Risorsa territory = new Risorsa();
        Risorsa building = new Risorsa();

        try {
            File fileeffect = new File("lorenzo21/src/main/resources/effects/getForEach.json");
            FileReader readfile = new FileReader(fileeffect.getAbsolutePath());

            arrayeffect = Json.parse(readfile).asArray();
            for(i=0; i<arrayeffect.size();i++) {
                List<Risorsa> foreach = new ArrayList<>();
                List<Risorsa> getres = new ArrayList<>();
                jeffect = arrayeffect.get(i).asObject();
                effect.setId(jeffect.get("id").asInt());
                effect.setDice(jeffect.get("dado").asInt());

                //FOREACH

                military.setTipo("MilitaryPoints");
                military.setQuantity(jeffect.getInt("MilitaryPoints", 0));
                foreach.add(0, (Risorsa) military.clone());
                character.setTipo("CharacterCards");
                character.setQuantity(jeffect.getInt("CharacterCards", 0));
                foreach.add(1, (Risorsa) character.clone());
                venture.setTipo("VentureCards");
                venture.setQuantity(jeffect.getInt("VentureCards", 0));
                foreach.add(2, (Risorsa) venture.clone());
                territory.setTipo("TerritoryCards");
                territory.setQuantity(jeffect.getInt("TerritoryCards", 0));
                foreach.add(3, (Risorsa) territory.clone());
                building.setTipo("BuildingCards");
                building.setQuantity(jeffect.getInt("BuildingCards", 0));
                foreach.add(4, (Risorsa) building.clone());
                effect.setResourcesfor(foreach);

                //GETRESOURCES
                coin.setTipo("CoinsGet");
                coin.setQuantity(jeffect.getInt("CoinsGet", 0));
                getres.add(0, (Risorsa) coin.clone());

                victory.setTipo("VictoryPointsGet");
                victory.setQuantity(jeffect.getInt("VictoryPointsGet", 0));
                getres.add(1, (Risorsa) victory.clone());

                effect.setResourcesget(getres);

                effectlist.add(i, (GetForEach) effect.clone());

            }


        }catch (IOException e){
            e.printStackTrace(); //TODO
        }
        return effectlist;
    }

    //'GetEndGameVP' effects parsing
    public List<GetVPEnd> getVPparsing() {

        int i;
        EffectStrategy endeffect = new GetVPEnd();
        List<GetVPEnd> listendeffect = new ArrayList<>();
        JsonArray arrayendeffect;
        JsonObject jendeffect;

        try{
            File endfile = new File("lorenzo21/src/main/resources/effects/getVPendgame.json");
            FileReader readend = new FileReader(endfile.getAbsolutePath());

            arrayendeffect = Json.parse(readend).asArray();
            for(i=0; i<arrayendeffect.size();i++) {
                jendeffect = arrayendeffect.get(i).asObject();
                endeffect.setId(jendeffect.get("id").asInt());
                endeffect.setVP(jendeffect.get("VP").asInt());
                listendeffect.add(i, (GetVPEnd) endeffect.clone());

            }

        }catch(IOException e){
            e.printStackTrace(); //TODO
        }

        return listendeffect;
    }




    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace(); //TODO return to server
            return null;
        }
    }


}