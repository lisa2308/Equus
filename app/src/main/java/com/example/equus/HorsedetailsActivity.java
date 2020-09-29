package com.example.equus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HorsedetailsActivity extends AppCompatActivity {
    TextView name;
    TextView proprietaire;
    TextView telproprio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_details);

        name = findViewById(R.id.horse_details_title);
        proprietaire = findViewById(R.id.activity_horse_details_proprietaire);
        telproprio = findViewById(R.id.activity_horse_details_numTel);


        Intent i = getIntent();
        String nameStr = i.getStringExtra("name");
        name.setText(nameStr);
        String proprietaireStr = i.getStringExtra("proprietaire");
        proprietaire.setText(proprietaireStr);
        String telProprioStr = i.getStringExtra("numTel");
        telproprio.setText(telProprioStr);

    }
}
