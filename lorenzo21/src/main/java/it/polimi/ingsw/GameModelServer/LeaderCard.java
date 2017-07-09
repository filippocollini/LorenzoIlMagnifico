package it.polimi.ingsw.GameModelServer;

import java.util.*;

/**
 * 
 */
public class LeaderCard extends Card {

    protected String name;
    protected List<Risorsa> requires;
    protected boolean active;
    protected boolean onceinarow;


    public LeaderCard() {
        requires = new ArrayList<>();
        active = false;
        onceinarow = false;
    }

    @Override
    public EffectStrategy activateEffect(int id) {
        return null;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Risorsa> getRequires() {
        return requires;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOnceinarow(boolean onceinarow) {
        this.onceinarow = onceinarow;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isOnceinarow() {
        return onceinarow;
    }

    public void setRequisiti(List<Risorsa> requisiti){
        this.requires = requisiti;
    }

    public Player onceInaRow(Player player){
        return null;
    }

    public FamilyMember onceInaRow(FamilyMember member){
        return null;
    }
    public List<Risorsa> doublebonusfromcard(List<Risorsa> resources){
        return null;
    }
    public List<Risorsa> coinsdiscount(List<Risorsa> cost){
        return null;
    }
    public FamilyMember boostmember(FamilyMember member){
        return null;
    }


}