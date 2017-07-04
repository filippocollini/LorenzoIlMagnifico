package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Tower {

    /**
     * Default constructor
     */
    public Tower(String type) {
        this.type = type;
        floors= new ArrayList<>();
    }

    /**
     * 
     */
    private List<CellTower> floors;
    private String type;

    public List<CellTower> getFloors() {
        return floors;
    }

    public String getType() {
        return type;
    }

    public void setFloors(List<CellTower> floors) {
        this.floors = floors;
    }
}