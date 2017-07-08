package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class FamilyMember {

    private String colorplayer;
    private String color;
    private int value;
    private boolean fmUsed;


    public FamilyMember(String color,String colorplayer) {
        this.color = color;
        this.colorplayer = colorplayer;
        value=0;
        fmUsed = false;
    }

    public boolean isFmUsed() {
        return fmUsed;
    }

    public void setFmUsed(boolean fmUsed) {
        this.fmUsed = fmUsed;
    }

    public String getColor(){
        return color;
    }

    public String getColorplayer() {
        return colorplayer;
    }

    public int getValue(){
        return value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setColorplayer(String colorplayer) {
        this.colorplayer = colorplayer;
    }

    public void setValue(int value) {
        this.value = value;
    }
}