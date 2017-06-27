package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Tower {

    /**
     * Default constructor
     */
    public Tower() {
        floors= new ArrayList<CellTower>();
    }

    /**
     * 
     */
    private List<CellTower> floors;

    public List<CellTower> getFloors() {
        return floors;
    }

    public void setFloors(List<CellTower> floors) {
        this.floors = floors;
    }
}