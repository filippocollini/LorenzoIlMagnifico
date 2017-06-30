package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class EffectStrategy {

    protected int id;
    public EffectStrategy() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void apply(Player player) {}

}