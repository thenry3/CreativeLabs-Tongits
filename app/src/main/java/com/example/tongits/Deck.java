package com.example.tongits;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    //CONSTRUCTOR
    public Deck()
    {
        card_deck = new Card[52];
        numberOfCardsInDeck = 0;

        // make all the cards
        for (int suit = 1; suit < 5; suit++)
        {
            for (int value = 1; value < 14; value++)
            {
                card_deck[numberOfCardsInDeck] = new Card(suit, value);
                numberOfCardsInDeck++;
            }
        }
    }

    public void shuffleDeck()
    {
        Random random = new Random();

        //For every card location, choose a random int and switch
        for (int i = 0; i < numberOfCardsInDeck; i++)
        {
            Card tempcard = card_deck[i];
            card_deck[i] = card_deck[random.nextInt() % 53];
            card_deck[i] = tempcard;
        }

    }

    public int getNumberOfCardsInDeck()
    {
        return numberOfCardsInDeck;
    }


    // PRIVATE VARIABLES
    private Card[] card_deck;
    private int numberOfCardsInDeck;
}
