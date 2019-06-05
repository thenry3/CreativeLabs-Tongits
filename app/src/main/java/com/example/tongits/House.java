package com.example.tongits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class House{
    //private variables
    public ArrayList<Card> card_house_list; //bad practice but reeeeeee

    //constructor
    public House(ArrayList<Card> oldcard_list) {
        card_house_list = new ArrayList<>(oldcard_list); //copy over by creating a shallow copy
    }

    public Card returnTopCard(){
        return card_house_list.get(card_house_list.size() - 1);
    }

    public boolean addCard (Card c){
        ArrayList<Card> temp_list = new ArrayList<>(card_house_list);
        temp_list.add(c);
        if (!isValidHouse(temp_list))
            return false;
        card_house_list.add(c);
        return true;
    }

    public boolean isValidHouse(ArrayList<Card> card_list) {
        Collections.sort(card_house_list);
        if (houseLength() < 3)
            return false;

        //checking if all the same #
        boolean isSameValue = true;
        int initialValue = card_list.get(0).getValue();
        for (int i = 1; i < card_list.size(); i++){
            if(!(card_list.get(i).getValue()==initialValue)){
                isSameValue = false;
                break;
            }
        }

        //checking if the same suit
        boolean isSameSuit = true;
        int initialSuit = card_list.get(0).getSuit();
        for (int i = 1; i < card_list.size(); i++){
            if (!(card_list.get(i).getSuit() == initialSuit)){
                isSameSuit=false;
                break;
            }
        }
        //checking if consecutive #s
        boolean isConsecutive = true;
        for (int i = 0; i < card_list.size()-1; i++){
            if (card_list.get(i+1).getValue() - card_list.get(i).getValue() !=1){
                isConsecutive = false;
                break;
            }
        }


        return isSameValue || (isSameSuit && isConsecutive);
    }

    public int houseLength(){
        return card_house_list.size();
    }
}