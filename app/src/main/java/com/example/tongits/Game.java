package com.example.tongits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.*;


public class Game extends AppCompatActivity {

    ImageView mainDeckView;
    ArrayList<Card> playerHand;
    boolean playerTurn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mainDeckView = findViewById(R.id.cardDeck);

        final Deck deckOfCards = new Deck();

        //shuffle a few times for good measure
        for (int i = 0; i < 5; i++){
            deckOfCards.shuffleDeck();
        }

        playerHand = new ArrayList<>();
        dealCards(deckOfCards, playerHand);

        mainDeckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerTurn)
                {
                    playerHand.add(deckOfCards.topCard());
                    deckOfCards.removeCard();
                    playerTurn = false;
                }
            }
        });



    }

    void dealCards(Deck deck, ArrayList<Card> playerDeck){
        for (int i = 0; i < 13; i++) {
            playerDeck.add(deck.topCard());
            deck.removeCard();
        }
    }
}
