package it.polimi.ingsw.GameModelServer;



import it.polimi.ingsw.Exceptions.FileMalformedException;
import it.polimi.ingsw.ServerController.AbstractPlayer;
import it.polimi.ingsw.ServerController.Stanza;

import junit.framework.TestCase;

import org.testng.annotations.Test;


import java.util.*;

import static it.polimi.ingsw.GameModelServer.Game.*;
import static it.polimi.ingsw.GameModelServer.Game.isFMok;


public class GameTest extends TestCase {

    @Test
    public void testisFMok(){
        FamilyMember member = new FamilyMember("Orange","blue");
        member.setValue(4);
        int floor = 3;
        Board board;
        board = Board.getInstance(2);
        Player player = new Player("paolo","blue",board);
        assert (isFMok(member,floor,player,3) == member);
    }

    @Test
    public void testcontrolpurchase(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(2);
            Player player = new Player("paolo","blue",board);
            assert (controlpurchase(player, game.getVioletdeck(3).drawfirstCard(), false, false));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testreOrder(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(3);
        FamilyMember member1 = new FamilyMember("Neutral","blue");
        FamilyMember member2 = new FamilyMember("Neutral","yellow");
        FamilyMember member3 = new FamilyMember("Orange","blue");
        FamilyMember member4 = new FamilyMember("Black","yellow");
        Player player1 = new Player("paolo","blue",board);
        Player player2 = new Player("luca","yellow",board);

        Player player4 = new Player("olmo","green",board);
        List<Player> oldorder = new ArrayList<>();
        oldorder.add(player2);
        oldorder.add(player1);
        oldorder.add(player4);


        CellAction cell1 = new CellAction();
        cell1.setFamilyMemberinCell(member1);
        board.getCouncilpalace().add( cell1);
        CellAction cell2 = new CellAction();
        cell2.setFamilyMemberinCell(member2);
        board.getCouncilpalace().add(cell2);
        CellAction cell3 = new CellAction();
        cell3.setFamilyMemberinCell(member3);
        board.getCouncilpalace().add( cell3);
        CellAction cell4 = new CellAction();
        cell4.setFamilyMemberinCell(member4);
        board.getCouncilpalace().add(cell4);

        assertEquals ("list should be equals", Arrays.asList(player1,player2,player4),game.reOrder(oldorder,board));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testrandomcolor(){
        List<String> previous = new ArrayList<>();
        previous.add("blue");
        previous.add("green");
        previous.add("yellow");
        assertEquals("should be red",randomcolor(previous),"red");
    }

    @Test
    public void testaddFMonTowerControl(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(2);
            boolean free = true;
            String member = "Black";
            String tower = "buildings";
            Player player = new Player("paolo","blue",board);
            assertEquals ("should be success","success"
                    ,game.addFMonTowerControl(player,member,tower,3,free));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testaddFMonProduction(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(2);

            String member = "White";
            List<Integer> choice = new ArrayList<>();
            choice.add(1);
            Player player = new Player("paolo","blue",board);
            assertEquals ("should be success","success"
                    ,game.addFMonProduction(player,member,choice));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testaddFMonPalace(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(2);

            String member = "Orange";
            String favor = "MilitaryPoints";

            Player player = new Player("paolo","blue",board);
            assertEquals ("should be success","success"
                    ,game.addFMonPalace(player,member,favor));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testaddFMonMarket(){
        Stanza stanza = new Stanza();

        HashMap<String,AbstractPlayer> map = new HashMap<>();
        try {
            Game game = new Game(map,stanza);
            Board board;
            board = Board.getInstance(2);

            String member = "Orange";
            String favor = "MPC";

            Player player = new Player("paolo","blue",board);
            assertEquals ("should be success","success"
                    ,game.addFMonMarket(player,member,favor));
        } catch (FileMalformedException e) {
            e.printStackTrace();
        }
    }

}