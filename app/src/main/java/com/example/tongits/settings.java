package com.example.tongits;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class settings extends AppCompatActivity {

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Switch music = findViewById(R.id.musicSwitch);
        music.setChecked(MainActivity.musicOn());

        music.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.toggleMusic(music.isChecked());
            }
        });

        name = findViewById(R.id.inputtedName);
        name.setText(MainActivity.getName());
    }

    @Override
    protected void onResume(){
        super.onResume();
        name.setText(MainActivity.getName());
    }

    public void onSettingsReturn (View view){
        finish();
    }

    public void onClickName (View view){
        Intent NameIntent = new Intent(this, name.class);
        startActivity(NameIntent);
    }

    public void onClickCredits (View view){
        Intent CreditsIntent = new Intent(this, credits.class);
        startActivity(CreditsIntent);
    }
}
