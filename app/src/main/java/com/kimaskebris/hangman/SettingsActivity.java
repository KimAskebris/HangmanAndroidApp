package com.kimaskebris.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup rg;
    private RadioButton rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rg = findViewById(R.id.rgroup);
    }


    public void changeGameMode(View view) {
        int radioButtonId = rg.getCheckedRadioButtonId();
        rb = findViewById(radioButtonId);
        String mode = rb.getText().toString();
        Intent data = new Intent();
    }
}
