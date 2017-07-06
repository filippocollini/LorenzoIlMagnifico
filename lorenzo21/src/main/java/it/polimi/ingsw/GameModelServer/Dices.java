package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 05/07/2017.
 */
public class Dices {

    private int value;
    private String color;

    public Dices (String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
