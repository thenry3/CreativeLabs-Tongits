package com.example.tongits;

import android.content.Intent;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static private MediaPlayer bgm;
    static private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgm = MediaPlayer.create(this, R.raw.fiveamlogic);
        bgm.start();

        name = "Name";
    }

    public static String getName() {
        return name;
    }

    public static void editName(String newName) {
        name = newName;
    }

    public static void toggleMusic(boolean turnOn) {
        if (turnOn) {
            bgm.start();
        } else {
            bgm.pause();
        }
    }

    public static boolean musicOn() {
        return bgm.isPlaying();
    }

    public void onClickSettings (View view){
        Intent SettingsIntent = new Intent(this, settings.class);
        startActivity(SettingsIntent);
    }

    public void onClickPlay(View view)
    {
        Intent GameIntent = new Intent(this, Game.class);
        startActivity(GameIntent);
    }

    public void onClickInstructions(View view){
        Intent InstructIntent = new Intent(this, instructions.class);
        startActivity(InstructIntent);
    }
}
