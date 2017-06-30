package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class CharacterCard extends DevelopementCard {

    private Risorsa cost1;
    private boolean choice;

    public CharacterCard() {
        super();
        cost1 = new Risorsa();
    }

    public Risorsa getCost1(){
        return cost1;
    }

    public boolean getChoice(){
        return choice;
    }

    public void setCost1(Risorsa cost1){
        this.cost1 = cost1;
    }

    public void setChoice(boolean choice){
        this.choice = choice;
    }

    public List<DevelopementCard> getCharacterCard(ArrayList<DevelopementCard> listcard) {
        int i;
        List<DevelopementCard> charactcard = new ArrayList<DevelopementCard>();

        for (i = 0; i < listcard.size(); i++) {
            if (listcard.get(i).getCardtype() == "characters") {
                charactcard.add(i, listcard.get(i));
            }
        }
        return charactcard;
    }


    @Override
    public void activateEffect(int id) {
        super.activateEffect(id);
    }
}

