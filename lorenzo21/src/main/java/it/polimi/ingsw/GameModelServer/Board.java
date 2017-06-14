package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class Board {




    public List puntiVittoria;
    public List puntiFede;
    public List puntiMilitari;
    public List<Player> ordineTurno;
    public List produzione;
    public List raccolto;
    public List mercato;
    public List<ExcommunicationTiles> carteScomunica;
    public List<Player> councilpalace;
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

    public void modifyOrdineTurno(){}


    public static Board getInstance() {
            if (instance == null)
                instance = new Board();
        return instance;
    }

}