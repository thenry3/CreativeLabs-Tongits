package com.example.tongits;

import java.security.acl.AclEntry;

public class Card implements Comparable<Card>{

    // CONSTRUCTOR
    public Card(int suit, int value)
    {
//      card value ranges from 1 to 13
//      1 -> Ace
//      11 -> Jack
//      12 -> Queen
//      13 -> King
        if (value > 13 || value < 1)
            throw new RuntimeException("Illegal Card Value");

        // suit ranges from 1-4. In order: Spades, Clubs, Diamonds, Hearts
        if (suit < 1 || suit > 4)
            throw new RuntimeException("Illegal Card Suit");

        this.suit = suit;
        this.value = value;
    }

    public int compareTo(Card other){
        Integer s = suit;
        Integer s1 = other.suit;
        return s.compareTo(s1);
    }

    public int compareCard(Card other){
        if (this.value < other.value)
            return -1;
        else if (this.value > other.value)
            return 1;
        else
            return 0;
    }



    public int getSuit()
    {
        return suit;
    }

    public int getValue()
    {
        return value;
    }

    // PRIVATE VARIABLES
    private final int suit;
    private final int value;

}
