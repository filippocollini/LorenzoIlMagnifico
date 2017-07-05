package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class FamilyMember {

    public String colorplayer;
    public String color;
    public int value;

    public FamilyMember(String color,String colorplayer) {
        this.color = color;
        this.colorplayer = colorplayer;
        value=0;
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