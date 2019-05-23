package com.example.tongits;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage (View view){
        Intent startNewActivity = new Intent(this,DisplayMessageActivity.class);
        startActivity(startNewActivity);
    }

    public void onClickPlay(View view)
    {
        Intent GameIntent = new Intent(MainActivity.this, Game.class);
        startActivity(GameIntent);
    }
}
