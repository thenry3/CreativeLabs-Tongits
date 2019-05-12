package com.example.tongits;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Deck {

    //CONSTRUCTOR
    public Deck()
    {
        card_deck = new ArrayList<>();

        // make all the cards
        for (int suit = 1; suit < 5; suit++)
        {
            for (int value = 1; value < 14; value++)
            {
                card_deck.add(new Card(suit, value));
            }
        }
    }

    // shuffle function
    public void shuffleDeck()
    {
        Random random = new Random();

        //For every card location, choose a random int and switch
        for (int i = 0; i < getNumberOfCardsInDeck(); i++) {
            int randomInt = random.nextInt() % 53;
            Card tempcard = card_deck.get(i);
            card_deck.set(i, card_deck.get(randomInt));
            card_deck.set(randomInt, tempcard);
        }
    }

    public int getNumberOfCardsInDeck()
    {
        return card_deck.size();
    }

    public ArrayList<Card> getCardDeck(){
        return card_deck;
    }

    // get the topcard of the deck
    public Card topCard(){
        return card_deck.get(card_deck.size() - 1);
    }

    //remove the top card in the deck
    public void removeCard(){
        if (getNumberOfCardsInDeck() > 0) {
            card_deck.remove(getNumberOfCardsInDeck() - 1);
        }
    }


    // PRIVATE VARIABLES
    private ArrayList<Card> card_deck;
}
