package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class FamilyMember {

    public String color;
    public int value;

    public FamilyMember(String color) {
        this.color = color;
        value=0;
    }


    public String getColor(){
        return color;
    }

    public int getValue(){
        return value;
    }

    
}