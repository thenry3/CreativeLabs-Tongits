package com.example.tongits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.*;


public class Game extends AppCompatActivity {

    // GLOBAL VARIABLES

    boolean gameFinished = false; // keeps track of when game ends

    ImageView mainDeckView; //the icon for the main deck
    LinearLayout viewHand; //holds player's cards
    ImageButton AI1DiscardView;
    ImageButton playerDiscardView;
    ImageButton AI2DiscardView;


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

        // initialize variables with Views
        mainDeckView = findViewById(R.id.cardDeck);
        mainDeckView.setImageResource(R.drawable.back);
        viewHand = findViewById(R.id.playerHand);
        viewHand.getLayoutParams().width = 100;
        AI1DiscardView = findViewById(R.id.AI1DiscardPile);
        AI2DiscardView = findViewById(R.id.AI2DiscardPile);
        playerDiscardView = findViewById(R.id.playerDiscardPile);

        final Deck deckOfCards = new Deck();

        //shuffle a few times for good measure
        for (int i = 0; i < 20; i++){
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

        // initialize discard piles
        AI1Discard = new ArrayList<>();
        AI2Discard = new ArrayList<>();
        playerDiscard = new ArrayList<>();

        // update player hand view
        updateHand();

        //button to allow player to draw card from main deck
        mainDeckView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 if (playerDrawTurn && deckOfCards.getNumberOfCardsInDeck() > 0) {
                        playerHand.add(deckOfCards.topCard());
                        deckOfCards.removeCard();
                        updateHand();
                        playerDrawTurn = false;
                        AITurn(deckOfCards);
                    }
                }
            });

        AI1DiscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AI2DiscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        playerDiscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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
        setNewCardImage(AI1Discard.get(AI1Discard.size() - 1).getSuit(), AI1Discard.get(AI1Discard.size() - 1).getValue(), AI1DiscardView);


//        try {
//            TimeUnit.SECONDS.sleep(2);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }

        // AI 2 turn
        AI2Hand.add(deck.topCard());
        deck.removeCard();
        highcardindex = findHighestCard(AI2Hand);
        AI2Discard.add(AI2Hand.get(highcardindex));
        AI2Hand.remove(highcardindex);
        setNewCardImage(AI2Discard.get(AI2Discard.size() - 1).getSuit(), AI2Discard.get(AI2Discard.size() - 1).getValue(), AI2DiscardView);

        playerDrawTurn = true; //end AIs' turns

        return;
    }

    // update the player's hand on screen
    void updateHand(){
        viewHand.removeAllViews();
        for (int i = 0; i < playerHand.size(); i++) {
            ImageButton card = new ImageButton(this);
            setNewCardImage(playerHand.get(i).getSuit(), playerHand.get(i).getValue(), card);
            card.setAdjustViewBounds(true);
            card.setLayoutParams(new LinearLayout.LayoutParams(258, 400));
            viewHand.addView(card);

            View space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            viewHand.addView(space);

        }
    }

    // Depending on the suit and card, update the card's photo
    void setNewCardImage(int suit, int value, View card)
    {
        switch (suit) {
            case 1:
                switch (value) {
                    case 1:
                        card.setBackgroundResource(R.drawable.spades_a);
                        break;
                    case 2:
                        card.setBackgroundResource(R.drawable.spades_2);
                        break;
                    case 3:
                        card.setBackgroundResource(R.drawable.spades_3);
                        break;
                    case 4:
                        card.setBackgroundResource(R.drawable.spades_4);
                        break;
                    case 5:
                        card.setBackgroundResource(R.drawable.spades_5);
                        break;
                    case 6:
                        card.setBackgroundResource(R.drawable.spades_6);
                        break;
                    case 7:
                        card.setBackgroundResource(R.drawable.spades_7);
                        break;
                    case 8:
                        card.setBackgroundResource(R.drawable.spades_8);
                        break;
                    case 9:
                        card.setBackgroundResource(R.drawable.spades_9);
                        break;
                    case 10:
                        card.setBackgroundResource(R.drawable.spades_10);
                        break;
                    case 11:
                        card.setBackgroundResource(R.drawable.spades_j);
                        break;
                    case 12:
                        card.setBackgroundResource(R.drawable.spades_q);
                        break;
                    case 13:
                        card.setBackgroundResource(R.drawable.spades_k);
                        break;
                }
                break;
            case 2:
                switch (value) {
                    case 1:
                        card.setBackgroundResource(R.drawable.clubs_a);
                        break;
                    case 2:
                        card.setBackgroundResource(R.drawable.clubs_2);
                        break;
                    case 3:
                        card.setBackgroundResource(R.drawable.clubs_3);
                        break;
                    case 4:
                        card.setBackgroundResource(R.drawable.clubs_4);
                        break;
                    case 5:
                        card.setBackgroundResource(R.drawable.clubs_5);
                        break;
                    case 6:
                        card.setBackgroundResource(R.drawable.clubs_6);
                        break;
                    case 7:
                        card.setBackgroundResource(R.drawable.clubs_7);
                        break;
                    case 8:
                        card.setBackgroundResource(R.drawable.clubs_8);
                        break;
                    case 9:
                        card.setBackgroundResource(R.drawable.clubs_9);
                        break;
                    case 10:
                        card.setBackgroundResource(R.drawable.clubs_10);
                        break;
                    case 11:
                        card.setBackgroundResource(R.drawable.clubs_j);
                        break;
                    case 12:
                        card.setBackgroundResource(R.drawable.clubs_q);
                        break;
                    case 13:
                        card.setBackgroundResource(R.drawable.clubs_k);
                        break;
                }
                break;
            case 3:
                switch (value) {
                    case 1:
                        card.setBackgroundResource(R.drawable.diamonds_a);
                        break;
                    case 2:
                        card.setBackgroundResource(R.drawable.diamonds_2);
                        break;
                    case 3:
                        card.setBackgroundResource(R.drawable.diamonds_3);
                        break;
                    case 4:
                        card.setBackgroundResource(R.drawable.diamonds_4);
                        break;
                    case 5:
                        card.setBackgroundResource(R.drawable.diamonds_5);
                        break;
                    case 6:
                        card.setBackgroundResource(R.drawable.diamonds_6);
                        break;
                    case 7:
                        card.setBackgroundResource(R.drawable.diamonds_7);
                        break;
                    case 8:
                        card.setBackgroundResource(R.drawable.diamonds_8);
                        break;
                    case 9:
                        card.setBackgroundResource(R.drawable.diamonds_9);
                        break;
                    case 10:
                        card.setBackgroundResource(R.drawable.diamonds_10);
                        break;
                    case 11:
                        card.setBackgroundResource(R.drawable.diamonds_j);
                        break;
                    case 12:
                        card.setBackgroundResource(R.drawable.diamonds_q);
                        break;
                    case 13:
                        card.setBackgroundResource(R.drawable.diamonds_k);
                        break;
                }
                break;
            case 4:
                switch (value) {
                    case 1:
                        card.setBackgroundResource(R.drawable.hearts_a);
                        break;
                    case 2:
                        card.setBackgroundResource(R.drawable.hearts_2);
                        break;
                    case 3:
                        card.setBackgroundResource(R.drawable.hearts_3);
                        break;
                    case 4:
                        card.setBackgroundResource(R.drawable.hearts_4);
                        break;
                    case 5:
                        card.setBackgroundResource(R.drawable.hearts_5);
                        break;
                    case 6:
                        card.setBackgroundResource(R.drawable.hearts_6);
                        break;
                    case 7:
                        card.setBackgroundResource(R.drawable.hearts_7);
                        break;
                    case 8:
                        card.setBackgroundResource(R.drawable.hearts_8);
                        break;
                    case 9:
                        card.setBackgroundResource(R.drawable.hearts_9);
                        break;
                    case 10:
                        card.setBackgroundResource(R.drawable.hearts_10);
                        break;
                    case 11:
                        card.setBackgroundResource(R.drawable.hearts_j);
                        break;
                    case 12:
                        card.setBackgroundResource(R.drawable.hearts_q);
                        break;
                    case 13:
                        card.setBackgroundResource(R.drawable.hearts_k);
                        break;
                }
                break;
        }

    }
}
