package it.polimi.ingsw.GameModelServer;

/**
 * Created by Simone on 20/06/2017.
 */
public class Token {
    private String color;
    private String type;
    private int position;


    public Token(String color){
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }
}
