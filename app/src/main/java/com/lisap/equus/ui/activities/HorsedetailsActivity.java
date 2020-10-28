package com.lisap.equus.ui.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lisap.equus.R;
import com.lisap.equus.databinding.ActivityHorseDetailsBinding;


public class HorsedetailsActivity extends AppCompatActivity {

    private ActivityHorseDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHorseDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent i = getIntent();
        String nameStr = i.getStringExtra("name");
        binding.name.setText(nameStr);
        String proprietaireStr = i.getStringExtra("proprietaire");
        binding.activityHorseDetailsProprietaire.setText(proprietaireStr);
        String telProprioStr = i.getStringExtra("telproprio");
        binding.activityHorseDetailsNumTel.setText(telProprioStr);


        binding.activityHorseDetailsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.activityHorseDetailsFab.isSelected()){
                    binding.activityHorseDetailsFab.setSelected(false);
                    binding.activityHorseDetailsFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorsedetailsActivity.this, R.color.colorAccentLight)));

                } else {
                    binding.activityHorseDetailsFab.setSelected(true);
                    binding.activityHorseDetailsFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HorsedetailsActivity.this,R.color.colorIncident)));

                }
        }
    });
}}








