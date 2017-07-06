package it.polimi.ingsw.GameModelServer;

import java.util.*;
import java.util.Scanner;

/**
 * Created by Simone on 02/07/2017.
 */
public class GetBoostandDiscount extends EffectStrategy implements Cloneable {
    private int id;
    private int diceboost;
    private String typecard;
    private boolean select;
    private List<Risorsa> discount;

    public GetBoostandDiscount(){}

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setDiceboost(int diceboost) {
        this.diceboost = diceboost;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setTypecard(String effecttype) {
        this.typecard = effecttype;
    }

    public void setDiscount(List<Risorsa> discount) {
        this.discount = discount;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTypecard() {
        return typecard;
    }

    public int getDiceboost() {
        return diceboost;
    }

    public List<Risorsa> getDiscount() {
        return discount;
    }
    public boolean getSelect(){
        return select;
    }

    @Override
    public Object clone()   {
        return super.clone();
    }


    public FamilyMember apply(FamilyMember member, String type) {
        if(this.typecard.equalsIgnoreCase(type)) {
            int oldvalue;
            oldvalue = member.getValue();
            member.setValue(oldvalue + this.diceboost);
        }
        return member;
    }


    public List<Risorsa> apply(List<Risorsa> cost) {
        int i = 0;
        String choice;
        int chosen = 0;
        if(select){
            System.out.println("Which discount do you want to apply? Woods - Stones");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextLine();
            while(!(choice.equals("Woods") || choice.equals("Stones"))){
                System.out.println("Error in input: Woods - Stones");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.nextLine();
            }
            for(Risorsa single : discount){
                if(single.gettipo().equals(choice))
                    chosen = single.getquantity();
            }
            for(Risorsa res : cost){
                if(res.gettipo().equals(choice)){
                        cost.get(i).setQuantity(cost.get(i).getquantity() - chosen);
                }
                i++;
            }
        }else{
            for(Risorsa res : cost){
                for(Risorsa single : discount){
                    if(res.gettipo().equals(single.gettipo()))
                        cost.get(i).setQuantity(cost.get(i).getquantity() - single.getquantity());
                }
                i++;
            }
        }

        return cost;
    }



}
