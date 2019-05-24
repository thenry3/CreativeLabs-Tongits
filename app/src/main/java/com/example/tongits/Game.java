package com.example.tongits;

import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import android.graphics.drawable.*;


public class Game extends AppCompatActivity {

    // GLOBAL VARIABLES

    boolean gameFinished = false; // keeps track of when game ends

    ImageView mainDeckView; //the icon for the main deck
    HorizontalScrollView viewHand; //holds player's cards


    ArrayList<Card> playerHand; //array to hold player's cards
    ArrayList<Card> playerDiscard; //player's discard pile - AI 1 takes from this
    ArrayList<Card> AI1Discard; //AI 1's discard- AI 2 takes from this
    ArrayList<Card> AI2Discard; //AI 2's discard - Player takes from this

    //arrays to hold AIs' hands
    ArrayList<Card> AI1Hand;
    ArrayList<Card> AI2Hand;
    boolean playerDrawTurn = true; //keeps track of player's turn

    /*------------------------------------------------------------------*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mainDeckView = findViewById(R.id.cardDeck);

        final Deck deckOfCards = new Deck();

        for (int i = 0; i < deckOfCards.getNumberOfCardsInDeck(); i++) {
            Log.d("ADebugTag", "Value: " + deckOfCards.getCardDeck().get(i).getSuit() + deckOfCards.getCardDeck().get(i).getSuit());
        }

        //shuffle a few times for good measure
        for (int i = 0; i < 5; i++){
            deckOfCards.shuffleDeck();
        }

        //initialize the player's hand and distribute 13 starting cards
        playerHand = new ArrayList<>();
        dealCards(deckOfCards, playerHand, 13);

        // initialize both AIs' hands and distribute 12 cards each
        AI1Hand = new ArrayList<>();
        AI2Hand = new ArrayList<>();
        dealCards(deckOfCards, AI1Hand, 12);
        dealCards(deckOfCards, AI2Hand, 12);

        mainDeckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if (playerDrawTurn && deckOfCards.getNumberOfCardsInDeck() > 0) {
                        playerHand.add(deckOfCards.topCard());
                        deckOfCards.removeCard();
                        playerDrawTurn = false;
                    }
                }
            });

//        updateHand();
//
//        while (deckOfCards.getNumberOfCardsInDeck() > 0) { //while there are cards in draw deck
//            updateHand();
//            // button listener to take card from main deck
//            mainDeckView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                 if (playerDrawTurn && deckOfCards.getNumberOfCardsInDeck() > 0) {
//                        playerHand.add(deckOfCards.topCard());
//                        deckOfCards.removeCard();
//                        playerDrawTurn = false;
//                    }
//                }
//            });

//            updateHand();
//
//            AITurn(deckOfCards);
//
//        }


    }

    void dealCards(Deck deck, ArrayList<Card> playerDeck, int numCards){
        for (int i = 0; i < numCards; i++) {
            playerDeck.add(deck.topCard());
            deck.removeCard();
        }
        return;
    }

    //finds the index of the greatest valued card in deck
    int findHighestCard(ArrayList<Card> deck){
        int highindex = 0;
        if (deck.size() > 1) {
            for (int i = 1; i < deck.size(); i++) {
                if (deck.get(i).compareCard(deck.get(highindex)) < 0)
                    highindex = i;
            }
        }
        return highindex;
    }

    // Have AIs' take a card from main deck and discard their highest valued card
    void AITurn(Deck deck){
        if (playerDrawTurn || deck.getNumberOfCardsInDeck() < 1)
            return;
        // AI 1 draw card and discard
        AI1Hand.add(deck.topCard());
        deck.removeCard();
        int highcardindex = findHighestCard(AI1Hand);
        AI1Discard.add(AI1Hand.get(highcardindex));
        AI1Hand.remove(highcardindex);

        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        // AI 2 turn
        AI2Hand.add(deck.topCard());
        deck.removeCard();
        highcardindex = findHighestCard(AI2Hand);
        AI2Discard.add(AI2Hand.get(highcardindex));
        AI2Hand.remove(highcardindex);

        playerDrawTurn = true; //end AIs' turns

        return;
    }

    // update the player's hand on screen
    void updateHand(){
        viewHand.removeAllViews();
        for (int i = 0; i < playerHand.size(); i++)
        {
            ImageButton card = new ImageButton(this);

            // Depending on the suit and card, update the card's photo
            switch(playerHand.get(i).getSuit())
            {
                case 1:
                    switch(playerHand.get(i).getValue())
                    {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            card.setBackgroundResource(R.drawable.ic_launcher_foreground);
                            break;
                    }
                case 2:
                    switch(playerHand.get(i).getValue())
                    {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            card.setBackgroundResource(R.drawable.ic_launcher_foreground);
                            break;
                    }
                case 3:
                    switch(playerHand.get(i).getValue())
                    {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            card.setBackgroundResource(R.drawable.ic_launcher_foreground);
                            break;
                    }
                case 4:
                    switch(playerHand.get(i).getValue())
                    {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            card.setBackgroundResource(R.drawable.ic_launcher_foreground);
                            break;
                    }
            }
            viewHand.addView(card);
        }

        this.setContentView(viewHand);
    }
}
