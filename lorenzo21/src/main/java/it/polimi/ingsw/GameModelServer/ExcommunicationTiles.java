package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class ExcommunicationTiles extends Card implements Cloneable{

    /**
     * Default constructor
     */
    public ExcommunicationTiles() {
    }


    private int periodo;
    private int effect;

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriod(){
        return periodo;
    }

    public int getEffect(){
        return effect;
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

    @Override
    public void activateEffect(Effect effect) {

    }
}