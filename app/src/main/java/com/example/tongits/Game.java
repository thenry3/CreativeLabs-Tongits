package com.example.tongits;


import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.util.*;
import java.util.*;
import android.graphics.*;
import android.support.constraint.*;
import android.graphics.drawable.*;
import android.support.v4.graphics.drawable.*;


public class Game extends AppCompatActivity {

    // GLOBAL VARIABLES

    boolean gameFinished = false; // keeps track of when game ends
    final Deck deckOfCards = new Deck();

    ImageView mainDeckView; //the icon for the main deck
    LinearLayout viewHand; //holds player's cards
    LinearLayout DiscardPile;
    ConstraintLayout DiscardPilePage;

    ConstraintLayout EndGamePage;
    TextView endgameMessage;
    TextView pointsMessage;

    android.support.v7.widget.GridLayout HouseLayoutGameScreen; //holds the top cards of the players' houses

    ImageButton AI1DiscardView;
    ImageButton AI2DiscardView;
    ImageButton playerDiscardView;

    Button makeHouseButton;
    Button addHouseButton;

    ArrayList<Card> playerHand; //array to hold player's cards
    ArrayList<Card> playerDiscard; //player's discard pile - AI 1 takes from this
    ArrayList<Card> AI1Discard; //AI 1's discard- AI 2 takes from this
    ArrayList<Card> AI2Discard; //AI 2's discard - Player takes from this

    ArrayList<House> houses=new ArrayList<>();

    //arrays to hold AIs' hands
    ArrayList<Card> AI1Hand;
    ArrayList<Card> AI2Hand;
    boolean playerDrawTurn = false; //keeps track of player's turn
    boolean playerTurn = true;

    static boolean activeAI1 = false; //is the AI1DiscardPile Linear Layout open or not?
    static boolean activeAI2 = false; //same for AI2
    static boolean activePlayer = false; //same, but for player
    static int tracker1 = 0; //trying to see if the same button is pressed twice
    static int tracker2 = 0;
    static int playertracker = 0;

    ArrayList<Card> SelectedCards;
    boolean selectMode = false;

    boolean addMode = false;



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


        DiscardPile = findViewById(R.id.discardPiles); //this line matters! links to the name of the linear layout in the XML file
        DiscardPilePage = findViewById(R.id.discardPilePage); //links to constraint layout that controls display of cards
        EndGamePage = findViewById(R.id.endGamePopup);
        endgameMessage = findViewById(R.id.gameStatus);
        pointsMessage = findViewById(R.id.points);

        HouseLayoutGameScreen = findViewById(R.id.HouseGrid);

        AI1DiscardView = findViewById(R.id.AI1DiscardPile);
        AI2DiscardView = findViewById(R.id.AI2DiscardPile);
        playerDiscardView = findViewById(R.id.playerDiscardPile);

        makeHouseButton = findViewById(R.id.makeHouseButton);
        addHouseButton = findViewById(R.id.addHouseButton);

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
        SelectedCards = new ArrayList<>();

        // update player hand view
        updateHand();

        updateHouses(); //might interfere w/ update hand idk

        //button to allow player to draw card from main deck
        mainDeckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerDrawTurn && playerTurn && deckOfCards.getNumberOfCardsInDeck() > 0) {
                    playerHand.add(deckOfCards.topCard());
                    deckOfCards.removeCard();
                    updateHand();
                    playerDrawTurn = false;
                }
            }
        });

        AI2DiscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerDrawTurn && playerTurn && AI2Discard.size() > 0 && canDrawFromDiscard(playerHand, AI2Discard.get(AI2Discard.size() - 1))) {
                    playerHand.add(AI2Discard.remove(AI2Discard.size() - 1));
                    updateHand();
                    if (AI2Discard.isEmpty())
                        AI2DiscardView.setBackgroundResource(0);
                    else
                        setNewCardImage(AI2Discard.get(AI2Discard.size() - 1).getSuit(), AI2Discard.get(AI2Discard.size() - 1).getValue(), AI2DiscardView);
                    playerDrawTurn = false;
                }

            }
        });

        AI1DiscardView.setOnLongClickListener(new View.OnLongClickListener() { //this is for the first AI view
            @Override
            public boolean onLongClick(View v) {
                updateDiscardPile(1);

                activeAI1 = !activeAI1;

                if (activeAI1 == false && activeAI2 == false && activePlayer == false) {
                    DiscardPilePage.setVisibility(View.GONE);
                    tracker1=tracker2=playertracker=0;
                }
                else{
                    DiscardPilePage.setVisibility(View.VISIBLE);

                tracker1++;
                tracker2=playertracker=0;
                if (tracker1>=2) { //this line jank asf
                    DiscardPilePage.setVisibility(View.GONE);
                    tracker1 =0;
                }}
                return true;
            }
        });


        AI2DiscardView.setOnLongClickListener(new View.OnLongClickListener() { //this is for the first AI view
            @Override
            public boolean onLongClick(View v) {
                updateDiscardPile(2);
                activeAI2 = !activeAI2;

                if (activeAI1 == false && activeAI2 == false && activePlayer == false) {
                    DiscardPilePage.setVisibility(View.GONE);
                    tracker1=tracker2=playertracker=0;
                }
                else{
                    DiscardPilePage.setVisibility(View.VISIBLE);

                tracker2++;
                tracker1=playertracker=0;
                if (tracker2>=2) { //this line jank asf
                    DiscardPilePage.setVisibility(View.GONE);
                    tracker2 =0;
                }}

                return true;
            }
        });

        playerDiscardView.setOnLongClickListener(new View.OnLongClickListener() { //this is for the first AI view
            @Override
            public boolean onLongClick(View v) {
                updateDiscardPile(3);
                activePlayer = !activePlayer;
                if (activeAI1 == false && activeAI2 == false && activePlayer == false) {
                    DiscardPilePage.setVisibility(View.GONE);
                    tracker1=tracker2=playertracker=0;
                }
                else {
                    DiscardPilePage.setVisibility(View.VISIBLE);
                }
                playertracker++;
                tracker1=tracker2=0;
                if (playertracker>=2) { //this line jank asf
                    DiscardPilePage.setVisibility(View.GONE);
                    playertracker = 0;
                }

                return true;
            }
        });

        makeHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //must have selected at least 3 cards
                if (SelectedCards.size() < 3)
                    return;

                if (!isValidHouse(SelectedCards))
                    return;

                houses.add(new House(SelectedCards));

                for (int i = 0; i < SelectedCards.size(); i++)
                    playerHand.remove(SelectedCards.get(i));

                SelectedCards.clear();
                selectMode = false;

                makeHouseButton.setVisibility(View.GONE);
                addHouseButton.setVisibility(View.GONE);

                updateHand();
                updateHouses();

            }
        });

        addHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMode = !addMode;

                if (addMode)
                    addHouseButton.getBackground().setColorFilter(Color.argb(150, 0,0, 0), PorterDuff.Mode.SRC_ATOP);
                else
                    addHouseButton.getBackground().setColorFilter(Color.argb(0, 0,0, 0), PorterDuff.Mode.SRC_ATOP);
            }
        });

    }

    //finishes current activity (game screen) and returns to main menu
    public void onGameReturn (View view){
        finish();
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
                if (deck.get(i).compareCard(deck.get(highindex)) > 0)
                    highindex = i;
            }
        }
        return highindex;
    }

    // Have AIs' take a card from main deck and discard their highest valued card
    void AITurn(Deck deck){
        if (playerDrawTurn || playerTurn || deck.getNumberOfCardsInDeck() < 1) {
            if (deck.getNumberOfCardsInDeck() < 1) {
                gameFinished = true;
            }
            return;
        }
        // AI 1 turn
        // See if AI 1 can draw from player discard
        if (canDrawFromDiscard(AI1Hand, playerDiscard.get(playerDiscard.size() - 1)))
            AI1Hand.add(playerDiscard.remove(playerDiscard.size() - 1));
        else //if can't pull from player pile
        {
            AI1Hand.add(deck.topCard());
            deck.removeCard();
        }

        // check to see if any houses exist and place them
        if (AI1Hand.size() >= 3)
        {
            ArrayList<Card> temp = new ArrayList<>();
            for (int i = 0; i < AI1Hand.size(); i++) {
                for (int j = 0; j < AI1Hand.size(); j++) {
                    for (int k = 0; k < AI1Hand.size(); k++) {
                        if (i != k && i != j && j != k) {
                            temp.add(AI1Hand.get(i));
                            temp.add(AI1Hand.get(k));
                            temp.add(AI1Hand.get(j));
                            if (isValidHouse(temp))
                            {
                                houses.add(new House(temp));
                                for (int l = 0; l < temp.size(); l++)
                                    AI1Hand.remove(temp.get(l));
                                temp.clear();
                                updateHouses();
                            }
                        }
                    }
                }
            }
        }

        // if able to add to a house, do so
        for (int i = 0; i < AI1Hand.size(); i++)
        {
            for (int j = 0; j < houses.size(); j++)
            {
                if (houses.get(j).addCard(AI1Hand.get(i)))
                {
                    AI1Hand.remove(i);
                    updateHouses();
                }
            }
        }

        int highcardindex = findHighestCard(AI1Hand);
        AI1Discard.add(AI1Hand.remove(highcardindex));
        setNewCardImage(AI1Discard.get(AI1Discard.size() - 1).getSuit(), AI1Discard.get(AI1Discard.size() - 1).getValue(), AI1DiscardView);

        if (deckOfCards.getNumberOfCardsInDeck() == 0)
            mainDeckView.setBackgroundResource(0);
        if (deckOfCards.getNumberOfCardsInDeck() == 0 || playerHand.size() == 0) {
            gameOver();
        }

        // AI 2 turn
        // See if AI 2 can draw from player discard
        if (canDrawFromDiscard(AI2Hand, AI1Discard.get(AI1Discard.size() - 1)))
            AI2Hand.add(AI1Discard.remove(AI1Discard.size() - 1));
        else //if can't pull from player pile
        {
            AI2Hand.add(deck.topCard());
            deck.removeCard();
        }

        // check to see if any houses exist and place them
        if (AI2Hand.size() >= 3)
        {
            ArrayList<Card> temp = new ArrayList<>();
            for (int i = 0; i < AI2Hand.size(); i++) {
                for (int j = 0; j < AI2Hand.size(); j++) {
                    for (int k = 0; k < AI2Hand.size(); k++) {
                        if (i != k && i != j && j != k) {
                            temp.add(AI2Hand.get(i));
                            temp.add(AI2Hand.get(k));
                            temp.add(AI2Hand.get(j));
                            if (isValidHouse(temp))
                            {
                                houses.add(new House(temp));
                                for (int l = 0; l < temp.size(); l++)
                                    AI2Hand.remove(temp.get(l));
                                temp.clear();
                                updateHouses();
                            }
                        }
                    }
                }
            }
        }

        // if able to add to a house, do so
        for (int i = 0; i < AI2Hand.size(); i++)
        {
            for (int j = 0; j < houses.size(); j++)
            {
                if (houses.get(j).addCard(AI2Hand.get(i)))
                {
                    AI2Hand.remove(i);
                    updateHouses();
                }
            }
        }

        highcardindex = findHighestCard(AI2Hand);
        AI2Discard.add(AI2Hand.remove(highcardindex));
        setNewCardImage(AI2Discard.get(AI2Discard.size() - 1).getSuit(), AI2Discard.get(AI2Discard.size() - 1).getValue(), AI2DiscardView);

        if (deckOfCards.getNumberOfCardsInDeck() == 0)
            mainDeckView.setBackgroundResource(0);
        if (deckOfCards.getNumberOfCardsInDeck() == 0 || playerHand.size() == 0) {
            gameOver();
        }

        playerDrawTurn = true; //end AIs' turns
        playerTurn = true;

        return;
    }

    // update the player's hand on screen
    void updateHand(){
        viewHand.removeAllViews();
        for (int i = 0; i < playerHand.size(); i++) {
            final ImageButton card = new ImageButton(this);
            final int suit = playerHand.get(i).getSuit();
            final int value = playerHand.get(i).getValue();
            final int curr = i;
            setNewCardImage(playerHand.get(i).getSuit(), playerHand.get(i).getValue(), card);
            card.setAdjustViewBounds(true);
            card.setLayoutParams(new LinearLayout.LayoutParams(258, 400));
            viewHand.addView(card);
            card.setId(i);

            View space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            viewHand.addView(space);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //makes sure you draw first
                    if (playerDrawTurn)
                        return;
                    int index = viewHand.indexOfChild(card);
                    // if selecting cards for a house
                    if (selectMode)
                    {
                        if (SelectedCards.contains(playerHand.get(index/2))) {
                            card.getBackground().setColorFilter(Color.argb(0, 0, 0 ,0), PorterDuff.Mode.SRC_ATOP);
                            SelectedCards.remove(playerHand.get(index / 2));
                        }

                        else {
                            SelectedCards.add(playerHand.get(index/2));
                            card.getBackground().setColorFilter(Color.argb(150, 0, 0 ,0), PorterDuff.Mode.SRC_ATOP);
                        }

                        // if nothing is selected, turn off selectMode
                        if (SelectedCards.isEmpty()) {
                            selectMode = false;
                            makeHouseButton.setVisibility(View.GONE);
                            addHouseButton.setVisibility(View.GONE);
                        }

                        if (SelectedCards.size() >= 3)
                            makeHouseButton.setVisibility(View.VISIBLE);
                        else
                            makeHouseButton.setVisibility(View.GONE);

                        return;

                    }
                    // if not selecting, add card to player's discard
                    playerTurn = false;
                    playerDiscard.add(playerHand.remove(index/2));
                    updateDiscardPile(3);
                    updateHand();
                    setNewCardImage(playerDiscard.get(playerDiscard.size() - 1).getSuit(), playerDiscard.get(playerDiscard.size() - 1).getValue(), playerDiscardView);

                    if (deckOfCards.getNumberOfCardsInDeck() == 0)
                        mainDeckView.setBackgroundResource(0);

                    //check game over
                    if (deckOfCards.getNumberOfCardsInDeck() == 0 || playerHand.size() == 0) {
                        gameOver();
                    }

                    AITurn(deckOfCards);
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (playerDrawTurn)
                        return true;
                    if (selectMode) {
                        for (int i = 0; i < viewHand.getChildCount(); i += 2)
                        {
                            viewHand.getChildAt(i).getBackground().setColorFilter(Color.argb(0, 0, 0 ,0), PorterDuff.Mode.SRC_ATOP);
                        }
                        SelectedCards.clear();
                    }
                    selectMode = true;
                    addHouseButton.setVisibility(View.VISIBLE);
                    makeHouseButton.setVisibility(View.GONE);
                    int index = viewHand.indexOfChild(card);
                    SelectedCards.add(playerHand.get(index/2));
                    card.getBackground().setColorFilter(Color.argb(150, 0, 0 ,0), PorterDuff.Mode.SRC_ATOP);
                    return true;
                }
            });
        }
    }

    // update the house grid view
    void updateHouses(){
        HouseLayoutGameScreen.removeAllViews();

        for (int i = 0; i < houses.size(); i++){
            final ImageButton card = new ImageButton(this);
            setNewCardImage(houses.get(i).returnTopCard().getSuit(),houses.get(i).returnTopCard().getValue(),card);
            card.getBackground().setColorFilter(Color.argb(0, 0, 0 ,0), PorterDuff.Mode.SRC_ATOP);
            card.setAdjustViewBounds(true);
            card.setLayoutParams(new LinearLayout.LayoutParams(258,400));
            HouseLayoutGameScreen.addView(card);

            View space = new Space (this);
            space.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            HouseLayoutGameScreen.addView(space);

            // add to existing house
            card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (!addMode)
                        return;

                    int index = HouseLayoutGameScreen.indexOfChild(card);
                    ArrayList<Card> temp = new ArrayList<>(houses.get(index/2).card_house_list);

                    for (int i = 0; i < SelectedCards.size(); i++)
                        temp.add(SelectedCards.get(i));

                    if (!isValidHouse(temp))
                        return;

                    for (int i = 0; i < SelectedCards.size(); i++)
                        houses.get(index/2).card_house_list.add(SelectedCards.get(i));

                    for (int i = 0; i < SelectedCards.size(); i++)
                        houses.get(index/2).card_house_list.remove(SelectedCards.get(i));

                    selectMode = false;
                    addMode = false;

                    updateHand();
                    updateHouses();
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // view cards in hand
                    return true;

                }
            });
        }
    }

    void updateDiscardPile(int ai) { //1 = ai1, 2 = ai2, 3 = player
        DiscardPile.removeAllViews();
        if (ai == 1) {
            for (int i = 0; i < AI1Discard.size(); i++) {
                ImageButton card = new ImageButton(this);
                setNewCardImage(AI1Discard.get(i).getSuit(), AI1Discard.get(i).getValue(), card);
                card.setAdjustViewBounds(true);
                card.setLayoutParams(new LinearLayout.LayoutParams(180, 280));
                DiscardPile.addView(card);

                View space = new Space(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                DiscardPile.addView(space);
            }
        } else if (ai == 2) {
            for (int i = 0; i < AI2Discard.size(); i++) {
                ImageButton card = new ImageButton(this);
                setNewCardImage(AI2Discard.get(i).getSuit(), AI2Discard.get(i).getValue(), card);
                card.setAdjustViewBounds(true);
                card.setLayoutParams(new LinearLayout.LayoutParams(180, 280));
                DiscardPile.addView(card);

                View space = new Space(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                DiscardPile.addView(space);
            }
        }
        else if (ai == 3){
            for (int i = 0; i < playerDiscard.size(); i++) {
                ImageButton card = new ImageButton(this);
                setNewCardImage(playerDiscard.get(i).getSuit(), playerDiscard.get(i).getValue(), card);
                card.setAdjustViewBounds(true);
                card.setLayoutParams(new LinearLayout.LayoutParams(180, 280));
                DiscardPile.addView(card);

                View space = new Space(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                DiscardPile.addView(space);
            }
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

    public boolean canDrawFromDiscard(ArrayList<Card> hand,Card discardedCard){ //checks if any valid combo works
        if (hand.size() < 2) return false; //need at least 2 cards in hand
        ArrayList<Card> threecard = new ArrayList<>(); //2 from hand + discardedCard
        for (int i = 0; i < hand.size(); i++){
            for (int j = 0; j < hand.size(); j++){
                if (j!=i){
                    threecard.add(hand.get(i));
                    threecard.add(hand.get(j));
                    threecard.add(discardedCard);
                    if (isValidHouse(threecard))
                        return true;
                    threecard.clear(); //empties threecard if not true
                }
            }
        }
        return false;
    }

    // checks if a group of cards is a valid house
    public boolean isValidHouse(ArrayList<Card> card_list)
    {
        if (card_list.size() < 3)
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
        ArrayList<Card> temp = new ArrayList<>(card_list);
        ArrayList<Integer> values =  new ArrayList<>();
        while (!temp.isEmpty())
            values.add(temp.remove(findHighestCard(temp)).getValue());
        for (int i = 0; i < values.size() - 1; i++)
        {
            if (values.get(i) - values.get(i + 1) != 1)
            {
                isConsecutive = false;
                break;
            }
        }

        return isSameValue || (isSameSuit && isConsecutive);
    }

    void gameOver(){
        int playerPoints = calculatePoints(playerHand);
        int AI1Points = calculatePoints(AI1Hand);
        int AI2Points = calculatePoints(AI2Hand);

        int winningPoints = Math.min(Math.min(AI1Points,AI2Points),playerPoints); //finding min of 3 ints

        //pretty sure this block takes care of all the possibilities
        if (playerPoints!=winningPoints){
            if (AI1Points == winningPoints && AI1Points != AI2Points)
                endgameMessage.setText("Opponent 1 Won :(");
            else if (AI2Points == winningPoints && AI2Points != AI1Points)
                endgameMessage.setText("Opponent 2 Won :(");
            else
                endgameMessage.setText("Opponent 1 and 2 Tied :(");
        }
        else{ //playerPoints = winningPoints = min(points)
            if (playerPoints < AI1Points && playerPoints < AI2Points)
                endgameMessage.setText("YOU WIN!!!");
            else if (playerPoints == AI2Points && playerPoints == AI1Points)
                endgameMessage.setText("3 Way Tie!");
            else if (AI2Points < AI1Points)
                endgameMessage.setText("You Tie With Opponent 2");
            else
                endgameMessage.setText("You Tie With Opponent 1");
        }

        String message = "Your Points: " + playerPoints + "\nOpponent 1 Points: " + AI1Points + "\nOpponent 2 Points: " + AI2Points;
        pointsMessage.setText(message);
        EndGamePage.setVisibility(View.VISIBLE);
    }

    int calculatePoints(ArrayList<Card> deck) {
        int totalScore = 0;

        for (int i = 0; i < deck.size(); i++){
            if (deck.get(i).getValue() > 10)
                totalScore+=10;
            else
                totalScore += deck.get(i).getValue();
        }

        return totalScore;
    }
}
