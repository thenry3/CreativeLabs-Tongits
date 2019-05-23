package com.example.tongits;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        final MediaPlayer logic = MediaPlayer.create(this,R.raw.fiveamlogic);
        Switch moosic = (Switch) this.findViewById(R.id.switch2);
        moosic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (logic.isPlaying()){
                    logic.pause();
                }
                else logic.start();
            }
        });
    }
}
