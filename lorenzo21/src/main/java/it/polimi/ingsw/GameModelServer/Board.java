package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Board {




    public List puntiVittoria;
    public List puntiFede;
    public List puntiMilitari;
    public List ordineTurno;
    public List produzione;
    public List raccolto;
    public List mercato;
    public List<ExcommunicationTiles> carteScomunica;
    public List councilpalace;
    public List<Integer> dadi;
    private static Board instance;
    public List<Tower> territoriesTower;
    private List<Tower> buildingsTower;
    private List<Tower> charactersTower;
    private List<Tower> venturesTower;


    private Board(){}

    public void getVari() {
        // TODO implement here
    }

    public static Board getInstance() {
            if (instance == null)
                instance = new Board();
        return instance;
    }

}