package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class CharacterCard extends DevelopementCard {

    ArrayList<DevelopementCard> cards;

    public CharacterCard() {}

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
    public void activateEffect(Effect effect) {

    }
}

