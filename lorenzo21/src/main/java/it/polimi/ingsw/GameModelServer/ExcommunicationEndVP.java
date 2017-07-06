package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 18/06/2017.
 */
public class ExcommunicationEndVP extends EffectStrategy implements Cloneable {
    private int id;
    private int period;
    private String type;

    public ExcommunicationEndVP (){}

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setTypecard(String type) {
        this.type = type;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCard() {
        return type;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Player apply(Player player) { // il type deve essere un CharacterCard o VentureCard ecc..
        int i;
        int j;
        int k;

        for (i = 0; i < player.getPB().gettypecards(type).size(); i++) { //scorro tutte le celle di quel tipo
            if(type.equalsIgnoreCase("VentureCard")){
                    for(j=0;j<player.getPB().gettypecards(type).get(i).getCard().getGetvp()
                            .size();j++) { //per ogni carta scorro tutti i possibili effetti GetVP
                        for (k = 0; k < player.getPB().gettypecards(type).get(i).getCard().getPermanenteffect().size();
                             k++) { //scorro tutti gli effetti permanenti della carta
                            if (player.getPB().gettypecards(type).get(i).getCard().getPermanenteffect()
                                    .get(k) == player.getPB().gettypecards(type).get(i).getCard().getGetvp()
                                    .get(j).getId()) //confronto l'id dell'effetto con gli effetti della carta
                                player.getPB().gettypecards(type).get(i).getCard().getGetvp()
                                        .get(j).setVP(0);//setto i vp dell'effetto a 0
                        }
                    }
            }else {
                player.getPB().gettypecards(type).get(i).setVictoryPoints(0); // setto i vp della cella a 0
            }
        }
        return player;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
