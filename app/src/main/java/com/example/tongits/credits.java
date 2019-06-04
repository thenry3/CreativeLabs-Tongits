package com.example.tongits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void onCreditsReturn (View view){
        finish();
    }
}
